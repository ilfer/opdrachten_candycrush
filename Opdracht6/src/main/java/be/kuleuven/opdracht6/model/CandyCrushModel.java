package be.kuleuven.opdracht6.model;
import  be.kuleuven.CheckNeighboursInGrid;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CandyCrushModel {
    private String speler;
    private List<Integer> speelbord;
    private int width;
    private int height;
    private int score;

    public CandyCrushModel(String speler) {
        this.speler = speler;
        speelbord = new ArrayList<>();
        width = 4;
        height = 4;
        score = 0;

        for (int i = 0; i < width * height; i++) {
            Random random = new Random();
            int randomGetal = random.nextInt(5) + 1;
            speelbord.add(randomGetal);
        }
    }

    public String getSpeler() {
        return speler;
    }

    public List<Integer> getSpeelbord() {
        return speelbord;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public void candyWithIndexSelected(int index) {
        if (index != -1) {
            List<Integer> removedIndices = updateNeighbours(index, speelbord.get(index));
            score += removedIndices.size();
            refillEmptyCells(removedIndices);
        } else {
            System.out.println("model:candyWithIndexSelected:indexWasMinusOne");
        }
    }

    private List<Integer> updateNeighbours(int index, int candyType) {
        List<Integer> removedIndices = new ArrayList<>();
        Iterable<Integer> neighboursIterable = CheckNeighboursInGrid.getSameNeighboursIds(speelbord, width, height, index);
        List<Integer> neighbours = new ArrayList<>();
        for (Integer neighbour : neighboursIterable) {
            neighbours.add(neighbour);
        }

        if (neighbours.size() >= 2) {
            neighbours.add(index); // Voeg het geselecteerde snoepje toe aan de te verwijderen lijst
            for (int neighbourIndex : neighbours) {
                speelbord.set(neighbourIndex, 0); // Verwijder het snoepje (stel in op 0)
                removedIndices.add(neighbourIndex);
            }
        }
        return removedIndices;
    }

    public void refillEmptyCells(List<Integer> removedIndices) {
        for (int index : removedIndices) {
            Random random = new Random();
            int randomGetal = random.nextInt(5) + 1;
            speelbord.set(index, randomGetal);
        }
    }

    public void resetGame() {
        score = 0;
        speelbord.clear();
        for (int i = 0; i < width * height; i++) {
            Random random = new Random();
            int randomGetal = random.nextInt(5) + 1;
            speelbord.add(randomGetal);
        }
    }

    public int getIndexFromRowColumn(int row, int column) {
        return column + row * width;
    }
}