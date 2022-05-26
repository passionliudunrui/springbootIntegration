package com.springbootRedisLock;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@RestController
public class IndexController {
    @Autowired
    private Redisson redisson;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 秒杀的接口
     * @return
     * @throws Exception
     */
    /**
     * 1.0版本  线程安全问题
     * 1.1版本  通过synchronized加锁(进程级别的锁)  单体架构没有问题
     * 但是集群部署分布式架构是有问题的
     * 2.0版本  使用redis中的setnx 进行加锁的设计
     * 2.1版本  使用try  catch  finally  在最后释放锁  问题：redis挂了或者系统挂了再次重启后 还是没有释放锁
     * 2.2版本  使用redis的超时时间来解决 expire  问题：还没有执行就宕机了 还是产生了死锁
     * 2.3版本  使用redis提供的原子性的操作来解决
     * 问题：  当这个超时时间设置为10s的时候，在高并发的压力下，系统的性能和响应时间下降，导致
     * 这个任务在15s才能执行完成，但是10s就失效，其他的线程就能获取锁，然后执行。当第一个线程释放锁的时候其实
     * 是释放的第二个线程加的锁。 也就是自己线程加的锁被别的线程释放掉了。
     * 2.4版本  使用了线程的UUID来进行判断(比如  tomcat1中处理这个任务的线程UUID为1
     * tomcat2中处理这个任务的线程UUID是2  在finally中进行释放锁的时候判断lock的value是不是当前线程的UUID的值)
     *问题：如果在超时时间内没有完成任务怎么处理
     * 2.5版本  对这把锁进行续命处理
     * 在拿到锁之后，创建一个子线程 这个线程执行while循环 或者redis中的timer 或者是定时器
     * 如果 time/3的时候去判断是否执行完了任务 没有执行完 那就超时时间+time/3
     * 3.0版本  使用redisson来解决分布式场景下复杂的问题
     * 问题：大部分公司的redis都是哨兵模式 主从架构 集群部署。
     * 当主节点执行完了set命令后宕机，并没有同步到slave。重新选举节点后发现锁不存在
     * 4.0版本 使用zookeeper来解决
     * 应为zookeeper是强一致性的，那么当我们set一个数据的时候，当半数以上的节点收到之后才认为
     * 这个set成功了，当master挂掉之后，zookeeper保证选举出来的节点一定存在这个key
     * 5.0版本   使用redLock来解决
     * 也就是和zk相同的思想 加锁的时候对多个redis节点进行加锁 超过半数以上的节点加锁成功才算成功
     * 但是性能有问题 如果失败还要回滚
     * 6.0版本  借鉴concurrentHashMap的分段锁的思想
     * stock_001  是1000
     * stock_001_01 stock_001_02  100 每个分段 分段到redis集群中
     * 那么实现了性能的提升  恰好redis集群是分片的
     *这里面又会出现一些问题  比如有库存 但是某个redis已经没有库存了其实有库存
     *
     *
     */


    @RequestMapping("/testBloom")
    public String bloom(){

        RBloomFilter<String> test = redisson.getBloomFilter("test");
        test.tryInit(1000L,0.2);
        test.add("1");
        test.add("2");

        boolean contains = test.contains("3");
        boolean contains1 = test.contains("2");

        System.out.println(contains);
        System.out.println(contains1);

        return "ok";

    }


    @RequestMapping("/deduct_stock")
    public String deductStock() throws Exception{

        String lockKey="product_001";

//        Boolean result=stringRedisTemplate.opsForValue().setIfAbsent(lockKey,"passion");
//        stringRedisTemplate.expire(lockKey,10, TimeUnit.SECONDS);
        //创建这个线程的唯一ID
        String clientId= UUID.randomUUID().toString();
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "passion", 10, TimeUnit.SECONDS);



        RLock redissonLock = redisson.getLock(lockKey);

        try{
//            if(!result){
//                return "error";
//            }

            redissonLock.lock(30,TimeUnit.SECONDS);
            int stock=Integer.parseInt(stringRedisTemplate.opsForValue().get(lockKey));

            if(stock>0){
                int realStock=stock-1;
                stringRedisTemplate.opsForValue().set("stock",realStock+"");
                System.out.println("扣减库存成功 剩余库存 "+realStock);

            }else{

                System.out.println("扣减失败 库存不足");
            }
        }
        finally {
            redissonLock.unlock();
//            if(clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))){
//                stringRedisTemplate.delete(lockKey);
//            }

        }




        return "end";


    }


}
