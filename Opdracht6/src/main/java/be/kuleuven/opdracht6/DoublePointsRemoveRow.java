package be.kuleuven.opdracht6;

import javafx.scene.paint.Color;

public record DoublePointsRemoveRow(int color) implements Candy {
    @Override
    public int getColor(){
        return color;
    }
}