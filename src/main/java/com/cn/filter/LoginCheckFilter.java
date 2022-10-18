package com.cn.filter;


import com.alibaba.fastjson.JSON;
import com.cn.common.BaseContext;
import com.cn.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//匹配器,  检测用户是否登录
@WebFilter(urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        //1.获取本此请求的uri
        String requestURI = httpServletRequest.getRequestURI();

        //定义不需要处理的请求路径
        String[] uris = new String[]{
          "/employee/login",
           "/employee/logout",
           "/backend/**",
           "/front/**",
           "/user/sendMsg",
           "/user/login",
                "/doc.html",
                "/webjars/**",
                "/swagger/**",
                "/v2/api-docs"
        };

        //2.判断本此请求是否需要处理
        boolean check = check(uris, requestURI);

        //3.如果不需要处理,则直接放行
        if (check){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //4-1、判断登录状态，如果已登录，则直接放行
        if(httpServletRequest.getSession().getAttribute("employee") != null){

            Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //4-2、判断登录状态，如果已登录，则直接放行
        if(httpServletRequest.getSession().getAttribute("user") != null){

            Long userId = (Long) httpServletRequest.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //5.如果未登录则返回未登录结果,通过输出流向客户端响应数据,由客户端判断是否跳转
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] uris,String reqrui){
        for(String uri : uris){
            boolean match = PATH_MATCHER.match(uri, reqrui);
            if (match){
                return true;
            }
        }
        return false;
    }


}
