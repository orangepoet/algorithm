package cn.orangepoet.inaction.algorithm.ex

import cn.orangepoet.inaction.algorithm.ex.flightattack.*
import spock.lang.Specification

/**
 * @author chengzhi* @date 2019/10/24
 */
class FlightMapTest extends Specification {


    def "TestGuess"() {
        given:
        def starter = new Application()
        def flightMap = FlightMap.create(10)
        def flightUnits = flightMap.listFlightUnits()

        when:
        def targetFN = new FlightUnit(Arrays.asList(
                Flight.get(Flight.Direction.UP, Position.get(3, 3)),
                Flight.get(Flight.Direction.DOWN, Position.get(6, 6)),
                Flight.get(Flight.Direction.DOWN, Position.get(8, 10))
        ))
        def times = starter.guessTimes(flightMap, flightUnits, targetFN)

        then:
        times < 20


        when:
        def targetFN2 = new FlightUnit(Arrays.asList(
                Flight.get(Flight.Direction.LEFT, Position.get(1, 5)),
                Flight.get(Flight.Direction.UP, Position.get(3, 7)),
                Flight.get(Flight.Direction.RIGHT, Position.get(10, 5))
        ))
        def times2 = starter.guessTimes(flightMap, flightUnits, targetFN2)
        then:
        times2 < 20
    }


    def "RefreshFlightUnits"() {
        def flightMap = FlightMap.create(10)

        FlightUnit targetFN = new FlightUnit(Arrays.asList(
                Flight.get(Flight.Direction.UP, Position.get(3, 3)),
                Flight.get(Flight.Direction.DOWN, Position.get(6, 6)),
                Flight.get(Flight.Direction.DOWN, Position.get(8, 10))
        ))
        List<FlightUnit> testFNList = Arrays.asList(targetFN)

        expect:
        flightMap.refreshFlightUnits(testFNList, pos, matchResult).size() == n
        where:
        pos                | matchResult      | n
        Position.get(4, 4) | MatchResult.BODY | 1
        Position.get(4, 2) | MatchResult.NONE | 1
        Position.get(4, 6) | MatchResult.BODY | 1
        Position.get(2, 6) | MatchResult.BODY | 1
        Position.get(2, 4) | MatchResult.BODY | 1
        Position.get(3, 5) | MatchResult.BODY | 1
        Position.get(7, 3) | MatchResult.BODY | 1
        Position.get(6, 3) | MatchResult.BODY | 1
        Position.get(8, 3) | MatchResult.NONE | 1
        Position.get(5, 3) | MatchResult.BODY | 1
        Position.get(5, 4) | MatchResult.BODY | 1
        Position.get(5, 5) | MatchResult.BODY | 1
        Position.get(4, 3) | MatchResult.NONE | 1
        Position.get(6, 5) | MatchResult.BODY | 1
        Position.get(6, 6) | MatchResult.HEAD | 1
        Position.get(4, 5) | MatchResult.BODY | 1
        Position.get(7, 5) | MatchResult.BODY | 1
        Position.get(3, 3) | MatchResult.HEAD | 1
        Position.get(3, 4) | MatchResult.BODY | 1
        Position.get(3, 6) | MatchResult.BODY | 1
        Position.get(8, 5) | MatchResult.BODY | 1
        Position.get(6, 4) | MatchResult.BODY | 1
        Position.get(7, 8) | MatchResult.NONE | 1
        Position.get(5, 9) | MatchResult.NONE | 1
        Position.get(5, 8) | MatchResult.NONE | 1
        Position.get(9, 7) | MatchResult.BODY | 1
        Position.get(9, 9) | MatchResult.BODY | 1
        Position.get(8, 7) | MatchResult.BODY | 1
        Position.get(8, 8) | MatchResult.BODY | 1
        Position.get(8, 9) | MatchResult.BODY | 1
        Position.get(7, 7) | MatchResult.BODY | 1
    }
}
