package com.example.demo.mapper;

import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @Insert("insert into user(uname,password) values(#{uname},#{password})")
    int saveUser(@Param("uname") String uname,@Param("password") String password);

    @Select("select uid,uname,password from user where uname=#{uname}")
    User selectUname(@Param("uname") String uname);

    @Select("select uid,uname,password from user where uname=#{uname} and password=#{password}")
    User selectUser(@Param("uname") String uname,@Param("password") String password);
}