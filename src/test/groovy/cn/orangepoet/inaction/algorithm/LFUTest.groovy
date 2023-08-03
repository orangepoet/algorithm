package cn.orangepoet.inaction.algorithm

import spock.lang.Specification

class LFUTest extends Specification {
    def '测试LFU'() {
        given:
        LFU lfu = new LFU(2)

        when:
        lfu.put(1, 1)
        lfu.put(2, 2)
        lfu.get(1)
        lfu.get(1)

        lfu.put(3, 3)
        lfu.get(3)

        def val = lfu.get(1)
        def val2 = lfu.get(2)
        def val3 = lfu.get(3)

        then:
        val == 1
        val2 == -1
        val3 == 3
    }

    def '测试LRU2'() {
        given:
        LRU2 lru = new LRU2(2)

        when:
        lru.put(2, 1)
        lru.put(1, 1)
        lru.put(2, 3)
        lru.put(4, 1)
        def val = lru.get(1)
        def val2 = lru.get(2)

        then:
        val == -1
        val2 == 3
    }
}
