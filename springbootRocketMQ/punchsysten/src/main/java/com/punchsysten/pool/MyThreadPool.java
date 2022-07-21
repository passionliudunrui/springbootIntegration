package com.punchsysten.pool;

import java.util.concurrent.*;

public class MyThreadPool {

    public static ExecutorService executorService=new ThreadPoolExecutor(8,16,60, TimeUnit.MINUTES,new LinkedBlockingDeque<>(100));


}
