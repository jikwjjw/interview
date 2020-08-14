+ 首先redis设计用来做缓存的，因为redis含有阻塞式API，所以redis也可以做消息队列，做消息队列的其他特性例如FIFO(先进先出)也很容易实现只需要一个list对象，从头取数据，从尾部数据即可，redis能做消息队列得益于于list结构作为队列
  + 一般使用list结构作为消息队列，rpush生产消息，lpop消费消息，当lpop没有消息的时候，要适当sleep一会再重试。
    + 不使用sleep时，可以使用bloop，再没有消息的时候，它会阻塞消息的到来。
  + 发布订阅模式(pub/sub)
    + 使用pub/sub主题订阅模式，可以实现1:N的消息队列，一般使用消息队列MQ
------------------------------
+ 实现延时队列
  + 使用sortset，拿时间戳做score，消息内容作为key调用zadd来生产消息，消费者用zrangbyscore指令来获取N秒前的数据轮询来处理
--------------------
+ 集群高可用
  + redis sentinal着眼于高可用，再master宕机时，会自动将salve提升为master，继续提供服务
  + redis cluster着眼于扩展性，在单个redis不足时，使用cluster分布式存储
