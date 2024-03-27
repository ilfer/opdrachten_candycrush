package be.kuleuven.opdracht6;

import javafx.scene.paint.Color;

public record DoublePointsRemoveRow() implements Candy {
    // Implementatie van DoublePointsCandy
    public Color getColor() {
        return Color.LIGHTGREEN; // Kies een kleur voor ExtraMoveCandyRemoveBorder, bijvoorbeeld lichtgroen
    }
}