package be.kuleuven.opdracht6;

import java.util.function.Function;

public class Board<T> {
    private final BoardSize size;
    private final T[][] cells;

    public Board(BoardSize size, Class<T> type) {
        this.size = size;
        this.cells = (T[][]) new Object[size.numRows()][size.numColumns()];
    }

    public T getCellAt(Position position) {
        return cells[position.row()][position.column()];
    }

    public void replaceCellAt(Position position, T newCell) {
        cells[position.row()][position.column()] = newCell;
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

        for (Position position : size.positions()) {
            T cell = this.getCellAt(position);
            otherBoard.replaceCellAt(position, cell);
        }
    }
}
