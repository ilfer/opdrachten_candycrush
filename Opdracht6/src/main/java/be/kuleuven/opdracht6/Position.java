package be.kuleuven.opdracht6;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record Position(int row, int column, BoardSize boardSize) {

    public Position {
        if (row < 0 || row >= boardSize.numRows() || column < 0 || column >= boardSize.numColumns()) {
            throw new IllegalArgumentException("Ongeldige positie op het speelveld.");
        }
    }

    public int toIndex() {
        return row * boardSize.numColumns() + column;
    }

    public static Position fromIndex(int index, BoardSize size) {
        if (index < 0 || index >= size.numRows() * size.numColumns()) {
            throw new IllegalArgumentException("Ongeldige index voor de positie op het speelveld.");
        }
        int row = index / size.numColumns();
        int column = index % size.numColumns();
        return new Position(row, column, size);
    }

    public Iterable<Position> neighborPositions() {
        List<Position> neighbors = new ArrayList<>();

        int[][] directions = { {1, 0}, {0, 1}, {-1, 0}, {0, -1} }; // Down, Right, Up, Left

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newColumn = column + direction[1];
            if (isValidPosition(newRow, newColumn)) {
                neighbors.add(new Position(newRow, newColumn, boardSize));
            }
        }

        return neighbors;
    }

    public boolean isLastColumn() {
        return column == boardSize.numColumns() - 1;
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < boardSize.numRows() && column >= 0 && column < boardSize.numColumns();
    }

    public Stream<Position> walkLeft() {
        return Stream.iterate(this, pos -> new Position(pos.row(), pos.column() - 1, boardSize))
                .limit(column + 1)
                .filter(pos -> isValidPosition(pos.row(), pos.column()));
    }

    public Stream<Position> walkRight() {
        return Stream.iterate(this, pos -> new Position(pos.row(), pos.column() + 1, boardSize))
                .limit(boardSize.numColumns() - column)
                .filter(pos -> isValidPosition(pos.row(), pos.column()));
    }

    public Stream<Position> walkUp() {
        return Stream.iterate(this, pos -> new Position(pos.row() - 1, pos.column(), boardSize))
                .limit(row + 1)
                .filter(pos -> isValidPosition(pos.row(), pos.column()));
    }

    public Stream<Position> walkDown() {
        return Stream.iterate(this, pos -> new Position(pos.row() + 1, pos.column(), boardSize))
                .limit(boardSize.numRows() - row)
                .filter(pos -> isValidPosition(pos.row(), pos.column()));
    }

}