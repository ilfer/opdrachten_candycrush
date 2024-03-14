package be.kuleuven.opdracht6.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.ArrayList;

public class CandyCrushModelTests {

    @Test
    public void testGetSpeler() {
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Arne");

        // Act
        String result = model.getSpeler();

        // Assert
        assertEquals("Arne", result);
    }

    @Test
    public void testEmptySpeler() {
        // Arrange
        CandyCrushModel model = new CandyCrushModel(" ");

        // Act
        String result = model.getSpeler();

        // Assert
        assertEquals(" ", result);
    }

    @Test
    public void testCandyWithIndexSelected() {  // faalt
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Arne");
        List<Integer> speelbordBefore = new ArrayList<>(model.getSpeelbord());

        // Act
        model.candyWithIndexSelected(0); // Verwijder snoepje op index 0
        List<Integer> speelbordAfter = model.getSpeelbord();

        // Assert
        assertNotEquals(speelbordBefore, speelbordAfter); // Speelbord moet veranderen na het verwijderen van snoepjes
    }



    @Test
    public void testResetGame() {
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Arne");

        // Act
        model.resetGame();

        // Assert
        List<Integer> speelbord = model.getSpeelbord();
        assertEquals(16, speelbord.size()); // Speelbord moet opnieuw gevuld zijn met 16 elementen
        assertEquals(0, model.getScore()); // Score moet gereset zijn naar 0
    }

    @Test
    public void testGetWidth() {
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Arne");

        // Act
        int width = model.getWidth();

        // Assert
        assertEquals(4, width); // Breedte van het speelbord moet 4 zijn
    }

    @Test
    public void testGetHeight() {
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Arne");

        // Act
        int height = model.getHeight();

        // Assert
        assertEquals(4, height); // Hoogte van het speelbord moet 4 zijn
    }

    @Test
    public void testGetSpeelbord() {
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Arne");

        // Act
        List<Integer> speelbord = model.getSpeelbord();

        // Assert
        assertNotNull(speelbord); // Speelbord mag niet null zijn
        assertEquals(16, speelbord.size()); // Speelbord moet 16 elementen bevatten
    }

    @Test
    public void testCandyWithIndexSelectedInvalidIndex() {
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Arne");
        int initialScore = model.getScore();

        // Act
        model.candyWithIndexSelected(-1); // Selecteer een ongeldige index

        // Assert
        assertEquals(initialScore, model.getScore()); // Score mag niet veranderen bij een ongeldige index
    }

    @Test
    public void testCandyWithIndexSelectedNoMatches() {
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Arne");
        int initialScore = model.getScore();

        // Act
        model.candyWithIndexSelected(0); // Selecteer een snoepje zonder overeenkomsten

        // Assert
        assertEquals(initialScore, model.getScore()); // Score mag niet veranderen als er geen overeenkomsten zijn
    }

    @Test
    public void testRefillEmptyCells() {
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Arne");
        List<Integer> speelbordBefore = model.getSpeelbord();

        // Act
        model.candyWithIndexSelected(0); // Verwijder snoepje op index 0
        model.refillEmptyCells(List.of(0)); // Hervul lege cellen

        // Assert
        List<Integer> speelbordAfter = model.getSpeelbord();
        assertNotEquals(speelbordBefore, speelbordAfter); // Speelbord moet veranderen na het hervullen van lege cellen
    }

    @Test
    public void testGetIndexFromRowColumn() {
        // Arrange
        CandyCrushModel model = new CandyCrushModel("Arne");

        // Act
        int index = model.getIndexFromRowColumn(1, 2); // Row = 1, Column = 2

        // Assert
        assertEquals(6, index); // Verwachte index voor Row 1, Column 2 is 6
    }

    @Test
    public void testInitialScoreIsZero() {
        CandyCrushModel model = new CandyCrushModel("Test");
        assertEquals(0, model.getScore());
    }

    @Test
    public void testInitialBoardHasCorrectSize() {
        CandyCrushModel model = new CandyCrushModel("Test");
        assertEquals(16, model.getSpeelbord().size());
    }

    @Test
    public void testResetGameResetsScoreToZero() {
        CandyCrushModel model = new CandyCrushModel("Test");
        model.candyWithIndexSelected(0);
        model.resetGame();
        assertEquals(0, model.getScore());
    }

    @Test
    public void testResetGameResetsBoardToNewState() {
        CandyCrushModel model = new CandyCrushModel("Test");
        model.candyWithIndexSelected(0);
        model.resetGame();
        assertEquals(16, model.getSpeelbord().size());
    }

    @Test
    public void testCandyWithIndexSelectedIncreasesScoreWhenCandiesRemoved() {  // faalt
        CandyCrushModel model = new CandyCrushModel("Test");
        model.candyWithIndexSelected(0);
        assertEquals(0, model.getScore()); // Assuming no candies were removed initially
        model.candyWithIndexSelected(1);
        assertEquals(2, model.getScore()); // Assuming 2 candies were removed this time
    }

    @Test
    public void testCandyWithIndexSelectedDoesNotIncreaseScoreWhenNoCandiesRemoved() {
        CandyCrushModel model = new CandyCrushModel("Test");
        assertEquals(0, model.getScore()); // Initial score is 0
        model.candyWithIndexSelected(-1); // No candies are removed
        assertEquals(0, model.getScore()); // Score remains 0
    }
}