package be.kuleuven;

import java.util.ArrayList;
import java.util.List;

public class CheckNeighboursInGrid {

    public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid, int width, int height, int indexToCheck) {
        List<Integer> result = new ArrayList<>();

        // Bereken de rij- en kolomindex van het opgegeven element
        int rowIndex = indexToCheck / width;
        int colIndex = indexToCheck % width;
        int valueToCheck = -1;

        // Zoek de waarde van het element op de opgegeven index
        int currentIndex = 0;
        for (int element : grid) {
            if (currentIndex == indexToCheck) {
                valueToCheck = element;
                break;
            }
            currentIndex++;
        }

        // Controleer de buren
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = colIndex - 1; j <= colIndex + 1; j++) {
                int neighborIndex = i * width + j;
                // Controleer of de buur binnen de grenzen van het rooster ligt
                if (i >= 0 && i < height && j >= 0 && j < width && neighborIndex != indexToCheck) {
                    // Controleer of de waarde van de buur overeenkomt met de waarde van het gespecificeerde element
                    currentIndex = 0;
                    for (int neighbor : grid) {
                        if (currentIndex == neighborIndex && neighbor == valueToCheck) {
                            result.add(neighborIndex);
                            break;
                        }
                        currentIndex++;
                    }
                }
            }
        }

        return result;
    }
}