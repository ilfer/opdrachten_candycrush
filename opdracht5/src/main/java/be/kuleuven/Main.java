package be.kuleuven;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Integer[] gridArray = {
                0, 0, 1, 0,
                1, 1, 0, 2,
                2, 0, 1, 3,
                0, 1, 1, 1
        };

        Iterable<Integer> grid = List.of(gridArray);
        int width = 4;
        int height = 4;
        int indexToCheck = 5;

        Iterable<Integer> result = CheckNeighboursInGrid.getSameNeighboursIds(grid, width, height, indexToCheck);

        System.out.println(result); // Output: [2, 4, 10]
    }
}