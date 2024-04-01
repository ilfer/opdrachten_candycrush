package be.kuleuven.opdracht6;

public record ExtraMoveCandy(int color) implements Candy {
    @Override
    public int getColor(){
        return color;
    }
}