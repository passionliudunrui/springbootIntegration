1.springbootNetty 先用springboot整合netty的基本项目。
2.springbootMybatis 将所用的表的Dao层整合完毕。  整合使用redis缓存。 登录模块完成。  整合使用rabbitmq 。  整合使用分页插件。
5.springbootNettyMax 将原来的项目进行整合
6.springbootDynamicTp 动态线程池的实现
7.springbootRedisLock  redis实现分布式锁并且整合布隆过滤器
8.springbootMybatisRedisCache mybatis整合redis实现二级缓存 （代码有点问题）具体看springboot-redis
9.springbootCookieAndSession   springboot整合thymeleaf 并且实现cookie和session的交互


For Spring
(一) spring spring framework  springmvc  springboot
spring其实是一个生态体系,里面包含了spring framework springmvc springboot springcloud等等
常说的spring框架其实是spring framework。 其他的spring的框架都是在spring framework的ioc和aop的
对于解决特定领域问题的延申。
    1. spirng framework 是spring全家桶的核心，spring的其他产品都是基于spring framework框架的。
    最主要就是两个思想 IOC和AOP，快速构建轻量级的JAVAEE框架。简化企业级的应用开发。
    程序设计中有个思想就是低耦合高内聚。
    那么ioc是控制反转解决了低耦合的问题。通过自动注入。也就是需要某个对象的时候直接从容器中获取，不需要自己创建或者去查找。
    aop很好的解决了高内聚的问题。面向切面。允许通过分离应用的业务逻辑和系统级别的服务，进行内聚性开发。在做业务开发的时候需要关注
    业务本身。
    2. springmvc的是企业级WEB开发的MVC框架。专注于开发WEB项目。也是依赖与spring framework。对web领域做出了
    延申。 提出了model view contoller的思想。 三层架构
    (1) 浏览器向ip+端口号发送了web请求，服务器中dispatcherServlet进行接收。
    (2) 然后通过浏览器发来的地址，找到handle。然后也就是处理器来处理这个请求。
    (3) 处理请求后返回ModelAndView到dispatcherServlet。
    (4) dispatcherServlet对ModelAndView进行解析，然后返回真正的视图。
    (5) dispatcherServlet将最后的视图返回给浏览器。
    (6) 浏览器进行渲染。
    3. springboot主要为了简化spring项目的开发，提出了约定大于配置的思想，开箱即用的特点，简化了配置的流程。
    取消了xml文件的配置。直接嵌入了tomcat。非常方便的去开发。
    如果使用springboot开发web应用。因为了web start依赖之后直接写个controller和requestMapper就能直接接收浏览器的请求。


(二) 关于spring的使用的问题
    1. 什么是spring ioc容器。
    2. 通过哪几种方法完成依赖注入。
        构造函数注入
        setter方法注入
        接口注入
    3. 关于自动装配
    byname 通过名字来获取对象 去ioc容器中寻找同名的对象
    byType 根据属性数据类型自动装配。如果从ioc容器中找到一个那就匹配。如果找到了多个的话那就会抛出异常。
    构造方法自动装配 。autowire设置为constructor。


(三) 面试题
    1.springboot中的starter。
        之前开发web项目的时候需要很多的依赖。现在使用springboot使用了spring-boot-starter-web就能解决
        因为这个starter中包含了多个web依赖的并且是搭配好的版本。
    2.spring中的依赖注入。
        (1)使用构造器注入。<bean id='' class=''>  <constructor ref> </constructor> </bean>
        (2)使用set注入。 <bean id='' class=''>  <property name='' value=''> </bean>
        (3)使用注解  @Configuration @Bean 进行注入    @Controller  @Service @ComponentScan
    3.sprin中的装配。
        (1)byType。
        (2)byName。
        (3)使用注解。根据数据类型装配 ，比如@Autowire  比如使用 @Qualifier(name)  或者使用@Resource
    4.springboot的设计模式
    5.spring的ioc对象作用域？
        1.单例模式 多线程不安全 默认  <socpe='sigleton'>
        2.原型模式  每次使用时候创建一个新的Bean实例。 每一个AutoWire 都会创建一个对象。
        3.request。一次http请求中，容器提供一个实例。http请求结束，实例销毁。
        4.session。一次session中，容器返回这个Bean的同一个实例。
        5.gloal session。没有研究过。
    6.spirng怎么运行的？
    7.spring的生命周期。实例化 属性赋值 初始化    使用bean  销毁
        (1) 实例化一个bean,也就是new一个对象。
        (2) 属性赋值 也就是对这个对象赋值，set... 根据xml中的 name=''  value=''  对属性进行赋值
        (2) IOC依赖注入 按照spring上下文对实例化的bean进行配置，也就是IOC注入。注册到了容器的hashmap中。
        (3) 初始化。主要是对bean的元数据进行配置。setBeanName  setApplicationContext
        执行postProcessBeforeInitiailization 然后执行bean中的 init-method方法  postProcessAfterInitialization方法。
        执行完了初始化的操作，这个bean已经在beanFactory中也就是容器中了。
        (4) 用户使用bean
        (5) 销毁bean容器。最后执行 bean中配置的 destory-method方法
    8.ioc原理
    9.aop原理
    10.beanFactory和applicationContext区别
        applicationContext是beanFactory的子类。古老的beanFactory无法满足不断更新的spring的需求，用applicationContext代替了beanFactory
        添加了bean的自动装配，资源访问等。
    11.如何解决循环依赖的问题
        三级缓存解决循环依赖问题
        singletonObjects 一级缓存，用于保存实例化、注入、初始化完成的bean实例
        earlySingletonObjects 二级缓存，用于保存实例化完成的bean实例
        singletonFactories 三级缓存，用于保存bean创建工厂，以便于后面扩展有机会创建代理对象
        假设A依赖了实例对象B，同时B也依赖A实例对象。
        (1) A首先完成实例化，并把自己添加到三层缓存中。
        (2) A进行依赖注入，发现自己依赖对象B，尝试get(B)
        (3) B没有实例化那就对B实例化，添加到三级缓存中。
        (4) B再依赖注入的时候发现自己依赖A对象。去一级 二级 三级中找到了A，然后A从三级放到了二级缓存中。
        (5) B再拿到A对象后顺利初始化，并且放到了二级缓存中。
        (6) 然后返回A，A拿到了B的对象。完成初始化。
