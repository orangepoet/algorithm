package cn.orangepoet.inaction.algorithm.ex.flightattack;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public interface FlightJudge {
    boolean isHead(Position position);

    boolean isBody(Position position);

    boolean hasPos(Position position);
}
