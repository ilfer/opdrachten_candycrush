package be.kuleuven.opdracht6.model;

import be.kuleuven.opdracht6.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CandyCrushModel {
    private String speler;
    private List<Candy> speelbord;
    private BoardSize boardSize;
    private int score;

    public CandyCrushModel(String speler, BoardSize boardSize) {
        this.speler = speler;
        this.boardSize = boardSize;
        speelbord = new ArrayList<>();
        score = 0;

        for (int i = 0; i < boardSize.numRows() * boardSize.numColumns(); i++) {
            speelbord.add(generateRandomCandy());
        }
    }

    public String getSpeler() {
        return speler;
    }

    public List<Candy> getSpeelbord() {
        return speelbord;
    }

    public int getScore() {
        return score;
    }

    public void candyWithPositionSelected(Position position) {
        if (position != null) {
            int index = position.toIndex();
            List<Integer> removedIndices = updateNeighbours(index, speelbord.get(index));
            score += removedIndices.size();
            refillEmptyCells(removedIndices);
        } else {
            System.out.println("model:candyWithPositionSelected:positionWasNull");
        }
    }

    public Iterable<Position> getSameNeighbourPositions(Position position) {
        List<Position> result = new ArrayList<>();
        Candy currentCandy = speelbord.get(position.toIndex());

        for (Position neighborPosition : position.neighborPositions()) {
            if (isWithinBoard(neighborPosition)) {
                Candy neighborCandy = speelbord.get(neighborPosition.toIndex());
                if (neighborCandy.equals(currentCandy)) {
                    result.add(neighborPosition);
                }
            }
        }

        return result;
    }

    private List<Integer> updateNeighbours(int index, Candy candy) {
        List<Integer> removedIndices = new ArrayList<>();
        Iterable<Position> neighbours = Position.fromIndex(index, boardSize).neighborPositions();
        List<Integer> neighbourIndices = new ArrayList<>();
        for (Position neighbour : neighbours) {
            neighbourIndices.add(neighbour.toIndex());
        }

        if (neighbourIndices.size() >= 2) {
            neighbourIndices.add(index); // Voeg het geselecteerde snoepje toe aan de te verwijderen lijst
            for (int neighbourIndex : neighbourIndices) {
                speelbord.set(neighbourIndex, null); // Verwijder het snoepje (stel in op null)
                removedIndices.add(neighbourIndex);
            }
        }
        return removedIndices;
    }

    public void refillEmptyCells(List<Integer> removedIndices) {
        for (int index : removedIndices) {
            speelbord.set(index, generateRandomCandy());
        }
    }

    public void resetGame() {
        score = 0;
        speelbord.clear();
        for (int i = 0; i < boardSize.numRows() * boardSize.numColumns(); i++) {
            speelbord.add(generateRandomCandy());
        }
    }

    public int getIndexFromPosition(Position position) {
        return position.toIndex();
    }

    private Candy generateRandomCandy() {
        Random random = new Random();
        int randomType = random.nextInt(5); // Assuming there are 5 types of candies
        switch (randomType) {
            case 0:
                return new NormalCandy(1); // Assuming NormalCandy is one of the implementations of Candy
            case 1:
                return new DoublePointsCandy(2); // Assuming DoublePointsCandy1 is one of the implementations of Candy
            case 2:
                return new DoublePointsRemoveRow(3); // Assuming DoublePointsCandy2 is one of the implementations of Candy
            case 3:
                return new ExtraMoveCandy(4); // Assuming ExtraMoveCandy1 is one of the implementations of Candy
            case 4:
                return new ExtraMoveCandyRemoveBorder(5); // Assuming ExtraMoveCandy2 is one of the implementations of Candy
            default:
                return new NormalCandy(1); // Default to NormalCandy if no valid candy type is generated
        }
    }

    private boolean isWithinBoard(Position position) {
        return position.row() >= 0 && position.row() < boardSize.numRows() &&
                position.column() >= 0 && position.column() < boardSize.numColumns();
    }
}