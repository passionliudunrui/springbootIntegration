package com.punchsysten.thread;



import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThread implements Runnable {

    private static AtomicInteger atomicInteger=new AtomicInteger();

    private ArrayList<String> queue;

    public MyThread(ArrayList<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        System.out.println("执行批量插入 数据  "+atomicInteger.getAndIncrement());

//            <insert id="insertBatch" keyProperty="id" useGeneratedKeys="true">
//                insert into labour_user_group(user_id, status, name, avatar, real_name, address, level, describe, gmt_created, gmt_modified, created_by, modified_by, is_deteled)
//                values
//                <foreach collection="entities" item="entity" separator=",">
//                (#{entity.userId}, #{entity.status}, #{entity.name}, #{entity.avatar}, #{entity.realName}, #{entity.address}, #{entity.level}, #{entity.describe}, #{entity.gmtCreated}, #{entity.gmtModified}, #{entity.createdBy}, #{entity.modifiedBy}, #{entity.isDeleted})
//                </foreach>
//            </insert>

    }
}
