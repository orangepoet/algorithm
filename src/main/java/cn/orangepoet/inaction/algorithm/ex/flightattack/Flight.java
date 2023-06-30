package cn.orangepoet.inaction.algorithm.ex.flightattack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public class Flight implements FlightJudge {
    private final Position head;

    private final Set<Position> allPosSet = new HashSet<>();

    private static final Map<String, Flight> FLIGHT_CACHE = new HashMap<>();

    private Flight(Position head,
                   Set<Position> body) {
        this.head = head;

        allPosSet.addAll(body);
        allPosSet.add(head);
    }

    public static Flight get(Direction direction, Position head) {
        String key = String.format("%s-%s", direction, head.toString());
        Flight flight = FLIGHT_CACHE.get(key);
        if (flight == null) {
            flight = new Flight(head, getBody(head, direction));
            FLIGHT_CACHE.put(key, flight);
        }
        return flight;
    }

    private static Set<Position> getBody(Position head, Direction direction) {
        Set<Position> body = new HashSet<>();

        int hx = head.x();
        int hy = head.y();
        switch (direction) {
            case LEFT -> {
                for (int y = hy - 2; y <= hy + 2; y++) {
                    body.add(Position.get(hx + 1, y));
                }
                for (int y = hy - 1; y <= hy + 1; y++) {
                    body.add(Position.get(hx + 3, y));
                }
                body.add(Position.get(hx + 2, hy));
            }
            case RIGHT -> {
                for (int y = hy - 2; y <= hy + 2; y++) {
                    body.add(Position.get(hx - 1, y));
                }
                for (int y = hy - 1; y <= hy + 1; y++) {
                    body.add(Position.get(hx - 3, y));
                }
                body.add(Position.get(hx - 2, hy));
            }
            case UP -> {
                for (int x = hx - 2; x <= hx + 2; x++) {
                    body.add(Position.get(x, hy + 1));
                }
                for (int x = hx - 1; x <= hx + 1; x++) {
                    body.add(Position.get(x, hy + 3));
                }
                body.add(Position.get(hx, hy + 2));
            }
            case DOWN -> {
                for (int x = hx - 2; x <= hx + 2; x++) {
                    body.add(Position.get(x, hy - 1));
                }
                for (int x = hx - 1; x <= hx + 1; x++) {
                    body.add(Position.get(x, hy - 3));
                }
                body.add(Position.get(hx, hy - 2));
            }
        }
        return body;
    }

    @Override
    public boolean isHead(Position p) {
        return this.head.equals(p);
    }

    @Override
    public boolean isBody(Position p) {
        return this.allPosSet.contains(p) && !this.head.equals(p);
    }

    @Override
    public boolean hasPos(Position p) {
        return this.allPosSet.contains(p);
    }

    public boolean isOverlap(Flight flight) {
        if (flight == null) {
            return false;
        }
        return this.allPosSet.stream().anyMatch(flight.allPosSet::contains);
    }

    public Position getHead() {
        return head;
    }

    public Set<Position> allPosSet() {
        return new HashSet<>(this.allPosSet);
    }

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
