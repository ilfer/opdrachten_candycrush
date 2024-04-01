package be.kuleuven.opdracht6;

public record NormalCandy(int color) implements Candy {
    @Override
    public int getColor(){
        return color;
    }
}