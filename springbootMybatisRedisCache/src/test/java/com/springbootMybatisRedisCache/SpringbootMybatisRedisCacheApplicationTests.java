package com.springbootMybatisRedisCache;

import com.springbootMybatisRedisCache.entity.User;
import com.springbootMybatisRedisCache.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootMybatisRedisCacheApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    public void findAll() {

        System.out.println("0000000000000000000");
        List<User> all = userService.findAll();
        for(User user:all){
            System.out.println(user);
        }
        System.out.println("0000000000000000000");

        List<User>all2=userService.findAll();
        for(User user:all2){
            System.out.println(user);
        }

        System.out.println("0000000000000000000");

        List<User>all3=userService.findAll();
        for(User user:all2){
            System.out.println(user);
        }

        System.out.println("0000000000000000000");
        userService.delete("2");

        System.out.println("0000000000000000000");

        List<User>all4=userService.findAll();
        for(User user:all2){
            System.out.println(user);
        }








    }



    @Test
    public void findById(){

        System.out.println("第一次");
        User user1 = userService.findById("4");

        System.out.println(user1);

        User user2 = userService.findById("5");
        System.out.println("第二次");

        System.out.println(user2);

        System.out.println("第三次");
        User user3 = userService.findById("4");

        System.out.println(user3);

        User user4 = userService.findById("5");
        System.out.println("第四次");

        System.out.println(user4);



        System.out.println("删除4");
        userService.delete("4");

        System.out.println("查找5");

        User user5=userService.findById("5");
        System.out.println(user5);





    }

    @Test
    public void delete(){
        userService.delete("2");

    }




}