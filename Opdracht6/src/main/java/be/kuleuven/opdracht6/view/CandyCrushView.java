package be.kuleuven.opdracht6.view;

import be.kuleuven.opdracht6.model.CandyCrushModel;
import be.kuleuven.opdracht6.Candy;
import be.kuleuven.opdracht6.BoardSize;
import be.kuleuven.opdracht6.Position;
import be.kuleuven.opdracht6.NormalCandy;
import be.kuleuven.opdracht6.DoublePointsCandy;
import be.kuleuven.opdracht6.DoublePointsRemoveRow;
import be.kuleuven.opdracht6.ExtraMoveCandy;
import be.kuleuven.opdracht6.ExtraMoveCandyRemoveBorder;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;

public class CandyCrushView extends GridPane {
    private CandyCrushModel model;
    private BoardSize boardSize;

    public CandyCrushView(CandyCrushModel model, BoardSize boardSize) {
        this.model = model;
        this.boardSize = boardSize;
        update();

        setOnMouseClicked(this::handleMouseClicked);
    }

    public void update() {
        getChildren().clear();

        for (int row = 0; row < boardSize.numRows(); row++) {
            for (int col = 0; col < boardSize.numColumns(); col++) {
                Candy candy = model.getSpeelbord().get(row * boardSize.numColumns() + col);
                Node candyShape = makeCandyShape(candy);
                add(candyShape, col, row);
            }
        }
    }

    private void handleMouseClicked(MouseEvent event) {
        Position position = getPositionFromMouseEvent(event);
        model.candyWithPositionSelected(position);
        update();
    }

    public Position getPositionFromMouseEvent(MouseEvent me) {
        double cellWidth = getWidth() / boardSize.numColumns();
        double cellHeight = getHeight() / boardSize.numRows();

        int column = (int) (me.getX() / cellWidth);
        int row = (int) (me.getY() / cellHeight);

        return new Position(row, column, boardSize);
    }

    private Node makeCandyShape(Candy candy) {
        Node shape;
        Color color;

        // Bepaal de kleur op basis van het type snoepje
        if (candy instanceof NormalCandy) {
            color = Color.RED;
        } else if (candy instanceof DoublePointsCandy) {
            color = Color.GREEN;
        } else if (candy instanceof DoublePointsRemoveRow) {
            color = Color.BLUE;
        } else if (candy instanceof ExtraMoveCandy) {
            color = Color.YELLOW;
        } else if (candy instanceof ExtraMoveCandyRemoveBorder) {
            color = Color.ORANGE;
        } else {
            // Default kleur voor onbekende snoepjes
            color = Color.WHITE;
        }

        // Maak de vorm van het snoepje
        if (candy instanceof NormalCandy) {
            Circle circle = new Circle(20);
            circle.setFill(color);
            shape = circle;
        } else {
            Rectangle rectangle = new Rectangle(40, 40);
            rectangle.setFill(color);
            shape = rectangle;
        }

        shape.setOnMouseClicked(this::handleMouseClicked);

        return shape;
    }

}