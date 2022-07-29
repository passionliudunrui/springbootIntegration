package com.punchsystem1.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {

    public static ExecutorService executorService=new ThreadPoolExecutor(8,16,60, TimeUnit.MINUTES,new LinkedBlockingDeque<>(100));


}
