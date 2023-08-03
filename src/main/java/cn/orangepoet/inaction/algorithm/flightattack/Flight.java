package cn.orangepoet.inaction.algorithm.flightattack;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
@Getter
public class Flight {
    private final Position head;

    private final Direction direction;

    private final Set<Position> body;

    private final Set<Position> positions;

    private static final Map<String, Flight> FLIGHT_CACHE = new HashMap<>();

    private Flight(Position head,
                   Direction direction,
                   Set<Position> body) {
        this.head = head;
        this.direction = direction;
        this.body = body;

        positions = new HashSet<>(body);
        positions.add(head);
    }

    public static Flight get(Direction direction, Position head) {
        String key = String.format("%s-%s", direction, head.toString());
        Flight flight = FLIGHT_CACHE.get(key);
        if (flight == null) {
            flight = new Flight(head, direction, getBody(head, direction));
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


    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
