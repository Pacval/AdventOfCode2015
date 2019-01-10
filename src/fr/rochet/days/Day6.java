package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.Arrays;

public class Day6 implements DayInterface {

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(6);

        int[][] lights = new int[1000][1000];
        for (int[] innerRow : lights){
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
        for (int[] innerRow : lights){
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
