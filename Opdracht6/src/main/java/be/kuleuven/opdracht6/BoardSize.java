package be.kuleuven.opdracht6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public record BoardSize(int numRows, int numColumns) {

    public BoardSize {
        if (numRows <= 0 || numColumns <= 0) {
            throw new IllegalArgumentException("Aantal rijen en kolommen moeten groter zijn dan 0.");
        }
    }

    public Iterable<Position> positions() {
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                positions.add(new Position(i, j, this));
            }
        }
        return positions;
    }

    // Tests
    public static void main(String[] args) {
        testPositions();
    }

    private static void testPositions() {
        BoardSize boardSize = new BoardSize(2, 2);
        Iterable<Position> positions = boardSize.positions();
        Iterator<Position> iterator = positions.iterator();
        Position position1 = iterator.next(); // (0, 0)
        Position position2 = iterator.next(); // (0, 1)
        Position position3 = iterator.next(); // (1, 0)
        Position position4 = iterator.next(); // (1, 1)
        assert position1.row() == 0 && position1.column() == 0;
        assert position2.row() == 0 && position2.column() == 1;
        assert position3.row() == 1 && position3.column() == 0;
        assert position4.row() == 1 && position4.column() == 1;
        System.out.println("positions test geslaagd.");
    }
}