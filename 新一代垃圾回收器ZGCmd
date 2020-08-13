[新一代垃圾回收器ZGC](https://tech.meituan.com/2020/08/06/new-zgc-practice-in-meituan.html)
+ ZGC（The Z Garbage Collector）是JDK 11中推出的一款低延迟垃圾回收器，它的设计目标包括：
  + 停顿时间不超过10ms；
  + 停顿时间不会随着堆的大小，或者活跃对象的大小而增加；
  + 支持8MB~4TB级别的堆（未来支持16TB）
+ GC之痛:
  + 很多低延迟高可用Java服务的系统可用性经常受GC停顿的困扰。GC停顿指垃圾回收期间STW（Stop The World），当STW时，所有应用线程停止活动，等待GC停顿结束。
    + 降低单次GC时间和降低GC频率两个角度出发进行了调优
+ CMS与G1停顿时间瓶颈
  + CMS新生代的Young GC、G1和ZGC都基于标记-复制算法，但算法具体实现的不同就导致了巨大的性能差异。标记-复制算法应用在CMS新生代（ParNew是CMS默认的新生代垃圾回收器）和G1垃圾回收器中。标记-复制算法可以分为三个阶段：
