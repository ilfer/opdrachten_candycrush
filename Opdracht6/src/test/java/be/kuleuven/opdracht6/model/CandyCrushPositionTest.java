package be.kuleuven.opdracht6.model;

import be.kuleuven.opdracht6.BoardSize;
import be.kuleuven.opdracht6.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CandyCrushPositionTest {
    @Test
    void testToIndex() {
        BoardSize boardSize = new BoardSize(2, 4);
        Position position = new Position(1, 2, boardSize);
        assertEquals(6, position.toIndex());
    }

    @Test
    void testFromIndex_ValidIndex() {
        BoardSize boardSize = new BoardSize(2, 4);
        Position position = Position.fromIndex(5, boardSize);
        assertEquals(1, position.row());
        assertEquals(1, position.column());
    }

    @Test
    void testFromIndex_InvalidIndex() {
        BoardSize boardSize = new BoardSize(2, 4);
        assertThrows(IllegalArgumentException.class, () -> Position.fromIndex(8, boardSize));
    }

    @Test
    void testNeighborPositions() {
        BoardSize boardSize = new BoardSize(3, 3);
        Position position = new Position(1, 1, boardSize);
        Iterable<Position> neighbors = position.neighborPositions();
        int count = 0;
        for (Position neighbor : neighbors) {
            count++;
        }
        assertEquals(4, count); // Aangezien er 4 buren zijn in een 3x3 bord
    }

    @Test
    void testIsLastColumn() {
        BoardSize boardSize = new BoardSize(2, 3);
        Position position = new Position(1, 2, boardSize);
        assertTrue(position.isLastColumn());
    }
}
