package com.cn.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cn.common.R;
import com.cn.entity.User;
import com.cn.service.UserService;
import com.cn.utils.SMSUtils;
import com.cn.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //验证码
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            //随机四位数验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            //调用阿里短信服务
            //SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,code);
            log.info(code);
            //存入session
            session.setAttribute(phone,code);
        }

//        session.setMaxInactiveInterval(60);
        return R.error("短信发送失败");
    }

    //登录
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpSession session){
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从Session中获取保存的验证码
        Object attribute = session.getAttribute(phone);

        //进行验证码的对比,(页面提交的验证码和Session中的验证码进行比对)
        if (attribute != null && attribute.equals(code)){
            //如果比对成功,说明登录成功
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone,phone);

            User user = userService.getOne(wrapper);
            if (user == null){
                //判断当前用户是否是新用户,如果是新用户则自动注册账号
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            user = userService.getOne(wrapper);
            session.setAttribute("user",user.getId());
            return R.success(user);
        }


        return R.error("登录失败");
    }

}
