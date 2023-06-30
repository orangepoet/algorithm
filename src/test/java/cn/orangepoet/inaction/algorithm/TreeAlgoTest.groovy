package cn.orangepoet.inaction.algorithm

import cn.orangepoet.inaction.algorithm.leetcode.TreeNode
import spock.lang.Specification

/**
 * @author chengzhi* @date 2019/10/30
 */
class TreeAlgoTest extends Specification {
    def "树包含节点"() {
        root.print(0)

        boolean isContain = TreeAlgo.contain(root, 4)
        System.out.println(String.format("contain 4: %s", isContain))


    }

    def "节点移除"() {
        when:
        TreeAlgo.Node removedItem = TreeAlgo.remove(root, 4)

        then:
        removedItem != null

    }

    def "发现最小"() {
    }

    def "新增节点"() {
        TreeAlgo.Node newItem = TreeAlgo.add(7, root)
        root.print(0)
    }

    def "广度遍历"() {
        given:
        def node = TreeAlgo.Node.parse([1, 2, 3, 4, 5, 6, 7] as Integer[])
        when:
        def ret = TreeAlgo.wideFirst(node)
        then:
        ret.equals([6, 2, 8, 1, 5] as int[])
    }

    def "深度遍历"() {
    }

    def "中序遍历"() {
        given:
        def root = TreeNode.create([4, 2, 6, 1, 3, 5, 7] as Integer[])

        when:
        def list = root.inorderTravel()
        then:
        list == [1, 2, 3, 4, 5, 6, 7]
    }
}
