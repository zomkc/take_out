package com.cn.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cn.common.BaseContext;
import com.cn.common.R;
import com.cn.entity.User;
import com.cn.service.UserService;
import com.cn.utils.SMSUtils;
import com.cn.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
//@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;
    @GetMapping("/get")
    public  String get(String s){
        System.out.println(s+"==========================test");
        return s;
    }
    //验证码
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user){
        //获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            //随机四位数验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            //调用阿里短信服务
            //SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,code);
            //将验证码缓存到redis中,设置有效期为五分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

//            R.success("验证码发送成功");
           return R.success(code);
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
//        Object attribute = session.getAttribute(phone);

        //从redis中获取缓存的验证码
        Object attribute = redisTemplate.opsForValue().get(phone);

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
//            redisTemplate.opsForValue().set(phone,user.getId(),60, TimeUnit.MINUTES);

            //用户登录成功,删除缓存中的验证码
            redisTemplate.delete(code);

            return R.success(user);
        }


        return R.error("登录失败");
    }

    //账号密码登录
    @PostMapping("/password")
    public R<User> password(@RequestBody Map map,HttpSession session) {
        //获取手机号
        String phone = map.get("phone").toString();
        //获取密码
        String password =  map.get("password").toString();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone,phone)
                .eq(User::getPassword,password);

        User user = userService.getOne(wrapper);
        if (user == null){
            //判断用户是否存在,如果存在则提示密码错误
            LambdaQueryWrapper<User> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(User::getPhone,phone);
            User user2 = userService.getOne(wrapper2);
            if (user2 != null){
                return R.error("密码错误");
            }
            //当前用户是新用户,自动注册账号
            user = new User();
            user.setName(phone);
            user.setPhone(phone);
            user.setStatus(1);
            user.setPassword(password);
            userService.save(user);
        }
        user = userService.getOne(wrapper);
        session.setAttribute("user",user.getId());

        return R.success(user);
    }

    //用户信息
    @PostMapping("/edit")
    public R<User> edit(@RequestBody User user){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone,user.getPhone());

        User one = userService.getOne(wrapper);
        return R.success(one);
    }

    //确认修改
    @PostMapping("/edituser")
    public R<String> edituser(@RequestBody User user){
        userService.editUser(user);
        return R.success("修改成功");
    }

    //用户退出
    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session){
        session.removeAttribute("user");

        return R.success("退出登录成功");
    }

}
