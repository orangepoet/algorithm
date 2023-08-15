package cn.orangepoet.inaction.algorithm

import spock.lang.Specification

class GameTest extends Specification {

    def game = new Game()

    def makeTree() {
        var terminals = [15, 58, 81, 74, 88, 15, 20, 92, 9, 62, 82, 92, 54, 17, 63, 60] as int[]
        Game.Node[] nodes = new Game.Node[terminals.length]
        for (int i = 0; i < terminals.length; i++) {
            nodes[i] = new Game.Node(terminals[i])
        }
        while (nodes.length > 1) {
            Game.Node[] parent = new Game.Node[nodes.length / 2];
            for (int i = 0; i < parent.length; i++) {
                parent[i] = new Game.Node(nodes[i * 2], nodes[i * 2 + 1]);
            }
            nodes = parent
        }
        return nodes[0]
    }

    def '最大最小搜索'() {
        given:
        Game.Node node = makeTree()

        when:
        def bv = game.negMax(node)

        then:
        bv == 60
    }

    def '最大最小AlphBeta剪枝搜索'() {
        given:
        Game.Node node = makeTree()

        when:
        def bv = game.alphaBetaNegMax(node, Integer.MIN_VALUE, Integer.MAX_VALUE)

        then:
        bv == 60
    }
}