package cn.orangepoet.inaction.algorithm.ex.flightattack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public record FlightUnit(List<Flight> flights) implements FlightJudge {
    @Override
    public List<Flight> flights() {
        return new ArrayList<>(flights);
    }

    @Override
    public boolean isHead(Position position) {
        return this.flights.stream()
                .anyMatch(f -> f.isHead(position));
    }

    @Override
    public boolean isBody(Position position) {
        return this.flights.stream()
                .anyMatch(f -> f.isBody(position));
    }

    @Override
    public boolean hasPos(Position position) {
        return this.flights.stream()
                .anyMatch(f -> f.hasPos(position));
    }
}
