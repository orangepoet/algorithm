package cn.orangepoet.inaction.algorithm.flightattack;

import lombok.NonNull;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public class FlightGame {

    static class TreeNode {
        TreeNode parent;
        Flight flight;
        List<TreeNode> children = new ArrayList<>();

        public TreeNode(Flight f, TreeNode p) {
            this.flight = f;
            this.parent = p;
        }
    }

    private final int mapSize;
    private final int groupSize;
    private final List<Flight> allFlights = new ArrayList<>();
    private final TreeNode root = new TreeNode(null, null);

    private int guessTime = 0;

    public FlightGame(int mapSize, int groupSize) {
        this.mapSize = mapSize;
        this.groupSize = groupSize;
        initFlightSet();
        initTree();
    }

    private void initFlightSet() {
        for (int y = 1; y <= mapSize; y++) {
            for (int x = 1; x <= mapSize; x++) {
                listFlightByHead(Position.get(x, y)).stream()
                        .filter(this::isInScope)
                        .forEach(this.allFlights::add);
            }
        }
    }

    private void initTree() {
        Instant start = Instant.now();
        initTree0(0, 0, root);

        Instant end = Instant.now();
        System.out.println("initFlightGroups elapse: " + Duration.between(start, end).toMillis());
    }


    /**
     * @param depth max: groupSize - 1
     * @param seq
     * @param n
     */
    private boolean initTree0(int depth, int seq, @NonNull TreeNode n) {
        if (depth == groupSize) {
            return true;
        }

        Set<Position> positions = new HashSet<>();
        TreeNode p = n;
        while (p != null && p.flight != null) {
            positions.addAll(p.flight.getPositions());
            p = p.parent;
        }

        for (int i = seq; i < this.allFlights.size(); i++) {
            Flight f = this.allFlights.get(i);

            if (f.getPositions().stream().noneMatch(positions::contains)) {
                TreeNode c = new TreeNode(f, n);
                boolean reachEnd = initTree0(depth + 1, i + 1, c);
                if (reachEnd) {
                    n.children.add(c);
                }
            }
        }
        return !n.children.isEmpty();
    }


    private void refresh(Position p, MatchResult matchedResult) {
        switch (matchedResult) {
            case NONE -> cleanIfFound(p, root);
            case BODY -> cleanIfBodyNotFound(p, root);
            case HEAD -> cleanIfHeadNotFound(p, root);
        }
    }

    private void cleanIfFound(Position p, TreeNode node) {
        if (node.flight != null && node.flight.getPositions().contains(p)) {
            remove0(node.parent, node);
            return;
        }
        List<TreeNode> copy = new ArrayList<>(node.children);
        for (TreeNode child : copy) {
            cleanIfFound(p, child);
        }
    }

    private void cleanIfBodyNotFound(Position p, TreeNode node) {
        if (node.flight != null && node.flight.getBody().contains(p)) {
            return;
        }
        if (node.children.isEmpty()) {
            remove0(node.parent, node);
        } else {
            List<TreeNode> copy = new ArrayList<>(node.children);
            for (TreeNode child : copy) {
                cleanIfBodyNotFound(p, child);
            }
        }
    }

    private void cleanIfHeadNotFound(Position p, TreeNode node) {
        if (node.flight != null && node.flight.getHead().equals(p)) {
            return;
        }
        if (node.children.isEmpty()) {
            remove0(node.parent, node);
        } else {
            List<TreeNode> copy = new ArrayList<>(node.children);
            for (TreeNode child : copy) {
                cleanIfHeadNotFound(p, child);
            }
        }
    }

    private void remove0(TreeNode p, TreeNode c) {
        if (p == null || c == null) {
            return;
        }
        p.children.remove(c);
        if (p.children.isEmpty()) {
            remove0(p.parent, p);
        }
    }


    private List<Flight> listFlightByHead(Position head) {
        return Arrays.asList(
                Flight.get(Flight.Direction.UP, head),
                Flight.get(Flight.Direction.DOWN, head),
                Flight.get(Flight.Direction.LEFT, head),
                Flight.get(Flight.Direction.RIGHT, head));
    }

    private boolean isInScope(Flight flight) {
        return flight.getPositions().stream()
                .allMatch(p -> p.x() >= 1 && p.x() <= mapSize && p.y() >= 1 && p.y() <= mapSize);
    }

    private Position guessNext(Set<Position> history) {
        Map<Position, Integer> pMap = new HashMap<>();
        statPos(pMap, root, history);

        Position p = pMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        return p;
    }

    private void statPos(Map<Position, Integer> pMap, TreeNode node, Set<Position> history) {
        if (node.flight != null && !history.contains(node.flight.getHead())) {
            pMap.compute(node.flight.getHead(), (k, v) -> v == null ? 1 : v + 1);
        }

        for (TreeNode child : node.children) {
            statPos(pMap, child, history);
        }
    }

    private int count(TreeNode n) {
        if (n.children.isEmpty()) {
            return 1;
        }
        int sum = 0;
        for (TreeNode c : n.children) {
            sum += count(c);
        }
        return sum;
    }

    public boolean guess(List<Flight> flights) {
        List<Flight> aimFlights = new ArrayList<>(flights);

        Set<Position> history = new HashSet<>();
        while (true) {
            Position np = guessNext(history);
            if (np == null) {
                return false;
            }

            history.add(np);
            guessTime++;
            MatchResult matchResult = MatchResult.judgePosition(np, flights);

            System.out.println("guess pos: " + np + ", result: " + matchResult);

            if (matchResult == MatchResult.HEAD) {
                aimFlights.removeIf(f -> np.equals(f.getHead()));
                if (aimFlights.isEmpty()) {
                    System.out.println("guess over, success, times: " + this.guessTime);
                    return true;
                }
            }

            refresh(np, matchResult);
            int leftCount = count(root);
            System.out.println("left case size: " + leftCount);

            if (leftCount == 0) {
                return false;
            }
        }
    }

    enum MatchResult {
        UNKNOWN,
        NONE,
        BODY,
        HEAD;

        public static MatchResult judgePosition(Position position, List<Flight> flights) {
            if (flights.stream()
                    .anyMatch(f -> f.getHead().equals(position))) {
                return HEAD;
            }
            if (flights.stream().anyMatch(f -> f.getBody().contains(position))) {
                return BODY;
            }
            return NONE;
        }
    }
}
