package cn.orangepoet.inaction.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简易 自实现令牌桶
 *
 * @author chengzhi
 * @date 2022/06/23
 */
@Slf4j
public class RateLimiter {
    private final int capacity;
    private final double rate;

    private int left;
    private long lastTime;

    /**
     * @param capacity
     * @param rate     令牌产生速率， 单位：个/毫秒
     */
    public RateLimiter(int capacity, double rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.lastTime = System.currentTimeMillis();
    }

    public boolean isAllowed() {
        return isAllowed(1);
    }

    public boolean isAllowed(int count) {
        long now = System.currentTimeMillis();
        int tokens = (int) Math.min(capacity, left + (now - lastTime) * rate);

        if (tokens >= count) {
            this.left = tokens - count;
            this.lastTime = now;
            log.info("count:{}, token:{}->{}", count, tokens, this.left);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        Random rnd = new Random();
        RateLimiter rateLimiter = new RateLimiter(100, 10.0 / 1000);

        AtomicInteger total = new AtomicInteger();
        new Thread(() -> mockManyTimes(now, rnd, rateLimiter, total)).start();
        new Thread(() -> mockManyTimes(now, rnd, rateLimiter, total)).start();
        new Thread(() -> mockManyTimes(now, rnd, rateLimiter, total)).start();
        new Thread(() -> mockManyTimes(now, rnd, rateLimiter, total)).start();
        new Thread(() -> mockManyTimes(now, rnd, rateLimiter, total)).start();
    }

    private static void mockManyTimes(long now, Random rnd, RateLimiter rateLimiter, AtomicInteger total) {
        while (true) {
            invoke(rateLimiter, rnd.nextInt(3) + 1, total);
            interval();
            if (System.currentTimeMillis() - now > 10000) {
                log.info("stop");
                break;
            }
        }
    }

    private static void invoke(RateLimiter rateLimiter, int count, AtomicInteger total) {
        boolean allowed = rateLimiter.isAllowed(count);
        for (int i = 0; i < count; i++) {
            total.incrementAndGet();
        }
        log.info("invoke... count: {}, total: {}, allowed: {}", count, total.get(), allowed);
    }

    private static void interval() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
