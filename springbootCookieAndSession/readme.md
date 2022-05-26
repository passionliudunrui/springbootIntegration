注意配置问题
使用redis的时候  配置redis的序列化方式
出现报错是redisConnectionFactory爆红
解决方法：(1)springboot版本使用2.6.6
(2)redis-stater版本使用2.6.6
(3)配置commons-pool2


cookie和session
cookie是浏览器访问呢时候携带的数据 key(ticket) value(uuid)
session是本地服务器的存根  通过或者cookie中的ticket(UUID)到服务器中寻找有没有这个user
        