package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18 implements DayInterface {

    private static final char ON = '#';
    private static final char OFF = '.';

    private char isLightUpNext(int x, int y, char[][] lights) {
        if (lights[x][y] == ON) {
            int neighborsOn = (int) Arrays.stream(new Character[]{lights[x - 1][y - 1], lights[x - 1][y], lights[x - 1][y + 1],
                    lights[x][y - 1], lights[x][y + 1],
                    lights[x + 1][y - 1], lights[x + 1][y], lights[x + 1][y + 1]})
                    .filter(light -> light == ON)
                    .count();
            return neighborsOn == 2 || neighborsOn == 3 ? ON : OFF;
        } else {
            int neighborsOn = (int) Arrays.stream(new Character[]{lights[x - 1][y - 1], lights[x - 1][y], lights[x - 1][y + 1],
                    lights[x][y - 1], lights[x][y + 1],
                    lights[x + 1][y - 1], lights[x + 1][y], lights[x + 1][y + 1]})
                    .filter(light -> light == ON)
                    .count();
            return neighborsOn == 3 ? ON : OFF;
        }
    }

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(18);
        int steps = 100;

        // pour se simplifier la vie on va rajouter dans nos plans une bordure de lumières éteintes autour de nos lumières.
        // cela permettra de gérer les bordures
        List<String> lightsWithBorder = new ArrayList<>(Arrays.asList(entries));

        char[] emptyCharLine = new char[lightsWithBorder.size()];
        Arrays.fill(emptyCharLine, OFF);
        // haut et bas
        lightsWithBorder.add(0, String.valueOf(emptyCharLine));
        lightsWithBorder.add(String.valueOf(emptyCharLine));
        // droite et gauche
        lightsWithBorder.replaceAll(str -> "." + str + ".");

        // On transforme en tableau de char pour économiser de la place
        char[][] lights = lightsWithBorder.stream().map(String::toCharArray).toArray(char[][]::new);

        for (int i = 0; i < steps; i++) {
            char[][] newLights = new char[lights.length][lights[0].length];
            for (int x = 1; x < lights.length - 1; x++) {
                for (int y = 1; y < lights[0].length - 1; y++) {
                    newLights[x][y] = isLightUpNext(x, y, lights);
                }
            }
            lights = newLights;
        }

        int totalLigntsOn = 0;
        for (char[] subArray : lights) {
            for (char light : subArray) {
                if (light == ON) {
                    totalLigntsOn++;
                }
            }
        }

        System.out.println("Total lights on after " + steps + " steps : " + totalLigntsOn);
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(18);
        int steps = 100;

        // pour se simplifier la vie on va rajouter dans nos plans une bordure de lumières éteintes autour de nos lumières.
        // cela permettra de gérer les bordures
        List<String> lightsWithBorder = new ArrayList<>(Arrays.asList(entries));

        char[] emptyCharLine = new char[lightsWithBorder.size()];
        Arrays.fill(emptyCharLine, OFF);
        // haut et bas
        lightsWithBorder.add(0, String.valueOf(emptyCharLine));
        lightsWithBorder.add(String.valueOf(emptyCharLine));
        // droite et gauche
        lightsWithBorder.replaceAll(str -> "." + str + ".");

        // On transforme en tableau de char pour économiser de la place
        char[][] lights = lightsWithBorder.stream().map(String::toCharArray).toArray(char[][]::new);

        // bourrin mais efficace : on met les 4 coins à ON et le fait à chaque tour
        lights[1][1] = ON;
        lights[1][lights[0].length - 2] = ON;
        lights[lights.length - 2][1] = ON;
        lights[lights.length - 2][lights[0].length - 2] = ON;

        for (int i = 0; i < steps; i++) {
            char[][] newLights = new char[lights.length][lights[0].length];
            for (int x = 1; x < lights.length - 1; x++) {
                for (int y = 1; y < lights[0].length - 1; y++) {
                    newLights[x][y] = isLightUpNext(x, y, lights);
                }
            }
            lights = newLights;

            lights[1][1] = ON;
            lights[1][lights[0].length - 2] = ON;
            lights[lights.length - 2][1] = ON;
            lights[lights.length - 2][lights[0].length - 2] = ON;
        }

        int totalLigntsOn = 0;
        for (char[] subArray : lights) {
            for (char light : subArray) {
                if (light == ON) {
                    totalLigntsOn++;
                }
            }
        }

        System.out.println("Total lights on after " + steps + " steps : " + totalLigntsOn);
    }
}
