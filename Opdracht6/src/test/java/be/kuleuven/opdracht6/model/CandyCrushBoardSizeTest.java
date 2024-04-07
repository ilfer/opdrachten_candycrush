package be.kuleuven.opdracht6.model;

import be.kuleuven.opdracht6.BoardSize;
import be.kuleuven.opdracht6.Position;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CandyCrushBoardSizeTest {
    @Test
    void positionsTest() {
        BoardSize boardSize = new BoardSize(2, 3);

        List<Position> expectedPositions = new ArrayList<>();
        expectedPositions.add(new Position(0, 0, boardSize));
        expectedPositions.add(new Position(0, 1, boardSize));
        expectedPositions.add(new Position(0, 2, boardSize));
        expectedPositions.add(new Position(1, 0, boardSize));
        expectedPositions.add(new Position(1, 1, boardSize));
        expectedPositions.add(new Position(1, 2, boardSize));

        Iterable<Position> actualPositions = boardSize.positions();
        for (Position position : actualPositions) {
            assertTrue(expectedPositions.contains(position));
        }
    }
}
