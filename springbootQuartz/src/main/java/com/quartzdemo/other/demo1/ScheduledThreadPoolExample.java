package com.quartzdemo.other.demo1;

import java.time.LocalDateTime;
import java.util.concurrent.*;

public class ScheduledThreadPoolExample {
    public static void main(String[] args) throws InterruptedException {
        // 创建 ScheduledThreadPool 线程池

        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);

        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        ExecutorService executorService2 = Executors.newFixedThreadPool(2);

        System.out.println("schedule 方法添加任务：" + LocalDateTime.now());
        threadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行 schedule 方法：" + LocalDateTime.now());
            }
        }, 3, TimeUnit.SECONDS); // 3s 之后执行


        // 以下代码是给业务方法一个时间对照信息
        TimeUnit.SECONDS.sleep(10); // 休眠 10s
        System.out.println("当前时间：" + LocalDateTime.now());
    }
}