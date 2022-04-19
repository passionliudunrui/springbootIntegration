springboot集成 mysql  mybatis  druid
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




---------------集成redis框架   用来缓存用户的信息  id nickname passward-----------------
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

























