package cn.orangepoet.inaction.algorithm.ex.flightattack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public record Position(int x, int y) {
    private final static Map<String, Position> POSITION_MAP = new HashMap<>();


    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public static Position get(int x, int y) {
        String key = String.format("%s-%s", x, y);
        Position position = POSITION_MAP.get(key);
        if (position == null) {
            position = new Position(x, y);
            POSITION_MAP.put(key, position);
        }
        return position;
    }
}
