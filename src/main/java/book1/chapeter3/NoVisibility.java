package book1.chapeter3;

/**
 * 简介：
 * 对象的共享
 *
 * @author 87627
 * @create 2018/8/19
 * @since 1.0.0
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {


            new ReaderThread().start();
            number = 42;
            ready = true;
        }
    }
}
