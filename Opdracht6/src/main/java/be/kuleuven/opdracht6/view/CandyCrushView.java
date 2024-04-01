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
import java.util.List;
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

        List<Candy> speelbord = model.getSpeelbord();
        for (int row = 0; row < boardSize.numRows(); row++) {
            for (int col = 0; col < boardSize.numColumns(); col++) {
                Candy candy = speelbord.get(row * boardSize.numColumns() + col);
                Position position = new Position(row, col, boardSize); // Maak een nieuwe positie
                Node candyShape = makeCandyShape(position, candy); // Roep makeCandyShape aan met positie en snoepje
                add(candyShape, col, row);
            }
        }
    }



    private void handleMouseClicked(MouseEvent event) {
        Position position = getPositionFromMouseEvent(event);
        if (position != null) {
            model.candyWithPositionSelected(position);
            update();
        }
    }


    public Position getPositionFromMouseEvent(MouseEvent me) {
        double cellWidth = getWidth() / boardSize.numColumns();
        double cellHeight = getHeight() / boardSize.numRows();

        int column = (int) (me.getX() / cellWidth);
        int row = (int) (me.getY() / cellHeight);

        try {
            return new Position(row, column, boardSize);
        } catch (IllegalArgumentException e) {
            // Als de positie ongeldig is, retourneer null en handel dit af in de event handler
            return null;
        }
    }

    private Node makeCandyShape(Position position, Candy candy) {
        Node shape;
        Color color;

        // Bepaal de kleur op basis van het type snoepje
        if (candy instanceof NormalCandy normalCandy) {
            color = switch (candy.getColor()) {
                case 0 -> Color.RED;
                case 1 -> Color.GREEN;
                case 2 -> Color.BLUE;
                case 3 -> Color.YELLOW;
                default -> throw new IllegalArgumentException("Invalid candy.");
            };
            shape = new Circle(20, color);
        } else if (candy instanceof DoublePointsCandy || candy instanceof DoublePointsRemoveRow ||
                candy instanceof ExtraMoveCandy || candy instanceof ExtraMoveCandyRemoveBorder) {
            color = switch (candy.getColor()) {
                case 4 -> Color.GREEN;
                case 5 -> Color.BLUE;
                case 6 -> Color.YELLOW;
                case 7 -> Color.ORANGE;
                default -> throw new IllegalArgumentException("Invalid candy.");
            };
            shape = new Rectangle(40, 40, color);
        } else {
            color = Color.WHITE;
            shape = new Rectangle(40, 40, color); // Default vorm en kleur voor onbekende snoepjes
        }

        shape.setOnMouseClicked(this::handleMouseClicked);

        return shape;
    }



}