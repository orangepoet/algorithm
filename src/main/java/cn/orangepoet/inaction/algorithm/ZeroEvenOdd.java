package cn.orangepoet.inaction.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * 参考：https://leetcode.cn/problems/print-zero-even-odd/solution/xin-hao-liang-fang-shi-java-by-hdlxt-isug/
 *
 * @author chengzhi
 * @date 2022/07/13
 */
@Slf4j
public class ZeroEvenOdd {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final Condition zero = lock1.newCondition();
    private final Condition odd = lock1.newCondition();
    private final Condition even = lock1.newCondition();

    private final int n;

    private volatile int state = 0;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        lock1.lock();
        try {
            for (int i = 1; i <= n; i++) {
                if (state != 0) {
                    zero.await();
                }
                printNumber.accept(0);
                if ((i & 1) == 1) {
                    state = 1;
                    odd.signal();
                } else {
                    state = 2;
                    even.signal();
                }
            }
        } finally {
            lock1.unlock();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        lock1.lock();
        try {
            for (int i = 1; i < n; i += 2) {
                if (state != 1) {
                    odd.await();
                }
                printNumber.accept(i);
                state = 0;
                zero.signal();
            }
        } finally {
            lock1.unlock();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        lock1.lock();
        try {
            for (int i = 2; i <= n; i += 2) {
                if (state != 2) {
                    even.await();
                }
                printNumber.accept(i);
                state = 0;
                zero.signal();
            }
        } finally {
            lock1.unlock();
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
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(8);

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
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(8);

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
