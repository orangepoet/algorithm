package cn.orangepoet.inaction.algorithm.ex.flightattack;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengzhi
 * @date 2019/10/24
 */
public class FlightMap {
    private final int size;

    private FlightMap(int size) {
        this.size = size;
    }

    public static FlightMap create(int size) {
        return new FlightMap(size);
    }

    public List<FlightUnit> listFlightUnits() {
        Instant start = Instant.now();
        List<FlightUnit> result = new ArrayList<>();

        List<Flight> flightSet = getFlightSet();
        for (Flight f1 : flightSet) {
            for (Flight f2 : flightSet) {
                for (Flight f3 : flightSet) {
                    if (f1 != f2 && f2 != f3 && isOverlap(List.of(f1, f2, f3))) {
                        FlightUnit flightUnit = new FlightUnit(Arrays.asList(f1, f2, f3));
                        result.add(flightUnit);
                    }
                }
            }
        }
        Instant end = Instant.now();
        long millis = Duration.between(start, end).toMillis();
        System.out.println("listFlightUnits elapse: " + millis);
        return result;
    }

    private static boolean isOverlap(List<Flight> flights) {
        if (CollectionUtils.isEmpty(flights)) {
            return false;
        }
        if (flights.size() == 1) {
            return false;
        }
        Set<Position> ps = new HashSet<>();
        for (Flight flight : flights) {
            if (!ps.isEmpty() && flight.allPosSet().stream().anyMatch(ps::contains)) {
                return true;
            }
            ps.addAll(flight.allPosSet());
        }
        return false;
    }

    public List<FlightUnit> refreshFlightUnits(List<FlightUnit> flightUnits,
                                               Position pos,
                                               MatchResult matchedResult) {
        List<FlightUnit> matchedFlightUnits = new ArrayList<>(flightUnits);
        switch (matchedResult) {
            case NONE:
                // 排除这个点P!=None的, [Head, Body]
                matchedFlightUnits.removeIf(fn -> fn.hasPos(pos));
                break;
            case BODY:
                // 排除这个点P=Head的
                matchedFlightUnits.removeIf(fn -> fn.isHead(pos));
                // 排除这个点P=None的
                matchedFlightUnits.removeIf(fn -> !fn.hasPos(pos));

                break;
            case HEAD:
                // 排除这个点P=Body的
                matchedFlightUnits.removeIf(fn -> fn.isBody(pos));
                // 排除这个点P=None的
                matchedFlightUnits.removeIf(fn -> !fn.hasPos(pos));
                break;
            default:
                throw new IllegalArgumentException("matchedResult is invalid");
        }
        return matchedFlightUnits;
    }

    public List<Flight> getFlightSet() {
        List<Flight> result = new ArrayList<>();

        for (int y = 1; y <= size; y++) {
            for (int x = 1; x <= size; x++) {
                listFlightByHead(Position.get(x, y)).stream()
                        .filter(this::isInScope)
                        .forEach(result::add);
            }
        }
        return result;
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
        return flight.allPosSet().stream().allMatch(p ->
                p.x() >= 1 && p.x() <= size && p.y() >= 1 && p.y() <= size);
    }

    public void printFlight(Flight flight) {
        System.out.println("---------------------------------");
        for (int y = 1; y <= size; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 1; x <= size; x++) {
                Position position = Position.get(x, y);
                if (flight.isHead(position)) {
                    line.append("  H");
                } else if (flight.isBody(position)) {
                    line.append("  O");
                } else {
                    line.append("  .");
                }
            }
            System.out.println(line.toString());
        }
    }

    public void printFlight(List<Flight> flights) {
        for (int y = 1; y <= size; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 1; x <= size; x++) {
                Position position = Position.get(x, y);
                if (flights.stream().anyMatch(flight -> flight.isHead(position))) {
                    line.append("  H");
                } else if (flights.stream().anyMatch(flight -> flight.isBody(position))) {
                    line.append("  O");
                } else {
                    line.append("  .");
                }
            }
            System.out.println(line.toString());
        }
    }

    public Position getHintPos(List<FlightUnit> flightUnits,
                               Set<Position> hintPosSet) {
        Map<Position, Long> posCntMap = flightUnits
                .stream()
                .flatMap(fn -> fn.flights().stream())
                .map(Flight::getHead)
                .filter(p -> !hintPosSet.contains(p))
                .collect(Collectors.groupingBy(h -> h, Collectors.counting()));
        if (MapUtils.isEmpty(posCntMap)) {
            return null;
        }
        Optional<Map.Entry<Position, Long>> hintPos = posCntMap.entrySet().stream().max(
                Comparator.comparing(Map.Entry::getValue));
        return hintPos.map(Map.Entry::getKey).orElse(null);
    }
}
