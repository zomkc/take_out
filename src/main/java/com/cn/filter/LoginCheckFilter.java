package com.cn.filter;


import com.alibaba.fastjson.JSON;
import com.cn.common.BaseContext;
import com.cn.common.R;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
@Component
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //配置跨域
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        response.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域

//        System.out.println(request.getSession().isNew());
        //1.获取本此请求的uri
//        String requestURI = request.getRequestURI();
//        System.out.println("=======================拦截到请求" + requestURI);
//        System.out.println("=======================请求类型" + request.getMethod());

        // 如果是OPTIONS则结束请求
//        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
//            response.setStatus(HttpStatus.NO_CONTENT.value());
//            System.out.println("这是OPTIONS请求,结束请求");
//            return;
//        }
//
//        //定义不需要处理的请求路径
//        String[] uris = new String[]{
//                "/employee/login",
//                "/employee/logout",
//                "/backend/**",
//                "/user/sendMsg",
//                "/user/login",
//                "/user/password",
//                "/user/get"
//        };
//        //2.判断本此请求是否需要处理
//        boolean check = check(uris, requestURI);
//
//        //3.如果不需要处理,则直接放行
//        if (check){
//            filterChain.doFilter(request,response);
//            return;
//        }
//
//        //4-2、判断登录状态，如果已登录，则直接放行
//        if (request.getSession().getAttribute("user") != null) {
//            System.out.println(BaseContext.getCurrentId()+"===base====");
//            System.out.println(request.getSession().getAttribute("user") + "拦截器获取的user");
//            Long userId = (Long) request.getSession().getAttribute("user");
//            BaseContext.setCurrentId(userId);
//
//            filterChain.doFilter(request, response);
//        }
//        filterChain.doFilter(request, response);

        //4-1、判断登录状态，如果已登录，则直接放行
//        if(request.getSession().getAttribute("employee") != null){
//
//            Long empId = (Long) request.getSession().getAttribute("employee");
//            BaseContext.setCurrentId(empId);
//
//            filterChain.doFilter(request,response);
//            return;
//        }

        filterChain.doFilter(request, response);
//
//        //4-2、判断登录状态，如果已登录，则直接放行
//        if(httpServletRequest.getSession().getAttribute("user") != null){
//
//            Long userId = (Long) httpServletRequest.getSession().getAttribute("user");
//            BaseContext.setCurrentId(userId);
//
//            filterChain.doFilter(httpServletRequest,httpServletResponse);
//            return;
//        }
//
//        //5.如果未登录则返回未登录结果,通过输出流向客户端响应数据,由客户端判断是否跳转
//        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//        return;
//    }
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