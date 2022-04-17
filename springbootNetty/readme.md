--------------------一.springboot整合Netty----------------------------
1.0版本
    1.通过使用@Server将一些Handler注入到spring容器中。
    2.通过使用@Autowire取出容器中的组件使用
    3.添加@SpringBootApplication表明是启动类 
    通过里面的ApplicationContext获取getBean获取容器中的对象 然后启动

2.0版本
    将服务的启动和关闭分离，实现CommandLineRunner接口中的run方法
    实现在项目启动后执行netty
    实现在jvm关闭时候，执行addShutdownHook的钩子函数
