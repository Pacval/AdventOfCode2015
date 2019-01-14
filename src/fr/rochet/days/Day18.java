package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * --- Day 18: Like a GIF For Your Yard ---
 * <p>
 * After the million lights incident, the fire code has gotten stricter: now, at most ten thousand lights are allowed. You arrange them in a 100x100 grid.
 * <p>
 * Never one to let you down, Santa again mails you instructions on the ideal lighting configuration. With so few lights, he says, you'll have to resort to animation.
 * <p>
 * Start by setting your lights to the included initial configuration (your puzzle input). A # means "on", and a . means "off".
 * <p>
 * Then, animate your grid in steps, where each step decides the next configuration based on the current one.
 * Each light's next state (either on or off) depends on its current state and the current states of the eight lights adjacent to it (including diagonals).
 * Lights on the edge of the grid might have fewer than eight neighbors; the missing ones always count as "off".
 * <p>
 * For example, in a simplified 6x6 grid, the light marked A has the neighbors numbered 1 through 8, and the light marked B, which is on an edge, only has the neighbors marked 1 through 5:
 * <p>
 * 1B5...
 * 234...
 * ......
 * ..123.
 * ..8A4.
 * ..765.
 * <p>
 * The state a light should have next is based on its current state (on or off) plus the number of neighbors that are on:
 * <p>
 * A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
 * A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
 * <p>
 * All of the lights update simultaneously; they all consider the same current state before moving to the next.
 * <p>
 * Here's a few steps from an example configuration of another 6x6 grid:
 * <p>
 * Initial state:
 * .#.#.#
 * ...##.
 * #....#
 * ..#...
 * #.#..#
 * ####..
 * <p>
 * After 1 step:
 * ..##..
 * ..##.#
 * ...##.
 * ......
 * #.....
 * #.##..
 * <p>
 * After 2 steps:
 * ..###.
 * ......
 * ..###.
 * ......
 * .#....
 * .#....
 * <p>
 * After 3 steps:
 * ...#..
 * ......
 * ...#..
 * ..##..
 * ......
 * ......
 * <p>
 * After 4 steps:
 * ......
 * ......
 * ..##..
 * ..##..
 * ......
 * ......
 * <p>
 * After 4 steps, this example has four lights on.
 * <p>
 * In your grid of 100x100 lights, given your initial configuration, how many lights are on after 100 steps?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * You flip the instructions over; Santa goes on to point out that this is all just an implementation of Conway's Game of Life.
 * At least, it was, until you notice that something's wrong with the grid of lights you bought: four lights, one in each corner, are stuck on and can't be turned off.
 * The example above will actually run like this:
 * <p>
 * Initial state:
 * ##.#.#
 * ...##.
 * #....#
 * ..#...
 * #.#..#
 * ####.#
 * <p>
 * After 1 step:
 * #.##.#
 * ####.#
 * ...##.
 * ......
 * #...#.
 * #.####
 * <p>
 * After 2 steps:
 * #..#.#
 * #....#
 * .#.##.
 * ...##.
 * .#..##
 * ##.###
 * <p>
 * After 3 steps:
 * #...##
 * ####.#
 * ..##.#
 * ......
 * ##....
 * ####.#
 * <p>
 * After 4 steps:
 * #.####
 * #....#
 * ...#..
 * .##...
 * #.....
 * #.#..#
 * <p>
 * After 5 steps:
 * ##.###
 * .##..#
 * .##...
 * .##...
 * #.#...
 * ##...#
 * <p>
 * After 5 steps, this example now has 17 lights on.
 * <p>
 * In your grid of 100x100 lights, given your initial configuration, but with the four corners always in the on state, how many lights are on after 100 steps?
 */
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
