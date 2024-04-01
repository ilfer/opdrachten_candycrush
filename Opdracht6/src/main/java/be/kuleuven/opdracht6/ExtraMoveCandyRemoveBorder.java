package be.kuleuven.opdracht6;

public record ExtraMoveCandyRemoveBorder(int color) implements Candy {
    @Override
    public int getColor(){
        return color;
    }
}