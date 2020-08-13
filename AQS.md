[从ReentrantLock的实现看AQS的原理及应用](https://tech.meituan.com/2019/12/05/aqs-theory-and-apply.html)
+ AQS的核心思想是
  + 如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并且将共享资源设置为锁定状态，如果请求的共享资源被占用，那么就需要一套线程阻塞等待以及唤醒时锁的分配机制。这个机制AQS使用CLH队列锁实现的，将暂时获取不到锁的线程加入到队列中。
  + CLH队列是虚拟的双向队列(虚拟的双向队列不存在队列实例，仅存在节点之间的关联关系)，AQS是将每条请求共享的资源线程封装成一个CLH锁队列的节点来实现锁的分配
  ```
  // java.util.concurrent.locks.AbstractQueuedSynchronizer
  private volatile int state;
  ```

