[从ReentrantLock的实现看AQS的原理及应用](https://tech.meituan.com/2019/12/05/aqs-theory-and-apply.html)
+ AQS的核心思想是
  + 如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并且将共享资源设置为锁定状态，如果请求的共享资源被占用，那么就需要一套线程阻塞等待以及唤醒时锁的分配机制。这个机制AQS使用CLH队列锁实现的，将暂时获取不到锁的线程加入到队列中。
  + CLH队列是虚拟的双向队列(FIFO,虚拟的双向队列不存在队列实例，仅存在节点之间的关联关系)，AQS是将每条请求共享的资源线程封装成一个CLH锁队列的节点来实现锁的分配,AQS使用一个Volatile的int类型的成员变量来表示同步状态，通过内置的FIFO队列来完成资源获取的排队工作，通过CAS完成对State值的修改。
    + State这个字段主要的过程：
      + State初始化的时候为0，表示没有任何线程持有锁。
      + 当有线程持有该锁时，值就会在原来的基础上+1，同一个线程多次获得锁是，就会多次+1，这里就是可重入的概念。
      + 解锁也是对这个字段-1，一直到0，此线程对锁释放。
  ```
  // java.util.concurrent.locks.AbstractQueuedSynchronizer
  private volatile int state;
  ```
+ Java中的大部分同步类（Lock、Semaphore、ReentrantLock等）都是基于AbstractQueuedSynchronizer（简称为AQS）实现的,AQS是一种提供了原子式管理同步状态、阻塞和唤醒线程功能以及队列模型的简单框架
  + ReentrantLock支持公平锁和非公平锁,并且ReentrantLock的底层就是由AQS来实现的
  ```
  // java.util.concurrent.locks.ReentrantLock#NonfairSync
  // 非公平锁
  static final class NonfairSync extends Sync {
	...
	final void lock() {
		if (compareAndSetState(0, 1))
			setExclusiveOwnerThread(Thread.currentThread());
		else
			acquire(1);
		}
  ...
  }
  ```
  + 这块代码的含义为：
    + 若通过CAS设置变量State（同步状态）成功，也就是获取锁成功，则将当前线程设置为独占线程。
    + 若通过CAS设置变量State（同步状态）失败，也就是获取锁失败，则进入Acquire方法进行后续处理。
  + 第一步很好理解，但第二步获取锁失败后，后续的处理策略是怎么样的呢？这块可能会有以下思考：
    + 某个线程获取锁失败的后续流程是什么呢？有以下两种可能：
      + 将当前线程获锁结果设置为失败，获取锁流程结束。这种设计会极大降低系统的并发度，并不满足我们实际的需求。所以就需要下面这种流程，也就是AQS框架的处理流程。
      + 存在某种排队等候机制，线程继续等待，仍然保留获取锁的可能，获取锁流程仍在继续。
 ```
 // java.util.concurrent.locks.ReentrantLock#FairSync
 static final class FairSync extends Sync {
  ...  
  final void lock() {
		acquire(1);
	}
  ...
  }
 ```
+ 结合公平锁和非公平锁的加锁流程，虽然流程上有一定的不同，但是都调用了Acquire方法，而Acquire方法是FairSync和UnfairSync的父类AQS中的核心方法。
+ 加锁：
  + 通过ReentrantLock的加锁方法Lock进行加锁操作。
  + 会调用到内部类Sync的Lock方法，由于Sync#lock是抽象方法，根据ReentrantLock初始化选择的公平锁和非公平锁，执行相关内部类的Lock方法，本质上都会执行AQS的Acquire方法。
  + AQS的Acquire方法会执行tryAcquire方法，但是由于tryAcquire需要自定义同步器实现，因此执行了ReentrantLock中的tryAcquire方法，由于ReentrantLock是通过公平锁和非公平锁内部类实现的tryAcquire方法，因此会根据锁类型不同，执行不同的tryAcquire。
  + tryAcquire是获取锁逻辑，获取失败后，会执行框架AQS的后续逻辑，跟ReentrantLock自定义同步器无关。
+ 解锁
  + 通过ReentrantLock的解锁方法Unlock进行解锁。
  + Unlock会调用内部类Sync的Release方法，该方法继承于AQS。
  + Release中会调用tryRelease方法，tryRelease需要自定义同步器实现，tryRelease只在ReentrantLock中的Sync实现，因此可以看出，释放锁的过程，并不区分是否为公平锁。
  + 释放成功后，所有处理由AQS框架完成，与自定义同步器无关。
 ---------------------------------
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

