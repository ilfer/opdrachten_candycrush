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
}