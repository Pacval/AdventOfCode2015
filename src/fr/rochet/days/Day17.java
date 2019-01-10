package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.Arrays;

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
                } else if(containersUsed == minNbContainersUsed) {
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
