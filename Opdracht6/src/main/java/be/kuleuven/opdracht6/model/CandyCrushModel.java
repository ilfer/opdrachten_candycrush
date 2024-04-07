package be.kuleuven.opdracht6.model;

import be.kuleuven.opdracht6.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CandyCrushModel {
    private String speler;
    private Board<Candy> speelbord;
    private BoardSize boardSize;
    private int score;

    public CandyCrushModel(String speler, BoardSize boardSize) {
        this.speler = speler;
        this.boardSize = boardSize;
        speelbord = new Board<>(boardSize, Candy.class);
        speelbord.fill(this::generateRandomCandy);
        score = 0;
    }

    public String getSpeler() {
        return speler;
    }

    public List<Candy> getSpeelbord() {
        List<Candy> candyList = new ArrayList<>();
        for (Position position : boardSize.positions()) {
            candyList.add(speelbord.getCellAt(position));
        }
        return candyList;
    }

    public int getScore() {
        return score;
    }

    public void candyWithPositionSelected(Position position) {
        if (position != null) {
            int index = position.toIndex();
            Candy selectedCandy = speelbord.getCellAt(position);
            List<Position> matchingPositions = getSameNeighbourPositions(position);
            if (matchingPositions.size() >= 2) {
                // Verwijder de overeenkomende snoepjes en verhoog de score
                for (Position matchingPosition : matchingPositions) {
                    int matchingIndex = matchingPosition.toIndex();
                    speelbord.replaceCellAt(matchingPosition, null);
                    score++;
                }
                // Hervul lege cellen
                refillEmptyCells(matchingPositions);
            }
        }
    }

    public void refillEmptyCells(List<Position> removedPositions) {
        for (Position position : removedPositions) {
            speelbord.replaceCellAt(position, generateRandomCandy(position));
        }
    }


    public void resetGame() {
        score = 0;
        speelbord.fill(this::generateRandomCandy);
    }

    private Candy generateRandomCandy(Position position) {
        Random random = new Random();
        int randomType = random.nextInt(8); // Assuming there are 5 types of candies
        switch (randomType) {
            case 0:
                return new NormalCandy(0); // Assuming NormalCandy is one of the implementations of Candy
            case 1:
                return new NormalCandy(1); // Assuming NormalCandy is one of the implementations of Candy
            case 2:
                return new NormalCandy(2); // Assuming NormalCandy is one of the implementations of Candy
            case 3:
                return new NormalCandy(3); // Assuming NormalCandy is one of the implementations of Candy
            case 4:
                return new DoublePointsCandy(4); // Assuming DoublePointsCandy is one of the implementations of Candy
            case 5:
                return new DoublePointsRemoveRow(5); // Assuming DoublePointsRemoveRow is one of the implementations of Candy
            case 6:
                return new ExtraMoveCandy(6); // Assuming ExtraMoveCandy is one of the implementations of Candy
            case 7:
                return new ExtraMoveCandyRemoveBorder(7); // Assuming ExtraMoveCandyRemoveBorder is one of the implementations of Candy
            default:
                return new NormalCandy(1); // Default to NormalCandy if no valid candy type is generated
        }
    }

    public List<Position> getSameNeighbourPositions(Position position) {
        List<Position> matchingPositions = new ArrayList<>();
        Candy currentCandy = speelbord.getCellAt(position);

        for (Position neighborPosition : position.neighborPositions()) {
            if (isWithinBoard(neighborPosition)) {
                Candy neighborCandy = speelbord.getCellAt(neighborPosition);
                if (neighborCandy != null && neighborCandy.equals(currentCandy)) {
                    matchingPositions.add(neighborPosition);
                }
            }
        }

        return matchingPositions;
    }


    private boolean isWithinBoard(Position position) {
        return position.row() >= 0 && position.row() < boardSize.numRows() &&
                position.column() >= 0 && position.column() < boardSize.numColumns();
    }
}