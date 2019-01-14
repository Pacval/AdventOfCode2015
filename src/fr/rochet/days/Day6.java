package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * --- Day 6: Probably a Fire Hazard ---
 * <p>
 * Because your neighbors keep defeating you in the holiday house decorating contest year after year, you've decided to deploy one million lights in a 1000x1000 grid.
 * <p>
 * Furthermore, because you've been especially nice this year, Santa has mailed you instructions on how to display the ideal lighting configuration.
 * <p>
 * Lights in your grid are numbered from 0 to 999 in each direction; the lights at each corner are at 0,0, 0,999, 999,999, and 999,0.
 * The instructions include whether to turn on, turn off, or toggle various inclusive ranges given as coordinate pairs.
 * Each coordinate pair represents opposite corners of a rectangle, inclusive; a coordinate pair like 0,0 through 2,2 therefore refers to 9 lights in a 3x3 square. The lights all start turned off.
 * <p>
 * To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions Santa sent you in order.
 * <p>
 * For example:
 * <p>
 * turn on 0,0 through 999,999 would turn on (or leave on) every light.
 * toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off.
 * turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
 * <p>
 * After following the instructions, how many lights are lit?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * You just finish implementing your winning light pattern when you realize you mistranslated Santa's message from Ancient Nordic Elvish.
 * <p>
 * The light grid you bought actually has individual brightness controls; each light can have a brightness of zero or more. The lights all start at zero.
 * <p>
 * The phrase turn on actually means that you should increase the brightness of those lights by 1.
 * <p>
 * The phrase turn off actually means that you should decrease the brightness of those lights by 1, to a minimum of zero.
 * <p>
 * The phrase toggle actually means that you should increase the brightness of those lights by 2.
 * <p>
 * What is the total brightness of all lights combined after following Santa's instructions?
 * <p>
 * For example:
 * <p>
 * turn on 0,0 through 0,0 would increase the total brightness by 1.
 * toggle 0,0 through 999,999 would increase the total brightness by 2000000.
 */
public class Day6 implements DayInterface {

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(6);

        int[][] lights = new int[1000][1000];
        for (int[] innerRow : lights) {
            Arrays.fill(innerRow, -1);
        }

        for (String instruction : entries) {
            if (instruction.split(" ")[0].equals("toggle")) {

                int startX = Integer.parseInt(instruction.split(" ")[1].split(",")[0]);
                int startY = Integer.parseInt(instruction.split(" ")[1].split(",")[1]);
                int endX = Integer.parseInt(instruction.split(" ")[3].split(",")[0]);
                int endY = Integer.parseInt(instruction.split(" ")[3].split(",")[1]);

                for (int i = startX; i <= endX; i++) {
                    for (int j = startY; j <= endY; j++) {
                        lights[i][j] *= -1;
                    }
                }

            } else {
                int startX = Integer.parseInt(instruction.split(" ")[2].split(",")[0]);
                int startY = Integer.parseInt(instruction.split(" ")[2].split(",")[1]);
                int endX = Integer.parseInt(instruction.split(" ")[4].split(",")[0]);
                int endY = Integer.parseInt(instruction.split(" ")[4].split(",")[1]);

                if (instruction.split(" ")[1].equals("on")) {
                    for (int i = startX; i <= endX; i++) {
                        for (int j = startY; j <= endY; j++) {
                            lights[i][j] = 1;
                        }
                    }
                } else if (instruction.split(" ")[1].equals("off")) {
                    for (int i = startX; i <= endX; i++) {
                        for (int j = startY; j <= endY; j++) {
                            lights[i][j] = -1;
                        }
                    }
                } else {
                    System.out.println("Incorrect instruction");
                }
            }
        }

        long lightsLit = Arrays.stream(lights).flatMapToInt(Arrays::stream).filter(light -> light == 1).count();
        System.out.println("Number of lights which are lit : " + lightsLit);
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(6);

        int[][] lights = new int[1000][1000];
        for (int[] innerRow : lights) {
            Arrays.fill(innerRow, 0);
        }

        for (String instruction : entries) {
            if (instruction.split(" ")[0].equals("toggle")) {

                int startX = Integer.parseInt(instruction.split(" ")[1].split(",")[0]);
                int startY = Integer.parseInt(instruction.split(" ")[1].split(",")[1]);
                int endX = Integer.parseInt(instruction.split(" ")[3].split(",")[0]);
                int endY = Integer.parseInt(instruction.split(" ")[3].split(",")[1]);

                for (int i = startX; i <= endX; i++) {
                    for (int j = startY; j <= endY; j++) {
                        lights[i][j] += 2;
                    }
                }

            } else {
                int startX = Integer.parseInt(instruction.split(" ")[2].split(",")[0]);
                int startY = Integer.parseInt(instruction.split(" ")[2].split(",")[1]);
                int endX = Integer.parseInt(instruction.split(" ")[4].split(",")[0]);
                int endY = Integer.parseInt(instruction.split(" ")[4].split(",")[1]);

                if (instruction.split(" ")[1].equals("on")) {
                    for (int i = startX; i <= endX; i++) {
                        for (int j = startY; j <= endY; j++) {
                            lights[i][j] += 1;
                        }
                    }
                } else if (instruction.split(" ")[1].equals("off")) {
                    for (int i = startX; i <= endX; i++) {
                        for (int j = startY; j <= endY; j++) {
                            lights[i][j] = lights[i][j] > 0 ? lights[i][j] - 1 : lights[i][j];
                        }
                    }
                } else {
                    System.out.println("Incorrect instruction");
                }
            }
        }

        long totalBrightness = Arrays.stream(lights).flatMapToInt(Arrays::stream).sum();
        System.out.println("Total brightness : " + totalBrightness);

    }
}
