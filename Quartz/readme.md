关于定时任务的处理。
1. 使用Timer来完成。写一个timerTask中要执行的任务。然后启动一个Timer来执行。
    缺点就是如果添加了多个任务的话，前面任务超时或者异常或者占用了其他任务的时间的话
    会造成其他任务的失败。本质上就是一个单线程。
2. 使用定时线程池完成。
    有点就是解决了Timer产生的问题，能够开启多个线程来处理多个任务。保证每个任务都能够被执行。
    说原理 缺点就是执行这种每周一8点干什么，这个时间非常难控制。
    原理：
    (1) 关于线程池的用法  使用ExecutorService接受
    Executors.newFixedThreadPool()  固定数量的线程数 阻塞队列无边界
    Executors.newCacheThreadPool() 只要有任务就直接执行 最大线程数为Integer.MAX_VALUE 阻塞队列没有元素  1min回收
    Executors.newSingleThreadPool() 只有一个线程 核心线程数和最大线程数都是1 阻塞队列无边界
    Executors.newSechudleThreadPool() 核心线程数是自己设置的 但是最大线程数是Integer.MAX_VALUE 
    也就说明不会存在延迟的情况。 而且使用的是DelayedWorkQueue
    自定义线程池  ThreadPoolExecutor tpe=new ThreadPoolExecutor(七个参数)
    (2) 关于定时线程。
    维护了一个大根堆。
    再ThreadPoolExecutor中，getTask方法，工作线程会循环的从workQueue中取任务。定时任务不是。
    需要在指定的执行时间才能取得任务。
    具体：    
        关于poll take peek
        poll是非阻塞的,没有元素到期返回null,删除   take是阻塞的,删除。
        peek获取但不移除头部 
    定时任务在获取任务的时候是通过getTask其实追代码的时候发现应用take来获取runnable对象的。
    take的具体实现是 在一个for(;;) 里面去判断堆顶元素和当前时间的大小。如果当前时间<堆顶任务执行时间的话那就执行加锁。
    使用reentranklock，然后进行await(time) 过了这个时间后能够进行signall（）。
        这里的leader线程是为了减少不必要的定时等待，当一个线程成为leader时，它只等待下一个节点的时间间隔，但其它线程无限期等待。 
    leader线程必须在从take（）或poll（）返回之前signal其它线程。
3. 使用springboot的 @Scheudled注解
    最主要的是引入了cron表达式，参数有非常多，能够表达出任何一个时间点，所以一般
    在springboot项目中使用@Schedule注解来解决。
    缺点就是分布式的情况下，其实有存在问题的。不能完成分布式定时任务。
4. 分布式定时任务。
    （1）时间驱动处理场景：整点发送优惠券，每天更新收益。
    （2）按月批量统计报表数据，批量更新短信状态，实时性要求不高。
    提到分布式首先想到的是并发量比较高。
    是否能够高可用，任务是否会出现重复执行，并发处理能力。动态修改和重启不丢失。
    使用上：
    1. job（任务）写执行任务的 
    2. Trigger（触发器） 写执行的具体的时间表
    3. Scheduler（调度器）将job和trigger加入到调度器中去执行。
    在业务层面可以对任务进行暂停和恢复。
    quartz集群模式下，是通过数据库独占锁来唯一获取任务，任务执行并没有实现完善的负载均衡机制。
    使用类似 for update保证只有一个机器执行这个任务。

5. 自己的思考
    使用数据库。加入ip 一直让某台机器执行。 或者使用update判断。
    使用redis分布式锁。 保证只有一个机器能够执行这个任务。
    
    
    