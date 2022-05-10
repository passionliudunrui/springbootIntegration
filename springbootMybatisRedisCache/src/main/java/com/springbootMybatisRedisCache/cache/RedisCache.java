package com.springbootMybatisRedisCache.cache;

import com.springbootMybatisRedisCache.util.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisCache implements Cache {

    /**
     * 当前放入缓存的mapper的namespace com.redis.dao.UserDao
     * 不同的Dao要有不同的id
     */
    private final String id;

    public RedisCache(String id) {
        this.id = id;
    }

    /**
     * getid() 返回cache的唯一名字 唯一标识
     * @return
     */
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object o, Object o1) {
        System.out.println("put执行");
        System.out.println("--------------------------------");
        System.out.println("id"+id);
        System.out.println("key:"+o.toString());
        System.out.println("value"+o1);
        System.out.println("--------------------------------");

//        RedisTemplate redisTemplate = ((RedisTemplate) ApplicationContextUtils.getBean("redisTemplate"));
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        getRedisTemplate().opsForHash().put(id.toString(),o.toString(),o1);


    }

    @Override
    public Object getObject(Object o) {
        System.out.println("get执行");
        System.out.println("key:"+o.toString());
//        RedisTemplate redisTemplate = ((RedisTemplate) ApplicationContextUtils.getBean("redisTemplate"));
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        return getRedisTemplate().opsForHash().get(id.toString(), o.toString());


    }


    @Override
    public Object removeObject(Object o) {
        System.out.println("根据指定key删除缓存");
        return null;
    }

    @Override
    public void clear() {
        System.out.println("清空缓存");
//        RedisTemplate redisTemplate = ((RedisTemplate) ApplicationContextUtils.getBean("redisTemplate"));
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //清空这个模块的所有缓存
        System.out.println(id.toString());
        getRedisTemplate().delete(id.toString());


    }

    @Override
    public int getSize() {
//        RedisTemplate redisTemplate = ((RedisTemplate) ApplicationContextUtils.getBean("redisTemplate"));
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //获取hash中 key value数量
        return (getRedisTemplate().opsForHash().size(id.toString()).intValue());

    }

    public RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = ((RedisTemplate) ApplicationContextUtils.getBean("redisTemplate"));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return  redisTemplate;
    }


}
