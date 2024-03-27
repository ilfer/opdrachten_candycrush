package be.kuleuven.opdracht6.view;

import be.kuleuven.opdracht6.model.CandyCrushModel;
import be.kuleuven.opdracht6.Candy;
import be.kuleuven.opdracht6.BoardSize;
import be.kuleuven.opdracht6.Position;
import be.kuleuven.opdracht6.NormalCandy;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Iterator;

public class CandyCrushView extends Region {
    private CandyCrushModel model;
    private BoardSize boardSize;

    public CandyCrushView(CandyCrushModel model, BoardSize boardSize) {
        this.model = model;
        this.boardSize = boardSize;
        update();
    }

    public void update() {
        getChildren().clear();
        int i = 0;
        int height = 0;
        Iterator<Candy> iter = model.getSpeelbord().iterator();
        while (iter.hasNext()) {
            Candy candy = iter.next();
            Position position = new Position(height, i, boardSize);
            Node candyShape = makeCandyShape(position, candy);
            getChildren().add(candyShape);

            if (i == boardSize.numColumns() - 1) {
                i = 0;
                height++;
            } else {
                i++;
            }
        }
    }

    public Position getPositionFromMouseEvent(MouseEvent me) {
        Position position = null;
        int row = (int) (me.getY() / getHeightPerCandy());
        int column = (int) (me.getX() / getWidthPerCandy());
        if (row < boardSize.numRows() && column < boardSize.numColumns()) {
            position = new Position(row, column, boardSize);
        }
        return position;
    }

    private Node makeCandyShape(Position position, Candy candy) {
        if (candy instanceof NormalCandy) {
            Circle circle = new Circle(getWidthPerCandy() / 2);
            circle.setFill(Color.RED); // Choose color for NormalCandy
            circle.setCenterX(position.column() * getWidthPerCandy() + getWidthPerCandy() / 2);
            circle.setCenterY(position.row() * getHeightPerCandy() + getHeightPerCandy() / 2);
            return circle;
        } else {
            Rectangle rectangle = new Rectangle(position.column() * getWidthPerCandy(), position.row() * getHeightPerCandy(), getWidthPerCandy(), getHeightPerCandy());
            rectangle.setFill(Color.BLUE); // Choose color for special candies
            return rectangle;
        }
    }

    private double getWidthPerCandy() {
        return getWidth() / boardSize.numColumns();
    }

    private double getHeightPerCandy() {
        return getHeight() / boardSize.numRows();
    }
}