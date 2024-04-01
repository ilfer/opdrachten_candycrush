package be.kuleuven.opdracht6;

public record DoublePointsRemoveRow(int color) implements Candy {
    @Override
    public int getColor(){
        return color;
    }
}