package be.kuleuven.opdracht6.model;

import be.kuleuven.opdracht6.BoardSize;
import be.kuleuven.opdracht6.Position;
import be.kuleuven.opdracht6.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CandyCrushBoardTest {
    @Test
    void testGetCellAt() {
        BoardSize size = new BoardSize(3, 3);
        Board<String> board = new Board<>(size, String.class);

        Position position = new Position(1, 1, size);
        board.replaceCellAt(position, "Test");

        assertEquals("Test", board.getCellAt(position));
    }

    @Test
    void testReplaceCellAt() {
        BoardSize size = new BoardSize(3, 3);
        Board<String> board = new Board<>(size, String.class);

        Position position = new Position(1, 1, size);
        board.replaceCellAt(position, "Test");

        assertEquals("Test", board.getCellAt(position));
    }

    @Test
    void testFill() {
        BoardSize size = new BoardSize(3, 3);
        Board<String> board = new Board<>(size, String.class);

        board.fill(position -> "Filled");

        for (Position position : size.positions()) {
            assertEquals("Filled", board.getCellAt(position));
        }
    }

    @Test
    void testCopyTo() {
        BoardSize size = new BoardSize(3, 3);
        Board<String> board1 = new Board<>(size, String.class);
        Board<String> board2 = new Board<>(size, String.class);

        board1.fill(position -> "Filled");

        board1.copyTo(board2);

        for (Position position : size.positions()) {
            assertEquals("Filled", board2.getCellAt(position));
        }
    }

    @Test
    void testCopyToDifferentSize() {
        BoardSize size1 = new BoardSize(3, 3);
        Board<String> board1 = new Board<>(size1, String.class);
        BoardSize size2 = new BoardSize(2, 2);
        Board<String> board2 = new Board<>(size2, String.class);

        assertThrows(IllegalArgumentException.class, () -> board1.copyTo(board2));
    }
}
