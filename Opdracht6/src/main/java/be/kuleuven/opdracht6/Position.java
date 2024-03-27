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

    // Tests
    public static void main(String[] args) {
        testToIndex();
        testFromIndex();
        testNeighborPositions();
        testIsLastColumn();
    }

    private static void testToIndex() {
        BoardSize boardSize = new BoardSize(2, 4);
        Position position = new Position(1, 2, boardSize); // Row 1, Column 2
        assert position.toIndex() == 6;
        System.out.println("toIndex test geslaagd.");
    }

    private static void testFromIndex() {
        BoardSize boardSize = new BoardSize(2, 4);
        Position position = Position.fromIndex(6, boardSize); // Index 6 (Row 1, Column 2)
        assert position.row() == 1 && position.column() == 2;
        System.out.println("fromIndex test geslaagd.");
    }

    private static void testNeighborPositions() {
        BoardSize boardSize = new BoardSize(3, 3);
        Position position = new Position(1, 1, boardSize); // Row 1, Column 1
        List<Position> expectedNeighbors = List.of(
                new Position(0, 1, boardSize), // Above
                new Position(1, 2, boardSize), // Right
                new Position(2, 1, boardSize), // Below
                new Position(1, 0, boardSize)  // Left
        );
        Iterable<Position> neighbors = position.neighborPositions();
        assert neighbors.equals(expectedNeighbors);
        System.out.println("neighborPositions test geslaagd.");
    }

    private static void testIsLastColumn() {
        BoardSize boardSize = new BoardSize(3, 3);
        Position position1 = new Position(1, 2, boardSize); // Row 1, Last Column
        Position position2 = new Position(1, 1, boardSize); // Row 1, Not Last Column
        assert position1.isLastColumn();
        assert !position2.isLastColumn();
        System.out.println("isLastColumn test geslaagd.");
    }
}