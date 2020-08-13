+ 通常情况下，我们创建的变量是可以被任意线程访问修改的。如果创建了一个ThreadLocal变量，那么访问这个变量的每个线程都会有此变量的变量副本，可以使用get()和set()方法来获取默认值或将其值更改为当前线程所存的副本值，从而避免线程问题。
+ 主线程为默认副本值，改变的是其他线程自己的副本值。
+ ThreadLocal内存泄漏问题
    + ThreadLocalMap是ThreadLocal内部静态类
    + ThreadLocalMap中使用key为ThreadLocal的弱引用，而value是强应用，所以ThreadLocal没有被外部强引用的情况下，在回收的的时候，key会被清理掉，而value不会被清理掉，这样来ThreadLocalMap中就会出现key为Null的Entry，假如我们不做任何措施的话，value永远无法被回收，这时候就可能产生内存泄漏。而ThreadLocalMap通过set(),get(),remove()等方法自动清理key为null的记录。
```
import java.text.SimpleDateFormat;
import java.util.Random;

public class ThreadLocalExample implements Runnable {
    // SimpleDateFormat 不是线程安全的，所以每个线程都要有自己独立的副本
    private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal.withInitial(() -> new

            SimpleDateFormat("yyyy MMdd HHmm"));

    @Override
    public void run() {
        formatter.set(new SimpleDateFormat("1"));
        System.out.println("Thread Name= " + Thread.currentThread().getName() + " default Formatter = " + formatter.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //formatter pattern is changed here by thread, but it won't reflect to other threads
        formatter.set(new SimpleDateFormat());

        System.out.println("Thread Name= " + Thread.currentThread().getName() + " formatter = " + formatter.get().toPattern());
    }


    public static void main(String[] args) throws InterruptedException {
        ThreadLocalExample obj = new ThreadLocalExample();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(obj, "" + i);
            Thread.sleep(new Random().nextInt(1000));
            t.start();
        }
        System.out.println("---->"+ThreadLocalExample.formatter.get().toPattern());
    }

    /**
     *
     * 输出：
     *
     * Thread Name= 0 default Formatter = 1
     * Thread Name= 1 default Formatter = 1
     * Thread Name= 1 formatter = yy-M-d ah:mm
     * Thread Name= 2 default Formatter = 1
     * Thread Name= 0 formatter = yy-M-d ah:mm
     * Thread Name= 2 formatter = yy-M-d ah:mm
     * Thread Name= 3 default Formatter = 1
     * ---->yyyy MMdd HHmm //初始化变量的值一直未改变，只改变了每个线程副本的值，通过set改变其线程副本的值。main线程的值就是初始值
     * Thread Name= 4 default Formatter = 1
     * Thread Name= 3 formatter = yy-M-d ah:mm
     * Thread Name= 4 formatter = yy-M-d ah:mm
     */
}
```
