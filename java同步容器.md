+ 在Java中，同步容器主要包括两类
  + Vertor，Stack(继承Vertor)，HashTable
  + Collections类提供的静态工厂方法创建的类
----------------------
+ Vector这样的同步容器的所有公共方法都是synchronized的，也就是说，我们可以在多线程场景下并发使用这种方法
+ 虽然同步容器的所有方法都加了锁，但是对这些容器的复合操作无法保证其线程安全性，需要客户端主动加锁来实现。
  + 复合操作：多线程同时操作一个方法内的同步方法，例：delete()中有size（）和remove()，多线程执行deleteLast，即size()，又remove()会出现异常。所以在使用同步容器的时候，如果涉及到多个同时执行删除操作，就要加锁
--------------------
+ 同步容器会存在并发度低的问题，所以可使用java.util.concurrent下的ConcurrentHashMap,LinkedBlockingQueue等原子操作.
  + ConcurrentHashMap定义了线程安全的复合操作，有更好的并发能力，直接使用java.util.concurrent下的ConcurrentHashMap,

  
