package cn.orangepoet.inaction.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author chengzhi
 * @date 2020/05/10
 */
@Slf4j
public class GuessNumber {
    public static final char BLANK = '.';
    private static final Set<Character> numCharSet = new HashSet<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9'));

    public static class TableNumConflictException extends RuntimeException {
    }

    public static class Cell {
        private final int x;
        private final int y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("(%s,%s)", x + 1, y + 1);
        }
    }

    public static void main(String[] args) {
        char[][] table = initTable();

        printTable(table);
        long start = System.currentTimeMillis();
        solveSudoku(table);
        long end = System.currentTimeMillis();
        printTable(table);

        log.info("elapsed: {} ms", end - start);
    }

    private static void printTable(char[][] table) {
        log.info("--------- table -----------");
        for (int i = 0; i < table.length; i++) {
            if (i == 0) {
                log.info("c  -> {}", "1 2 3 4 5 6 7 8 9");
            }
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < table[i].length; j++) {
                row.append(table[i][j]).append(" ");
            }
            log.info("r{} -> {}", i + 1, row.toString());
        }
        log.info("--------- table -----------");
    }

    private static char[][] initTable() {
        Function<String, List<Character>> buildRow = row -> {
            List<Character> result = new ArrayList<>();
            for (int i = 0; i < row.length(); i++) {
                Character cell = row.charAt(i);
                result.add(cell);
            }
            return result;
        };

        List<List<Character>> rows = Arrays.asList(
                buildRow.apply("..46.2..."),
                buildRow.apply("6...3...4"),
                buildRow.apply(".2.4...9."),
                buildRow.apply("98....35."),
                buildRow.apply("1.3....4."),
                buildRow.apply(".6....8.7"),
                buildRow.apply(".3..2..7."),
                buildRow.apply("....61..5"),
                buildRow.apply("..93..4..")

        );

        char[][] table = new char[9][9];
        for (int i = 0; i < rows.size(); i++) {
            List<Character> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                Character cell = row.get(j);
                table[i][j] = cell;
            }
        }
        return table;
    }

    public static void solveSudoku(char[][] board) {
        char[][] resolve = solveSudoku0(board);
        copy(resolve, board);
    }

    public static char[][] solveSudoku0(char[][] board) {
        Map<Cell, List<Character>> guessNumSet = getCellsResolve(board);

        // 单值解
        while (refreshFixedNum(board, guessNumSet)) {
            guessNumSet = getCellsResolve(board);
        }

        // 填充完成
        if (guessNumSet.size() == 0) {
            return board;
        }

        // 多值解
        Map.Entry<Cell, List<Character>> multipleNumSet = guessNumSet.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .min(Comparator.comparingInt(r -> r.getValue().size())).get();

        List<Character> value = multipleNumSet.getValue();

        for (int i = 0; i < value.size(); i++) {
            Character guessValue = value.get(i);

            Cell loc = multipleNumSet.getKey();
            char[][] copy = new char[9][9];
            copy(board, copy);
            copy[loc.x][loc.y] = guessValue;
            //log.info("guess {} at {} ({}/{})", guessValue, loc, i + 1, value.size());
            try {
                char[][] resolve = solveSudoku0(copy);
                if (resolve != null) {
                    return resolve;
                }
            } catch (TableNumConflictException e) {
                //log.error("conflict for {} at {}", guessValue, loc);
            }
        }
        return null;
    }

    private static boolean refreshFixedNum(char[][] table, Map<Cell, List<Character>> guessNumSet) {
        List<Map.Entry<Cell, List<Character>>> fixedNumCells = guessNumSet.entrySet().stream()
                .filter(entry -> entry.getValue().size() == 1)
                .toList();
        if (!fixedNumCells.isEmpty()) {
            fixedNumCells.forEach(entry -> {
                Cell loc = entry.getKey();
                Character value = entry.getValue().get(0);
                table[loc.x][loc.y] = value;

                //log.info("fill_fix, {} at {}", value, loc);
            });
            return true;
        }
        return false;
    }

    private static Map<Cell, List<Character>> getCellsResolve(char[][] table) throws TableNumConflictException {
        Map<Cell, List<Character>> guessNumSet = new HashMap<>();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == BLANK) {
                    List<Character> numSet = getNumSet(table, i, j);
                    if (numSet == null || numSet.isEmpty()) {
                        throw new TableNumConflictException();
                    }
                    guessNumSet.put(new Cell(i, j), numSet);
                }
            }
        }
        return guessNumSet;
    }

    private static List<Character> getNumSet(char[][] table, int i, int j) {
        Set<Character> rows = new HashSet<>();
        for (int k = 0; k < 9; k++) {
            if (k != j) {
                char cell = table[i][k];
                if (cell != '.' && !rows.add(cell)) {
                    throw new TableNumConflictException();
                }
            }
        }

        Set<Character> columns = new HashSet<>();
        for (int k = 0; k < 9; k++) {
            if (k != i) {
                char cell = table[k][j];
                if (cell != '.' && !columns.add(cell)) {
                    throw new TableNumConflictException();
                }
            }
        }

        Set<Character> blocks = new HashSet<>();

        int x = i / 3;
        int y = j / 3;
        for (int m = x * 3; m < x * 3 + 3; m++) {
            for (int n = y * 3; n < y * 3 + 3; n++) {
                char cell = table[m][n];
                if (cell != '.' && !blocks.add(cell)) {
                    throw new TableNumConflictException();
                }
            }
        }

        Set<Character> taken = new HashSet<>();
        taken.addAll(rows);
        taken.addAll(columns);
        taken.addAll(blocks);

        return numCharSet.stream()
                .filter(c -> !taken.contains(c))
                .collect(Collectors.toList());
    }

    private static void copy(char[][] from, char[][] to) {
        for (int i = 0; i < from.length; i++) {
            for (int j = 0; j < from[i].length; j++) {
                to[i][j] = from[i][j];
            }
        }
    }
}
