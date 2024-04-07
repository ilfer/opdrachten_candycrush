package be.kuleuven.opdracht6.model;

import be.kuleuven.opdracht6.BoardSize;
import be.kuleuven.opdracht6.Position;
import be.kuleuven.opdracht6.Candy;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CandyCrushModelTests {

    @Test
    public void testGetSpeler() {
        BoardSize boardSize = new BoardSize(4, 4);
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Ferdi", boardSize);

        // Act
        String result = model.getSpeler();

        // Assert
        assertEquals("Ferdi", result);
    }

    @Test
    public void testEmptySpeler() {
        BoardSize boardSize = new BoardSize(4, 4);
        // Arrange
        CandyCrushModel model = new CandyCrushModel(" ", boardSize);

        // Act
        String result = model.getSpeler();

        // Assert
        assertEquals(" ", result);
    }


    @Test
    public void testResetGame() {
        BoardSize boardSize = new BoardSize(4, 4);
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Ferdi", boardSize);
        model.candyWithPositionSelected(new Position(0, 0, boardSize)); // Selecteer een snoepje
        model.candyWithPositionSelected(new Position(0, 1, boardSize)); // Selecteer nog een snoepje

        // Act
        model.resetGame();

        // Assert
        List<Candy> speelbord = model.getSpeelbord();
        assertEquals(16, speelbord.size()); // Het speelbord moet opnieuw gevuld zijn met 16 elementen
        assertEquals(0, model.getScore()); // De score moet gereset zijn naar 0
    }

    @Test
    public void testGetSpeelbord() {
        BoardSize boardSize = new BoardSize(4, 4);
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Ferdi", boardSize);

        // Act
        List<Candy> speelbord = model.getSpeelbord();

        // Assert
        assertNotNull(speelbord); // Speelbord mag niet null zijn
        assertEquals(16, speelbord.size()); // Speelbord moet 16 elementen bevatten
    }


    @Test
    public void testCandyWithIndexSelectedNoMatches() {
        BoardSize boardSize = new BoardSize(4, 4);
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Ferdi", boardSize);
        int initialScore = model.getScore();

        // Act
        model.candyWithPositionSelected(new Position(0, 0, boardSize)); // Selecteer een snoepje zonder overeenkomsten

        // Assert
        assertEquals(initialScore, model.getScore()); // Score mag niet veranderen als er geen overeenkomsten zijn
    }

    @Test
    public void testGetIndexFromRowColumn() {
        BoardSize boardSize = new BoardSize(4, 4);
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Ferdi", boardSize);

        // Act
        Position position = new Position(1, 2, boardSize); // Row = 1, Column = 2
        int index = position.toIndex();

        // Assert
        assertEquals(6, index); // Verwachte index voor Row 1, Column 2 is 6
    }

    @Test
    public void testInitialScoreIsZero() {
        BoardSize boardSize = new BoardSize(4, 4);
        CandyCrushModel model = new CandyCrushModel("Ferdi", boardSize);
        assertEquals(0, model.getScore());
    }

    @Test
    public void testInitialBoardHasCorrectSize() {
        BoardSize boardSize = new BoardSize(4, 4);
        CandyCrushModel model = new CandyCrushModel("Ferdi", boardSize);
        assertEquals(16, model.getSpeelbord().size());
    }

    @Test
    public void testResetGameResetsScoreToZero() {
        BoardSize boardSize = new BoardSize(4, 4);
        CandyCrushModel model = new CandyCrushModel("Ferdi", boardSize);
        model.candyWithPositionSelected(new Position(0, 0, boardSize)); // Selecteer een snoepje
        model.resetGame();
        assertEquals(0, model.getScore());
    }

    @Test
    public void testResetGameResetsBoardToNewState() {
        BoardSize boardSize = new BoardSize(4, 4);
        CandyCrushModel model = new CandyCrushModel("Ferdi", boardSize);
        model.candyWithPositionSelected(new Position(0, 0, boardSize)); // Selecteer een snoepje
        model.resetGame();
        assertEquals(16, model.getSpeelbord().size());
    }


    @Test
    public void testCandyWithIndexSelectedDoesNotIncreaseScoreWhenNoCandiesRemoved() {
        BoardSize boardSize = new BoardSize(4, 4);
        CandyCrushModel model = new CandyCrushModel("Ferdi", boardSize);
        assertEquals(0, model.getScore()); // Initial score is 0
        model.candyWithPositionSelected(new Position(0, 0, boardSize)); // Selecteer een snoepje
        assertEquals(0, model.getScore()); // Score blijft 0 omdat er geen snoepjes zijn verwijderd
    }

}