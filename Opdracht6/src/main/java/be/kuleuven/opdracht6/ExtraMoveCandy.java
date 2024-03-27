package be.kuleuven.opdracht6;

import javafx.scene.paint.Color;

public record ExtraMoveCandy() implements Candy {
    // Implementatie van ExtraMoveCandy
    public Color getColor() {
        return Color.LIGHTBLUE; // Kies een kleur voor ExtraMoveCandyRemoveBorder, bijvoorbeeld lichtgroen
    }
}