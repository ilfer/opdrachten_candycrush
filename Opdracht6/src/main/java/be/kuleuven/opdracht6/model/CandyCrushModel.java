package be.kuleuven.opdracht6.model;

import be.kuleuven.opdracht6.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;
import java.util.stream.Stream;

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

    public Set<List<Position>> findAllMatches() {
        Set<List<Position>> matches = new HashSet<>();

        // Horizontal matches
        horizontalStartingPositions().forEach(startPos -> {
            List<Position> match = longestMatchToRight(startPos);
            if (match.size() >= 3) {
                matches.add(match);
            }
        });

        // Vertical matches
        verticalStartingPositions().forEach(startPos -> {
            List<Position> match = longestMatchDown(startPos);
            if (match.size() >= 3) {
                matches.add(match);
            }
        });

        return matches;
    }

    public boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions) {
        Iterator<Position> iterator = positions.iterator();
        return iterator.hasNext() && iterator.next().equals(candy) && iterator.hasNext() && iterator.next().equals(candy);
    }

    public Stream<Position> horizontalStartingPositions() {
        return boardSize.positions().stream().filter(pos -> {
            Stream<Position> leftNeighbors = pos.walkLeft();
            return firstTwoHaveCandy(speelbord.getCellAt(pos), leftNeighbors);
        });
    }

    public Stream<Position> verticalStartingPositions() {
        return boardSize.positions().stream().filter(pos -> {
            Stream<Position> upNeighbors = pos.walkUp();
            return firstTwoHaveCandy(speelbord.getCellAt(pos), upNeighbors);
        });
    }

    public List<Position> longestMatchToRight(Position pos) {
        return pos.walkRight()
                .takeWhile(p -> speelbord.getCellAt(p).equals(speelbord.getCellAt(pos)))
                .toList();
    }

    public List<Position> longestMatchDown(Position pos) {
        return pos.walkDown()
                .takeWhile(p -> speelbord.getCellAt(p).equals(speelbord.getCellAt(pos)))
                .toList();
    }

    public void clearMatch(List<Position> match) {
        // Verwijder de snoepjes van de huidige match
        for (Position position : match) {
            speelbord.replaceCellAt(position, null);
            score++;
        }

        // Hervul lege cellen
        refillEmptyCells(match);

        // Vind nieuwe matches na het verwijderen van de huidige match
        Set<List<Position>> newMatches = findAllMatches();

        // Recursief doorgaan met het verwijderen van matches totdat er geen meer zijn
        for (List<Position> newMatch : newMatches) {
            clearMatch(newMatch);
        }
    }

    public void fallDownTo(Position pos) {
        // Controleer of de huidige positie zich binnen het speelbord bevindt
        if (!isWithinBoard(pos))
            return;

        // Controleer of de huidige positie leeg is
        if (speelbord.getCellAt(pos) == null) {
            // Verplaats het snoepje naar beneden als de huidige positie leeg is
            int row = pos.row();
            int column = pos.column();

            // Vind de volgende niet-lege positie in dezelfde kolom onder de huidige positie
            while (row < boardSize.numRows() && speelbord.getCellAt(new Position(row, column, boardSize)) == null) {
                row++;
            }

            // Als row buiten het bord is of een niet-lege positie heeft gevonden, verplaats dan het snoepje
            if (row == boardSize.numRows() || speelbord.getCellAt(new Position(row, column, boardSize)) != null) {
                row--; // Terug naar de laatste lege positie
            }

            // Verplaats het snoepje naar de lege positie
            speelbord.replaceCellAt(new Position(row, column, boardSize), speelbord.getCellAt(pos));
            speelbord.replaceCellAt(pos, null);
        }

        // Ga verder met de volgende positie naar beneden
        fallDownTo(new Position(pos.row() + 1, pos.column(), boardSize));
    }

    public boolean updateBoard() {
        // Zoek alle matches
        Set<List<Position>> matches = findAllMatches();

        // Als er geen matches zijn, geef false terug
        if (matches.isEmpty())
            return false;

        // Verwijder elke match, laat de overblijvende snoepjes naar beneden vallen en herhaal indien nodig
        for (List<Position> match : matches) {
            clearMatch(match);
            for (Position pos : match) {
                fallDownTo(pos);
            }
        }

        // Herhaal het proces totdat er geen nieuwe matches meer zijn
        return updateBoard();
    }

}