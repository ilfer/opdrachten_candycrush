package be.kuleuven.opdracht6;

import javafx.scene.paint.Color;

public record ExtraMoveCandyRemoveBorder() implements Candy {
    // Implementatie van ExtraMoveCandyRemoveBorder

    // Geef een kleur terug voor het snoepje
    public Color getColor() {
        return Color.LIGHTCYAN; // Kies een kleur voor ExtraMoveCandyRemoveBorder, bijvoorbeeld lichtgroen
    }
}