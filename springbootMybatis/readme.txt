springboot集成 mysql  mybatis  druid  redis  rabbitmq
$ git remote rm origin
$ git remote add origin https://github.com/passionliudunrui/springbootIntegration.git
注意网络问题


mybatis绑定参数问题
1.如果是select的话 直接返回POJO类型
2.如果是insert的话  最好传入的POJO类型  参数名#{id}
3.如果是insert基本类型的话，在xml文件中指定parameterType
4.如果是想要返回插入数据的id，使用keyProperty(返回插入的id，注入到POJO对象中)  useGeneratedKeys(自增)


sql语句
1.update 表名user set k1 =#{v1}  ,  k2=#{v2}  where
2.insert into  user()  value 插入一条数据  values 插入多行数据
3.delete from

注解
1.@Autowire  @Resource区别
都是实现bean的注入
他们的主要区别就是@Autowired是默认按照类型装配的（spring的） @Resource默认是按照名称装配的（不是spring的）
byName 通过参数名 自动装配，如果一个bean的name 和另外一个bean的 property 相同，就自动装配。
byType 通过参数的数据类型自动自动装配，如果一个bean的数据类型和另外一个bean的property属性的数据类型兼容，就自动装配
如果想用名字注入 使用Qualifier(name)

问题：
resultMap如何使用还不会
resultMap解决的是 比如Student中有个属性是Card  那么如果想查询Student的所有信息的话，可以使用resultMap来实现映射查询




-----------------------------集成redis框架-------------------------------
    (一)关于redis的安装流程  https://www.bilibili.com/video/BV1sf4y1L7KE?p=14
    1.下载压缩包到download
    2.解压
    3.安装到 /usr/local/redis当中
    4.编译
    5.拷贝配置文件
    6.修改配置文件
        (1)#bind 127.0.0.1
        (2)protectmode no
        (3)daemonize yes  守护进程 后台运行

    基本的命令 1. ./redis-server redis.conf

    (二)用springboot连接redis注意的问题
    1.防火墙
    2.redis.conf的配置
    3..yml的配置是否生效

    (三)关于redis的持久化
        提供了两种持久化的策略  RDB  AOF
        自动配置 默认是RDB  配置  save 900 1  (900s 15分钟内1个修改)
                        save 300 10 (300s 5分钟内10个修改)
                       save 60 10000 (60s 1分钟内10000个修改)
        手动配置  save  bgsave
        save会阻塞redis，是主线程自己进行RDB。执行期间不能处理其他命令。
        bgsave是fork一个子线程来执行快照操作，主线程依然相应客户端的请求。
        RDB持久化是Redis定期将内存中数据集快照写入磁盘中，实际上是主进程创建了一个子进程，先将数据集写入临时文件
        写入成功后，再替换之前的文件，用二进制压缩存储，整个过程主进程不进行任何的IO操作。
        AOF是将操作的命令存到了磁盘中。
        no 不进行同步，依靠操作系统来进行同步，Faster
        always  表示每次写操作进行同步
        everysec  每一秒对写操作同步一次

        如果只配置 AOF ，重启时加载 AOF 文件恢复数据
        如果同时配置了 RDB 和 AOF ，启动只加载 AOF 文件恢复数据；
        如果只配置 RDB，启动将加载 dump 文件恢复数据。

        RDB和AOF都有各自的缺点：
        RDB是每隔一段时间持久化一次, 故障时就会丢失宕机时刻与上一次持久化之间的数据，无法保证数据完整性
        AOF存储的是指令序列, 恢复重放时要花费很长时间并且文件更大
        Redis 4.0 提供了更好的混合持久化选项： 创建出一个同时包含 RDB 数据和 AOF 数据的 AOF 文件， 其中 RDB 数据位于 AOF 文件的开头，
        它们储存了服务器开始执行重写操作时的数据库状态，至于那些在重写操作执行之后执行的 Redis 命令， 则会继续以 AOF 格式追加到 AOF 文件的末尾， 也即是 RDB 数据之后。


    (四)关于redis中update的操作的解决方法
        问题1：用户信息更新，只更改了数据库信息，redis信息是脏数据，所以更新时候将新数据查询后覆盖到redis
        解决思路：1.进行用户信息修改操作
                2.修改完毕后根据用户id查询用户并且覆盖到redis中
        也就是先修改数据库 再修改redis

        问题2：由于网络问题或者其他问题导致只更新的数据，未将数据写道redis，造成了数据不一致的问题
        解决思路：1.进行更新操作的时候，先将redis中的旧数据删除。
                2.修改数据库之后，再将新数据写入到redis中。
        也就是先删除redis，再修改数据库，再更新redis

        问题3：用户发送多个请求，旧缓存删除成功但是新数据还没有来得及更新到数据库中，读缓存发现redis没有数据，
        然后去数据库中查询，查到的是未修改的旧数据
        解决思路：1.先删除缓存。
                2.将数据库更新。
                3.线程等待一段时间后，再次淘汰缓存数据，用于别的请求请求完成，避免读取脏数据。
        也就是延时双删的策略
        延时的时间：A sleep的时间，需要大于线程B读取数据再写入缓存的时间，需要再实际业务运行时候估算


    (五)关于双写一致性问题
        系统引入缓存提高应用性能问题
        引入缓存后，需要考虑缓存和数据库双写一致性问题，可选的方案有：「更新数据库 + 更新缓存」、「更新数据库 + 删除缓存」
        不管哪种方案，只要第二步操作失败，都无法保证数据的一致性，针对这类问题，可以通过消息队列重试解决
        「更新数据库 + 更新缓存」方案，在「并发」场景下无法保证缓存和数据一致性，且存在「缓存资源浪费」和「机器性能浪费」的情况发生，一般不建议使用
        在「更新数据库 + 删除缓存」的方案中，「先删除缓存，再更新数据库」在「并发」场景下依旧有数据不一致问题，解决方案是「延迟双删」，但这个延迟时间很难评估，所以推荐用「先更新数据库，再删除缓存」的方案
        在「先更新数据库，再删除缓存」方案下，为了保证两步都成功执行，需配合「消息队列」或「订阅变更日志」的方案来做，本质是通过「重试」的方式保证数据一致性
        在「先更新数据库，再删除缓存」方案下，「读写分离 + 主从库延迟」也会导致缓存和数据库不一致，缓解此问题的方案是「强制读主库」或者「延迟双删」，凭借经验发送「延迟消息」到队列中，延迟删除缓存，同时也要控制主从库延迟，尽可能降低不一致发生的概率。

-----------------------------集成rabbitmq框架-------------------------------
1.下载到Downloads中了。
2.rabbitMQ的相关状态
    systemctl start rabbitmq-server.service
    systemctl status rabbitmq-server.service
    systemctl restart rabbitmq-server.service

3.springboot集成rabbitmq
    1.注意在配置yml的时候 配置的端口号和web端的端口号不同






















