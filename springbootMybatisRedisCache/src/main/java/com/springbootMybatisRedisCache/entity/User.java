package com.springbootMybatisRedisCache.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String id;
    private String name;
    private Integer age;
    private Date bir;

}