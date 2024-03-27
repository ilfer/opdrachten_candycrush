package be.kuleuven.opdracht6;

public record NormalCandy(int color) implements Candy {
    public NormalCandy {
        if (color < 0 || color > 3) {
            throw new IllegalArgumentException("Ongeldige kleur voor Normaal snoepje.");
        }
    }
}