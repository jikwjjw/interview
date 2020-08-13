+ G1 GC，全称Garbage-First Garbage Collector，通过-XX:+UseG1GC参数来启用，作为体验版随着JDK 6u14版本面世，在JDK 7u4版本发行时被正式推出，相信熟悉JVM的同学们都不会对它感到陌生。在JDK 9中，G1被提议设置为默认垃圾收集器（JEP 248）。
+ 从官网的描述中，我们知道G1是一种服务器端的垃圾收集器，应用在多处理器和大容量内存环境中，在实现高吞吐量的同时，尽可能的满足垃圾收集暂停时间的要求。它是专门针对以下应用场景设计的: 
  + 像CMS收集器一样，能与应用程序线程并发执行。 
  + 整理空闲空间更快。 
  + 需要GC停顿时间更好预测。 
  + 不希望牺牲大量的吞吐性能。 
  + 不需要更大的Java Heap。
+ G1收集器的设计目标是取代CMS收集器，它同CMS相比，在以下方面表现的更出色：
  + G1是一个有整理内存过程的垃圾收集器，不会产生很多内存碎片。
  + G1的Stop The World(STW)更可控，G1在停顿时间上添加了预测机制，用户可以指定期望停顿时间。
-----------------------------
+ 传统的GC收集器将连续的内存空间划分为新生代、老年代和永久代（JDK 8去除了永久代，引入了元空间Metaspace），这种划分的特点是各代的存储地址（逻辑地址，下同）是连续的。
+ 而G1的各代存储地址是不连续的，每一代都使用了n个不连续的大小相同的Region，每个Region占有一块连续的虚拟内存地址。
+ G1 GC模式:
  + 两种都是完全Stop The World的。
  + Young GC：选定所有年轻代里的Region。通过控制年轻代的region个数，即年轻代内存大小，来控制young GC的时间开销。
  + Mixed GC：选定所有年轻代里的Region，外加根据global concurrent marking统计得出收集收益高的若干老年代Region。在用户指定的开销目标范围内尽可能选择收益高的老年代Region。Mixed GC不是full GC，它只能回收部分老年代的Region，如果mixed GC实在无法跟上程序分配内存的速度，导致老年代填满无法继续进行Mixed GC，就会使用serial old GC（full GC）来收集整个GC heap。所以我们可以知道，G1是不提供full GC的。
+ global concurrent marking：它的执行过程类似CMS，但是不同的是，在G1 GC中，它主要是为Mixed GC提供标记服务的，并不是一次GC过程的一个必须环节。global concurrent marking的执行过程分为四个步骤：
  + 初始标记（initial mark，STW）：它标记了从GC Root开始直接可达的对象。
  + 并发标记（Concurrent Marking）：这个阶段从GC Root开始对heap中的对象标记，标记线程与应用程序线程并行执行，并且收集各个Region的存活对象信息
  + 最终标记（Remark，STW）：标记那些在并发标记阶段发生变化的对象，将被回收。
  + 标记清楚（Cleanup）：清除空Region（没有存活对象的），加入到free list。
    + 第一阶段initial mark是共用了Young GC的暂停，这是因为他们可以复用root scan操作，所以可以说global concurrent marking是伴随Young GC而发生的。第四阶段Clean up只是回收了没有存活对象的Region，所以它并不需要STW。
+ 主要的参数：
  + -XX:G1HeapRegionSize=n ：设置Region大小，并非最终值
  + -XX:MaxGCPauseMillis ：设置G1收集过程目标时间，默认值200ms，不是硬性条件
  + -XX:G1NewSizePercent ：新生代最小值，默认值5%
  + -XX:G1MaxNewSizePercent : 新生代最大值，默认值60%
  + -XX:ParallelGCThreads : STW期间，并行GC线程数
  + -XX:ConcGCThreads=n : 并发标记阶段，并行执行的线程数
  + -XX:InitiatingHeapOccupancyPercent : 设置触发标记周期的 Java 堆占用率阈值。默认值是45%。这里的java堆占比指的是non_young_capacity_bytes，包括old+humongous
    
  
  
