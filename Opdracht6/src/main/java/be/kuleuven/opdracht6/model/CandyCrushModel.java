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
            List<Position> matchingPositions = getSameNeighbourPositions(position);
            if (matchingPositions.size() >= 2) {
                clearMatch(matchingPositions);
                updateBoard();
            }
        }
    }

    public void resetGame() {
        score = 0;
        speelbord.fill(this::generateRandomCandy);
    }

    private Candy generateRandomCandy(Position position) {
        Random random = new Random();
        int randomType = random.nextInt(8); // Assuming there are 8 types of candies
        switch (randomType) {
            case 0: return new NormalCandy(0);
            case 1: return new NormalCandy(1);
            case 2: return new NormalCandy(2);
            case 3: return new NormalCandy(3);
            case 4: return new DoublePointsCandy(4);
            case 5: return new DoublePointsRemoveRow(5);
            case 6: return new ExtraMoveCandy(6);
            case 7: return new ExtraMoveCandyRemoveBorder(7);
            default: return new NormalCandy(1);
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

        if (matchingPositions.size() >= 1) {
            matchingPositions.add(position);
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
        while (iterator.hasNext()) {
            Position pos = iterator.next();
            if (speelbord.getCellAt(pos) == null || !speelbord.getCellAt(pos).equals(candy)) {
                return false;
            }
        }
        return true;
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
        List<Position> match = new ArrayList<>();
        match.add(pos);
        return longestMatchToRightHelper(pos, match);
    }

    private List<Position> longestMatchToRightHelper(Position pos, List<Position> match) {
        int row = pos.row();
        int column = pos.column();
        Candy currentCandy = speelbord.getCellAt(pos);

        while (column + 1 < boardSize.numColumns()) {
            Position nextPos = new Position(row, column + 1, boardSize);
            Candy nextCandy = speelbord.getCellAt(nextPos);
            if (nextCandy != null && nextCandy.equals(currentCandy)) {
                match.add(nextPos);
                column++;
            } else {
                break;
            }
        }
        return match;
    }

    public List<Position> longestMatchDown(Position pos) {
        List<Position> match = new ArrayList<>();
        match.add(pos);
        return longestMatchDownHelper(pos, match);
    }

    private List<Position> longestMatchDownHelper(Position pos, List<Position> match) {
        int row = pos.row();
        int column = pos.column();
        Candy currentCandy = speelbord.getCellAt(pos);

        while (row + 1 < boardSize.numRows()) {
            Position nextPos = new Position(row + 1, column, boardSize);
            Candy nextCandy = speelbord.getCellAt(nextPos);
            if (nextCandy != null && nextCandy.equals(currentCandy)) {
                match.add(nextPos);
                row++;
            } else {
                break;
            }
        }
        return match;
    }

    public void clearMatch(List<Position> match) {
        Set<Position> positionsToRemove = new HashSet<>();

        // Add all positions in the match to positionsToRemove
        positionsToRemove.addAll(match);

        // Remove positions from positionsToRemove if they are not part of the match
        for (Position position : match) {
            for (Position neighborPosition : position.neighborPositions()) {
                if (speelbord.getCellAt(neighborPosition) != null && !match.contains(neighborPosition)) {
                    positionsToRemove.remove(neighborPosition);
                }
            }
        }

        // Replace candies at positionsToRemove with null
        for (Position positionToRemove : positionsToRemove) {
            speelbord.replaceCellAt(positionToRemove, null);
            score++;
        }

        // Let candies fall down after removing the match
        for (Position position : match) {
            fallDownTo(position);
        }

        // Find new matches after clearing the current match
        Set<List<Position>> newMatches = findAllMatches();
        for (List<Position> newMatch : newMatches) {
            clearMatch(newMatch);
        }
    }

    public void fallDownTo(Position pos) {
        if (!isWithinBoard(pos))
            return;

        // Controleer of de huidige positie leeg is
        if (speelbord.getCellAt(pos) == null) {
            // Zoek de eerstvolgende niet-lege positie in dezelfde kolom naar boven
            int row = pos.row() - 1;
            while (row >= 0 && speelbord.getCellAt(new Position(row, pos.column(), boardSize)) == null) {
                row--;
            }
            // Als een niet-lege positie wordt gevonden, verplaats het snoepje naar deze positie
            if (row >= 0) {
                Position newPos = new Position(row, pos.column(), boardSize);
                speelbord.replaceCellAt(pos, speelbord.getCellAt(newPos));
                speelbord.replaceCellAt(newPos, null);
                // Roep de methode opnieuw aan voor de nieuwe positie om verder naar beneden te vallen
                fallDownTo(newPos);
            } else {
                // Als er geen niet-lege positie wordt gevonden, blijft het snoepje op zijn huidige positie
                speelbord.replaceCellAt(pos, null);
            }
        } else {
            // Als de huidige positie niet leeg is, ga verder naar de volgende positie naar beneden
            fallDownTo(new Position(pos.row() + 1, pos.column(), boardSize));
        }
    }


    public boolean updateBoard() {
        Set<List<Position>> matches = findAllMatches();
        boolean matchFound = !matches.isEmpty();

        for (List<Position> match : matches) {
            clearMatch(match);
        }

        return matchFound;
    }
}