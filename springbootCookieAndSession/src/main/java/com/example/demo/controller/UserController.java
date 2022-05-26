package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/dologin")
//    @ResponseBody
    public String login(HttpServletRequest request){
        String uname = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("uname:{}",uname);
        log.info( "pasword:{}",password );
        User user = userMapper.selectUser(uname, password);
        System.out.println(user);

        if(user != null){
            System.out.println("11111");
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
}