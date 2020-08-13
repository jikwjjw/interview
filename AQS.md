[从ReentrantLock的实现看AQS的原理及应用](https://tech.meituan.com/2019/12/05/aqs-theory-and-apply.html)
+ AQS的核心思想是
  + 如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并且将共享资源设置为锁定状态，如果请求的共享资源被占用，那么就需要一套线程阻塞等待以及唤醒时锁的分配机制。这个机制AQS使用CLH队列锁实现的，将暂时获取不到锁的线程加入到队列中。
  + CLH队列是虚拟的双向队列(虚拟的双向队列不存在队列实例，仅存在节点之间的关联关系)，AQS是将每条请求共享的资源线程封装成一个CLH锁队列的节点来实现锁的分配
  ```
  // java.util.concurrent.locks.AbstractQueuedSynchronizer
  private volatile int state;
  ```
+ AQS定义两种资源共享方式
  + 独占：只有一个线程能执行，又分为公平锁和非公平锁
  + 共享：多线程可同时执行，通过信号量来控制多线程访问某个资源
+ 非公平锁和公平锁区别
  + 非公平锁性能高于公平锁，非公平锁可以减少CPU唤醒线程的开销，整体吞吐率会高点，CPU也不必唤醒所有线程，会减少唤起线程的数量
  + 非公平锁性能虽然优先公平锁，但会导致线程饥饿情况，在最坏情况下，可能存在某个线程一直获取不到锁。
---------------------
+ condition代替传统的object的wait(),notify()实现线程协作，相比使用object的wait(),notify(),使用condition中的await(),signal()这种方式实现线程之间协作更加安全和高效
+ condition和wait/notify的区别
  + condition可以精确的对多个不同条件进行控制，wait/notify只能和synchronized关键字一起使用，并且只能唤醒一个或多个的等待队列
  + condition需要使用lock进行控制，使用的时候主要lock和unlock，condition不会产生死锁，而wait/notify可能会产生死锁

