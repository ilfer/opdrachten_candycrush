package be.kuleuven.opdracht6;

import java.util.ArrayList;
import java.util.List;

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

}