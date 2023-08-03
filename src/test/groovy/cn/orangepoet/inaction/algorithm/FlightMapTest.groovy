package cn.orangepoet.inaction.algorithm


import cn.orangepoet.inaction.algorithm.flightattack.Flight
import cn.orangepoet.inaction.algorithm.flightattack.FlightGame
import cn.orangepoet.inaction.algorithm.flightattack.Position
import spock.lang.Specification

/**
 * @author chengzhi* @date 2019/10/24
 */
class FlightMapTest extends Specification {


    def "TestGuess1"() {
        given:
        def game = new FlightGame(10, 3)

        when:
        def flightPuts = List.of(
                Flight.get(Flight.Direction.UP, Position.get(3, 3)),
                Flight.get(Flight.Direction.DOWN, Position.get(6, 6)),
                Flight.get(Flight.Direction.DOWN, Position.get(8, 10))
        )
        def guessResult = game.guess(flightPuts)

        then:
        guessResult
        game.guessTime < 20


    }

    def "TestGuess2"() {
        given:
        def game = new FlightGame(10, 3)

        when:
        def flightPuts = List.of(
                Flight.get(Flight.Direction.LEFT, Position.get(1, 5)),
                Flight.get(Flight.Direction.UP, Position.get(3, 7)),
                Flight.get(Flight.Direction.RIGHT, Position.get(10, 5))
        )
        def guessResult = game.guess(flightPuts)
        then:
        guessResult
        game.guessTime < 20
    }
}
