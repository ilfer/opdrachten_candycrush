package be.kuleuven.opdracht6;


public record DoublePointsCandy(int color) implements Candy {
    @Override
    public int getColor(){
        return color;
    }
}