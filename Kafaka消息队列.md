+ 消息队列技术是分布式应用间交换信息的一种技术，消息队列可驻留在内存中或者磁盘上。通过消息队列存储消息，知道他们被应用程序读走。通过消息队列，应用程序可独立的消费消息，不需要知道彼此的位置，或在继续执行前不需要等待接收程序接受此消息。在分布式计算环境下，为了集成分布式应用，开发者需要向异构网络环境下的分布式应用提供有效的通信手段，为了管理需要共享的信息，对应用提供公共的信息交换机制是重要的。常用的消息技术是Message Quque。
+ Broker：kafka集群包含一个多个服务器，这种服务器被称为broker
+ Topic：每条发布到kafka集群中的消息都有一个类别，这个类别被称为topic(物理上不同的topic的消息分开存储，逻辑上topic的消息虽然保存在一个或多个broker上，但用户只需要指定消息的topic即可生产或消费数据而不必关心数据存于何处)
+ Partition：partition是物理上的概念，每个topic都包含一个或多个partition
+ Producer:生产者：负责发布消息到Kafka的Broker
+ Consumer：消费者，从Kafka broker读取消息的客户端
+ Consumer Group：每个Consumer属于一个特定的Consumer Group(可为每个Consumer指定group name，若只指定group name则属于默认group)
----------------------
+ Kafka的消息队列一般分为两种模式：点对点模式和发布订阅模式
  + 点对点：生产者 ——> Message Broker ——> Consumer
  + 发布订阅：多个生产者的消息被多个消费者同时消费的情况
    + Kafka producer采用push模式将消息发送到Broker，多个Broker组成一个kafka集群。Kafka consumer采用pull模式订阅消费信息
    + 一个kafka集群包含若干个Broker，每个Broker可接受多个producer发布的消息，Consumer从Broker中获取消息构成组，Kafka通过Zookeeper管理配置，选举leader，以及在Consumer Group发生变化时进行rebalance
-------------
+ kafka特点：
  + 高吞吐量，低延迟：kafka每秒可以处理几十万个消息，每个topic可分为多个partition，consumer group对partition进行consumer操作
  + 可扩展性：Kafka集群支持热扩展
  + 持久性，可靠性：消息被持久化到本地磁盘，并支持数据备份防止数据丢失
  + 容错性：允许集群中节点失败
  + 高并发：支持多个客户端读写
+ 使用情况
  + 日志收集
  + 运行指标：Kafka经常用来记录运营监控数据，包括收集各种分布式应用数据，生产数据集中反馈，比如报告等
  + 消息系统：解耦生产者和消费者，缓存消息
  + 用户活动追踪：kafka经常被用到记录wen用户或者app用户的各种活动，如浏览网页搜索，点击等活动，这些活动消息被各个服务器发布到kafka各个topic中，然后订阅这些topic来做实时监控分析
 ---------------------
+ lerder：Kafka集群
