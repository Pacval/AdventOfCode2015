package fr.rochet.days;

import fr.rochet.DayInterface;

import java.io.IOException;
import java.math.BigInteger;

public class Day25 implements DayInterface {

    @Override
    public void part1() throws IOException {
        // On fait les entrées à la main
        int row = 2947;
        int column = 3029;

        int currentRow = 1;
        int currentColumn = 1;

        int maxRow = 1;

        BigInteger value = BigInteger.valueOf(20151125);

        while (currentRow < row || currentColumn < column) {

            value = value.multiply(BigInteger.valueOf(252533)).remainder(BigInteger.valueOf(33554393));

            currentRow--;
            currentColumn++;
            if (currentRow <= 0) {
                maxRow++;
                currentRow = maxRow;
                currentColumn = 1;
            }
        }

        System.out.println("The code at row " + row + " and column " + column + " is : " + value);
    }

    @Override
    public void part2() throws IOException {

    }
}
