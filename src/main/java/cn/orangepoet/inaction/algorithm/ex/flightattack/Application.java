package cn.orangepoet.inaction.algorithm.ex.flightattack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public class Application {

    public int guessTimes(FlightMap flightMap, List<FlightUnit> flightUnits, FlightUnit targetFN) {
        int guessWinCnt = 0;
        Set<Position> hintPosSet = new HashSet<>();
        Position guessPos = flightMap.getHintPos(flightUnits, hintPosSet);
        int guessTimes = 1;
        while (guessPos != null) {
            MatchResult matchResult = targetFN.isHead(guessPos) ? MatchResult.HEAD : targetFN.isBody(guessPos)
                    ? MatchResult.BODY : MatchResult.NONE;

            System.out.println("guess pos: " + guessPos + ", result: " + matchResult);

            if (matchResult == MatchResult.HEAD) {
                guessWinCnt++;
                if (guessWinCnt >= 3) {
                    System.out.println("guess over, success, times: " + guessTimes);
                    break;
                }
            }

            flightUnits = flightMap.refreshFlightUnits(flightUnits, guessPos, matchResult);
            System.out.println("left case size: " + flightUnits.size());

            if (flightUnits.size() <= 1) {
                break;
            }

            hintPosSet.add(guessPos);
            guessPos = flightMap.getHintPos(flightUnits, hintPosSet);
            guessTimes++;
        }
        return guessTimes;
    }
}
