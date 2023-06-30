package cn.orangepoet.inaction.algorithm.exam;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同花顺面试
 *
 * @author chengzhi
 * @date 2022/06/10
 */
public class TonghuaShun {

    private static final Object lock = new Object();
    private static volatile TonghuaShun singleton;

    /**
     * 参见  单例{@link cn.orangepoet.inaction.designpattern.singleton.Singleton}
     *
     * @return
     */
    private static TonghuaShun getInstance() {
        if (singleton == null) {
            synchronized (lock) {
                if (singleton == null) {
                    singleton = new TonghuaShun();
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
        ReentrantLock res1 = new ReentrantLock();
        ReentrantLock res2 = new ReentrantLock();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    res1.lock();
                    System.out.println("t1/res1.lock");
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    res2.lock();
                    System.out.println("t1/res2.lock");
                } finally {
                    res2.unlock();
                    res1.unlock();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    res2.lock();
                    System.out.println("t2/res2.lock");
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    res1.lock();
                    System.out.println("t2/res1.lock");
                } finally {
                    res1.unlock();
                    res2.unlock();
                }
            }
        });

        t1.start();
        t2.start();
        countDownLatch.countDown();
    }
}
