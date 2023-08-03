package cn.orangepoet.inaction.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * 参考：https://leetcode.cn/problems/print-zero-even-odd/solution/xin-hao-liang-fang-shi-java-by-hdlxt-isug/
 *
 * @author chengzhi
 * @date 2022/07/13
 */
@Slf4j
public class ZeroEvenOdd2 {
    private final Semaphore zero = new Semaphore(1);
    private final Semaphore odd = new Semaphore(0);
    private final Semaphore even = new Semaphore(0);

    private final int n;

    public ZeroEvenOdd2(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zero.acquire();
            printNumber.accept(0);
            if ((i & 1) == 1) {
                odd.release();
            } else {
                even.release();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i < n; i += 2) {
            odd.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            even.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }

    public static void main(String[] args) {
        IntConsumer consumer = (int i) -> log.info("print: {}", i);
        test1(consumer);
        test2(consumer);
    }

    private static void test1(IntConsumer consumer) {
        log.info("---------------- test1 ---------------");
        CountDownLatch cdl = new CountDownLatch(3);
        ZeroEvenOdd2 zeroEvenOdd = new ZeroEvenOdd2(8);

        new Thread(() -> {
            try {
                zeroEvenOdd.even(consumer);
                cdl.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "even").start();
        new Thread(() -> {
            try {
                zeroEvenOdd.odd(consumer);
                cdl.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "odd").start();
        new Thread(() -> {
            try {
                zeroEvenOdd.zero(consumer);
                cdl.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "zero").start();

        try {
            cdl.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("---------------- test1 ---------------");
    }

    private static void test2(IntConsumer consumer) {
        log.info("---------------- test2 ---------------");
        CountDownLatch cdl = new CountDownLatch(3);
        ZeroEvenOdd2 zeroEvenOdd = new ZeroEvenOdd2(8);

        new Thread(() -> {
            try {
                zeroEvenOdd.even(consumer);
                cdl.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "even").start();
        new Thread(() -> {
            try {
                zeroEvenOdd.zero(consumer);
                cdl.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "zero").start();
        new Thread(() -> {
            try {
                zeroEvenOdd.odd(consumer);
                cdl.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "odd").start();
        try {
            cdl.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("---------------- test2 ---------------");
    }
}
