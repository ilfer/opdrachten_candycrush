package be.kuleuven.opdracht6;

import javafx.scene.paint.Color;

public record DoublePointsCandy() implements Candy {
    // Implementatie van DoublePointsCandy
    public Color getColor() {
        return Color.LIGHTYELLOW; // Kies een kleur voor ExtraMoveCandyRemoveBorder, bijvoorbeeld lichtgroen
    }
}