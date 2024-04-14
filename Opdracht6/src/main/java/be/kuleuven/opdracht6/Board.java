package be.kuleuven.opdracht6;

import java.util.function.Function;
import java.util.*;

public class Board<T> {
    private final BoardSize size;
    private final Map<Position, T> cellsMap;
    private final Map<T, Set<Position>> elementsPositionsMap;

    public Board(BoardSize size, Class<T> type) {
        this.size = size;
        this.cellsMap = new HashMap<>();
        this.elementsPositionsMap = new HashMap<>();
    }

    public T getCellAt(Position position) {
        return cellsMap.get(position);
    }

    public void replaceCellAt(Position position, T newCell) {
        T oldCell = cellsMap.get(position);
        if (oldCell != null) {
            // Remove old position from elementsPositionsMap
            Set<Position> positions = elementsPositionsMap.get(oldCell);
            if (positions != null) {
                positions.remove(position);
                if (positions.isEmpty()) {
                    elementsPositionsMap.remove(oldCell);
                }
            }
        }

        cellsMap.put(position, newCell);

        // Update elementsPositionsMap with new position
        elementsPositionsMap.computeIfAbsent(newCell, k -> new HashSet<>()).add(position);
    }

    public void fill(Function<Position, T> cellCreator) {
        for (Position position : size.positions()) {
            T cell = cellCreator.apply(position);
            replaceCellAt(position, cell);
        }
    }

    public void copyTo(Board<T> otherBoard) {
        if (!this.size.equals(otherBoard.size)) {
            throw new IllegalArgumentException("Boards have different sizes.");
        }

        for (Map.Entry<Position, T> entry : cellsMap.entrySet()) {
            Position position = entry.getKey();
            T cell = entry.getValue();
            otherBoard.replaceCellAt(position, cell);
        }
    }

    public Collection<Position> getPositionsOfElement(T element) {
        Set<Position> positions = elementsPositionsMap.getOrDefault(element, Collections.emptySet());
        return Collections.unmodifiableCollection(positions);
    }
}