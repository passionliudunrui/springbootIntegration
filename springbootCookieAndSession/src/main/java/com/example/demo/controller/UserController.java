package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.CookieUtil;
import com.example.demo.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/dologin")
//    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response){
        String uname = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("uname:{}",uname);
        log.info( "pasword:{}",password );
        User user = userMapper.selectUser(uname, password);
        System.out.println(user);

        if(user != null){
            System.out.println("11111");
            String ticket= UUIDUtil.uuid();
            redisTemplate.opsForValue().set("user:"+ticket,user);

            CookieUtil.setCookie(request,response,"userTicket",ticket);



            return "test.html";
        }else{
            System.out.println("22222222222");
            return "loginN";
        }
    }

    @RequestMapping("/doregister")
    public String register(HttpServletRequest request){
        String uname = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        log.info("uname:{}",uname);
        log.info( "pasword:{}",password );
        log.info( "pasword2:{}",password2 );
        User user = userMapper.selectUname(uname);
        if(user == null && password.equals(password2)){
            userMapper.saveUser(uname,password);
            return "index";
        }else{
            return "registN";
        }
    }

    @ResponseBody
    @RequestMapping("/doSomething")
    public String something(HttpServletRequest request,HttpServletResponse response){

        System.out.println("进入");

        String ticket=CookieUtil.getCookieValue(request,"userTicket");
        if(ticket==null){
            System.out.println("not ticket");
        }

        User user=(User)redisTemplate.opsForValue().get("user:"+ticket);
        System.out.println(user);
        if(user==null){
            System.out.println("not user");
        }

//        String uname = request.getParameter("username");
//        String password = request.getParameter("password");
//        String password2 = request.getParameter("password2");
//        log.info("uname:{}",uname);
//        log.info( "pasword:{}",password );
//        log.info( "pasword2:{}",password2 );
//        User user = userMapper.selectUname(uname);
//        if(user == null && password.equals(password2)){
//            userMapper.saveUser(uname,password);
//            return "index";
//        }else{
//            return "registN";
//        }

        return "你有权限查看";
    }


}