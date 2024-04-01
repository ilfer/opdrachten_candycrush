package be.kuleuven.opdracht6;

public sealed interface Candy permits NormalCandy, DoublePointsCandy, ExtraMoveCandy, DoublePointsRemoveRow, ExtraMoveCandyRemoveBorder {
    int getColor();
}