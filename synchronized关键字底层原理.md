+ synchronized关键字属于JVM原理
  + 通过JDK自带的javap命令查看synchronizedDemo类的相关字节码信息
    + 首先切换到对应目录执行 javac synchronizedDemo.java命令生成编译后的.class文件
    + 执行 javap -csvl synchronizedDemo.class即可查看字节码信息
  + javap仅编译class文件，查看java编译器生成的字节码
+ synchronized同步语句块的实现使用的是monitorEnter和monitorExit指令，其中monitorEnter指向同步代码块的开始位置，monitorExit指令则指向同步代码块的结束位置。当执行monitorEnter指令时，线程试图获取锁，也就是monitor的持有权(monitor对象存在于每个java的对象头中，synchronized锁便是通过这种方式获取锁的，也就是为什么java中任意对象作为锁的原因)，当计数器为0则可以获取，获取后将锁计数器设为1，也就是进行加1操作。相应的在执行monitorExit指令后，将锁计数器设为0，表明锁被释放，如果获取对象锁失败，则当前线程就要阻塞等待，直到锁被另一个锁释放为止。
----------------------
```
   /**
     * 同步代码块
     */
    public void synchronizedDemo(){
        synchronized (this){
            System.out.println("同步代码块");
        }
    }
```
```
  /**
     * 同步方法
     */
    public synchronized void synchronizedDemo(){
        System.out.println("同步方法");
    }

```
+ synchronized修饰的方法并没有monitorEnter和monitorExit指令，而有一个ACC_SYNCHRONIIED标识，该标识指明该方法是一个同步方法，JVM通过ACC_SYNCHRONIIED访问标志来辨识一个方法是否为同步方法
+ 字节码文件中，包含访问标志，是否为public，private，final，等
  + 包括实际保存的常量
  + 方法描述
  + 方法访问修饰符
  + 代码
  + 栈的深度
  + 局部变量的个数，方法参数等。
