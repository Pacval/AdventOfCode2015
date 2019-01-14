package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * --- Day 17: No Such Thing as Too Much ---
 * <p>
 * The elves bought too much eggnog again - 150 liters this time. To fit it all into your refrigerator, you'll need to move it into smaller containers.
 * You take an inventory of the capacities of the available containers.
 * <p>
 * For example, suppose you have containers of size 20, 15, 10, 5, and 5 liters. If you need to store 25 liters, there are four ways to do it:
 * <p>
 * 15 and 10
 * 20 and 5 (the first 5)
 * 20 and 5 (the second 5)
 * 15, 5, and 5
 * <p>
 * Filling all containers entirely, how many different combinations of containers can exactly fit all 150 liters of eggnog?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * While playing with all the containers in the kitchen, another load of eggnog arrives! The shipping and receiving department is requesting as many containers as you can spare.
 * <p>
 * Find the minimum number of containers that can exactly fit all 150 liters of eggnog. How many different ways can you fill that number of containers and still hold exactly 150 litres?
 * <p>
 * In the example above, the minimum number of containers was two. There were three ways to use that many containers, and so the answer there would be 3.
 */
public class Day17 implements DayInterface {

    private int totalLiquid;
    private int goodCombinaisons;
    private int minNbContainersUsed;

    // Fonction récursive permettant de calculer toutes les combinaisons d'une liste.
    // Seulement pas besoin de stocker la liste : à la fin on regarde juste si le total vaut 150 et si oui on incrémente le compteur
    private void calculateContent(int[] remainingContainers, Integer total) {
        if (remainingContainers.length == 0) {
            if (total == totalLiquid) {
                goodCombinaisons++;
            }
        } else {
            for (int i : new int[]{0, 1}) {
                calculateContent(Arrays.copyOfRange(remainingContainers, 1, remainingContainers.length), total + (remainingContainers[0] * i));
            }
        }
    }

    private void calculateContentWithMinimumUse(int[] remainingContainers, Integer total, int containersUsed) {
        if (remainingContainers.length == 0) {
            if (total == totalLiquid) {
                if (containersUsed < minNbContainersUsed) {
                    minNbContainersUsed = containersUsed;
                    goodCombinaisons = 1;
                } else if (containersUsed == minNbContainersUsed) {
                    goodCombinaisons++;
                }
            }
        } else {
            for (int i : new int[]{0, 1}) {
                calculateContentWithMinimumUse(
                        Arrays.copyOfRange(remainingContainers, 1, remainingContainers.length),
                        total + (remainingContainers[0] * i),
                        containersUsed + i);
            }
        }
    }

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(17);
        totalLiquid = 150;
        goodCombinaisons = 0;

        int[] containers = new int[entries.length];
        for (int i = 0; i < entries.length; i++) {
            containers[i] = Integer.parseInt(entries[i]);
        }
        calculateContent(containers, 0);

        System.out.println("Numbers of combinaisons who work : " + goodCombinaisons);
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(17);
        totalLiquid = 150;
        goodCombinaisons = 0;
        minNbContainersUsed = entries.length;

        int[] containers = new int[entries.length];
        for (int i = 0; i < entries.length; i++) {
            containers[i] = Integer.parseInt(entries[i]);
        }
        calculateContentWithMinimumUse(containers, 0, 0);

        System.out.println("Numbers of combinaisons who work with the minimum of containers used : " + goodCombinaisons);
        System.out.println(minNbContainersUsed + " containers used");
    }
}
