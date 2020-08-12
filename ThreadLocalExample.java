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
