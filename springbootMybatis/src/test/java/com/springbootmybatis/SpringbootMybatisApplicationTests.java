package com.springbootmybatis;

import com.springbootmybatis.entity.User;
import com.springbootmybatis.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@SpringBootTest
class SpringbootMybatisApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        List<User>users=userService.findAll();
        for(User user:users){
            System.out.println(user);
        }
    }

}
