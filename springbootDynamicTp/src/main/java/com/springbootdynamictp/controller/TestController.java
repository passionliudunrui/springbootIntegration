package com.springbootdynamictp.controller;

import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@SuppressWarnings("all")
public class TestController {

//    @Resource
//    private DtpExecutor dtpExecutor1;
//
//    @Resource
//    private DtpExecutor dtpExecutor2;

    @GetMapping("/dtp-zookeeper-example/test")
    public String test() {

        System.out.println("开始执行任务");
        new Thread(() -> {
            try {
                task();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return "success";
    }

    public void task() throws InterruptedException {
        DtpExecutor dtpExecutor1 = DtpRegistry.getExecutor("dtpExecutor1");
        DtpExecutor dtpExecutor2 = DtpRegistry.getExecutor("dtpExecutor2");
        System.out.println("----------------");
        System.out.println(dtpExecutor1.getCorePoolSize());
        System.out.println(dtpExecutor1.getMaximumPoolSize());

        System.out.println("----------------");
        for (int i = 0; i < 2; i++) {
            Thread.sleep(100);

            dtpExecutor1.execute(() -> {
                log.info("1 task");
                System.out.println(Thread.currentThread().getName()+"  i am dynamic-tp-test-1 task");
            });
            dtpExecutor2.execute(() -> {
                log.info("2 task");
                System.out.println(Thread.currentThread().getName()+"  i am dynamic-tp-test-2 task");
            });
        }
    }
}
