package cn.orangepoet.inaction.algorithm

import spock.lang.Specification

import static cn.orangepoet.inaction.algorithm.TreeAlgo.SearchTree.add

class TreeAlgoTest extends Specification {

    def '广度遍历'() {
        given:
        /**
         *            4
         *         2     6
         *      1   3   5  7
         */
        def root = new TreeAlgo.Node(4)
        add(2, root)
        add(1, root)
        add(3, root)
        add(6, root)
        add(5, root)
        add(7, root)
        when:
        def list = TreeAlgo.wideFirst(root)

        then:
        list == [4, 2, 6, 1, 3, 5, 7]
    }

    def '先序遍历'() {
        given:
        /**
         *            4
         *         2     6
         *      1   3   5  7
         */
        def root = new TreeAlgo.Node(4)
        add(2, root)
        add(1, root)
        add(3, root)
        add(6, root)
        add(5, root)
        add(7, root)

        def list2 = []
        when:
        def list = TreeAlgo.preOrder(root)
        TreeAlgo.preOrder2(root, list2)

        then:
        list == [4, 2, 1, 3, 6, 5, 7]
        list2 == [4, 2, 1, 3, 6, 5, 7]
    }

    def '中序遍历'() {
        given:
        /**
         *            4
         *         2     6
         *      1   3   5  7
         */
        def root = new TreeAlgo.Node(4)
        add(2, root)
        add(1, root)
        add(3, root)
        add(6, root)
        add(5, root)
        add(7, root)

        def list2 = []

        when:
        def list = TreeAlgo.inorder(root)
        TreeAlgo.inorder2(root, list2)

        then:
        list == [1, 2, 3, 4, 5, 6, 7]
        list2 == [1, 2, 3, 4, 5, 6, 7]
    }

    def '后续遍历'() {
        given:
        /**
         *            4
         *         2     6
         *      1   3   5  7
         */
        def root = new TreeAlgo.Node(4)
        add(2, root)
        add(1, root)
        add(3, root)
        add(6, root)
        add(5, root)
        add(7, root)

        def list2 = []
        when:
        def list = TreeAlgo.postOrder(root)
        TreeAlgo.postOrder2(root, list2)

        then:
        list == [1, 3, 2, 5, 7, 6, 4]
        list2 == [1, 3, 2, 5, 7, 6, 4]
    }

    def '最小堆'() {
        given:
        int[] arr = new int[]{150, 80, 40, 20, 10, 50, 110, 100, 30, 90, 60, 70, 120, 140, 130}
        TreeAlgo.Heap heap = new TreeAlgo.Heap(arr)

        when:
        def min = heap.findMin()
        println(heap.toString())

        then:
        min == 10
    }
}
