+ 两者都是可重入锁
  + 可重入锁的概念：ReentrantLock意思为可重入锁，指的是一个线程能够对一个临界资源重复加锁
    + 自己可以再次获取自己的内部锁，比如一个线程获得某个对象的锁，此时候，还未实现释放，当其再次想获取这个对象的锁时，还是可以获取的，如果锁不可重入的话，会造成死锁。同一线程每次获取锁，锁的计数器都可能自增1，所以要等到锁的计数器下降为0时，才能释放锁
+ synchronized是依赖于JVM，而ReentrantLock依赖于API，基于JDK层面实现的。
+ synchronized是非公平锁，ReentrantLock默认为非公平锁，可以设置为公平锁。
+ synchronized依赖监视器模式，ReentrantLock依赖AQS
+ synchronized自动释放监视器，ReentrantLock须显示调用unlock
