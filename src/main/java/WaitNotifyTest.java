/**
 * 1.wait( )，notify( )，notifyAll( )都不属于Thread类，而是属于Object基础类，也就是每个对象都有wait( )，notify( )，notifyAll( ) 的功能
 * ，因为每个对象都有锁，锁是每个对象的基础，当然操作锁的方法也是最基础了。当需要
 * 2.调用上述方法时需要持有对象的锁,否则会抛出以下异常
 * Exception in thread "main" java.lang.IllegalMonitorStateException
 * at java.lang.Object.wait(Native Method)
 * at java.lang.Object.wait(Object.java:502)
 * 3.在while循环里而不是if语句下使用wait，这样，会在线程暂停恢复后再次检查wait的条件，并在条件实际上并未改变的情况下处理唤醒通知
 * Created by 87627 on 2018/8/19.
 */
public class WaitNotifyTest {

    // 在多线程间共享的对象上使用wait
    private String[] shareObj = {"true"};

    public static void main(String[] args) {
        WaitNotifyTest test = new WaitNotifyTest();
        ThreadWait threadWait1 = test.new ThreadWait("wait thread1");
        threadWait1.setPriority(2);
        ThreadWait threadWait2 = test.new ThreadWait("wait thread2");
        threadWait2.setPriority(3);
        ThreadWait threadWait3 = test.new ThreadWait("wait thread3");
        threadWait3.setPriority(4);

        ThreadNotify threadNotify = test.new ThreadNotify("notify thread");

        threadNotify.start();
        threadWait1.start();
        threadWait2.start();
        threadWait3.start();
    }

    class ThreadWait extends Thread {

        public ThreadWait(String name) {
            super(name);
        }

        public void run() {
            synchronized (shareObj) {
                if ( "true".equals(shareObj[0])) {
                    long startTime = System.currentTimeMillis();
                    System.out.println("线程" + this.getName() + "开始等待");
                    try {
                        // 释放锁
                        shareObj.wait();
                        // 此后处于阻塞状态
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long endTime = System.currentTimeMillis();
                    System.out.println("线程" + this.getName()
                            + "等待时间为：" + (endTime - startTime));
                }
            }
            System.out.println("线程" + getName() + "等待结束");
        }
    }

    class ThreadNotify extends Thread {

        public ThreadNotify(String name) {
            super(name);
        }


        public void run() {
            try {
                // 给等待线程等待时间
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (shareObj) {
                System.out.println("线程" + this.getName() + "开始准备通知");
                shareObj[0] = "false";
                shareObj.notifyAll();
                System.out.println("线程" + this.getName() + "通知结束");
            }
            System.out.println("线程" + this.getName() + "运行结束");
        }
    }
}