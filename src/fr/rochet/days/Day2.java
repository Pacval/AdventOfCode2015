package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.Arrays;

public class Day2 implements DayInterface {

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(2);

        int totalPaper = 0;
        for (String gift : entries) {
            int l = Integer.valueOf(gift.split("x")[0]);
            int w = Integer.valueOf(gift.split("x")[1]);
            int h = Integer.valueOf(gift.split("x")[2]);

            totalPaper += 2 * l * w
                    + 2 * w * h
                    + 2 * h * l;

            totalPaper += Arrays.stream(new int[]{l * w, w * h, h * l}).min().getAsInt();
        }
        System.out.println("Total of wrapping paper to buy : " + totalPaper);
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(2);

        int totalRibbon = 0;
        for (String gift : entries) {
            int l = Integer.valueOf(gift.split("x")[0]);
            int w = Integer.valueOf(gift.split("x")[1]);
            int h = Integer.valueOf(gift.split("x")[2]);

            totalRibbon += Arrays.stream(new int[]{2 * l + 2 * w, 2 * w + 2 * h, 2 * h + 2 * l}).min().getAsInt();
            totalRibbon += l * w * h;
        }

        System.out.println("Total of ribbon to buy : " + totalRibbon);
    }
}
