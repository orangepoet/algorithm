package cn.orangepoet.inaction.algorithm.flightattack;

import lombok.Getter;
import org.apache.commons.collections4.MapUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
@Getter
public class FlightGame {
    private final int mapSize;
    private final int groupSize;
    private final List<Flight> allFlights = new ArrayList<>();
    private final List<List<Flight>> flightsList = new ArrayList<>();

    private int guessTime = 0;

    public FlightGame(int mapSize, int groupSize) {
        this.mapSize = mapSize;
        this.groupSize = groupSize;
        initFlightSet();
        initFlightGroups();
    }

    public void initFlightSet() {
        for (int y = 1; y <= mapSize; y++) {
            for (int x = 1; x <= mapSize; x++) {
                listFlightByHead(Position.get(x, y)).stream()
                        .filter(this::isInScope)
                        .forEach(this.allFlights::add);
            }
        }
    }

    private void initFlightGroups() {
        Instant start = Instant.now();


        listFlightUnits0(new ArrayList<>());

        Instant end = Instant.now();
        long millis = Duration.between(start, end).toMillis();
        System.out.println("initFlightGroups elapse: " + millis);
    }

    private void listFlightUnits0(List<Flight> flights) {
        if (flights.size() == this.groupSize) {
            this.flightsList.add(new ArrayList<>(flights));
            return;
        }
        Set<Position> groupPositions = flights.stream()
                .flatMap(f -> f.getPositions().stream())
                .collect(Collectors.toSet());
        for (Flight f : this.allFlights) {
            if (f.getPositions().stream().noneMatch(groupPositions::contains)) {
                flights.add(f);
                listFlightUnits0(flights);
                flights.remove(f);
            }
        }
    }

    private void refresh(Position p, MatchResult matchedResult) {
        switch (matchedResult) {
            case NONE ->
                // 排除这个点P!=None的, [Head, Body]
                    this.flightsList.removeIf(flights ->
                            flights.stream().anyMatch(f -> f.getPositions().contains(p))
                    );
            case BODY -> {
                // 排除这个点P=Head的
                this.flightsList.removeIf(flights ->
                        flights.stream().anyMatch(f -> p.equals(f.getHead()))
                );
                // 排除这个点P=None的
                this.flightsList.removeIf(flights ->
                        flights.stream().noneMatch(f -> f.getBody().contains(p))
                );
            }
            case HEAD -> {
                // 排除这个点P=Body的
                this.flightsList.removeIf(flights ->
                        flights.stream().anyMatch(f -> f.getBody().contains(p))
                );
                // 排除这个点P=None的
                this.flightsList.removeIf(flights ->
                        flights.stream().anyMatch(f -> f.getBody().contains(p))
                );
            }
        }
    }

    public List<Flight> listFlightByHead(Position head) {
        return Arrays.asList(
                Flight.get(Flight.Direction.UP, head),
                Flight.get(Flight.Direction.DOWN, head),
                Flight.get(Flight.Direction.LEFT, head),
                Flight.get(Flight.Direction.RIGHT, head)
        );
    }

    public boolean isInScope(Flight flight) {
        return flight.getPositions().stream().allMatch(p ->
                p.x() >= 1 && p.x() <= mapSize && p.y() >= 1 && p.y() <= mapSize);
    }

    public Position guessNext(Set<Position> hintPosSet) {
        Map<Position, Long> posCntMap = flightsList
                .stream()
                .flatMap(Collection::stream)
                .map(Flight::getHead)
                .filter(p -> !hintPosSet.contains(p))
                .collect(Collectors.groupingBy(h -> h, Collectors.counting()));
        if (MapUtils.isEmpty(posCntMap)) {
            return null;
        }
        Optional<Map.Entry<Position, Long>> hintPos = posCntMap.entrySet().stream().max(
                Comparator.comparing(Map.Entry::getValue));
        Position position = hintPos.map(Map.Entry::getKey).orElse(null);
        hintPosSet.add(position);
        return position;
    }


    public boolean guess(List<Flight> flights) {
        List<Flight> aimFlights = new ArrayList<>(flights);

        Set<Position> history = new HashSet<>();
        while (true) {
            Position np = guessNext(history);
            if (np == null) {
                return false;
            }
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
            System.out.println("left case size: " + flightsList.size());

            if (flightsList.size() <= 1) {
                return false;
            }
        }
    }

    public enum MatchResult {
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
