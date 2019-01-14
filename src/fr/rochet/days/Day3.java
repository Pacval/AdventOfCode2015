package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * --- Day 3: Perfectly Spherical Houses in a Vacuum ---
 * <p>
 * Santa is delivering presents to an infinite two-dimensional grid of houses.
 * <p>
 * He begins by delivering a present to the house at his starting location, and then an elf at the North Pole calls him via radio and tells him where to move next.
 * Moves are always exactly one house to the north (^), south (v), east (>), or west (<). After each move, he delivers another present to the house at his new location.
 * <p>
 * However, the elf back at the north pole has had a little too much eggnog, and so his directions are a little off, and Santa ends up visiting some houses more than once.
 * How many houses receive at least one present?
 * <p>
 * For example:
 * <p>
 * > delivers presents to 2 houses: one at the starting location, and one to the east.
 * ^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
 * ^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * The next year, to speed up the process, Santa creates a robot version of himself, Robo-Santa, to deliver presents with him.
 * <p>
 * Santa and Robo-Santa start at the same location (delivering two presents to the same starting house), then take turns moving based on instructions from the elf, who is eggnoggedly reading from the same script as the previous year.
 * <p>
 * This year, how many houses receive at least one present?
 * <p>
 * For example:
 * <p>
 * ^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.
 * ^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.
 * ^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.
 */
public class Day3 implements DayInterface {

    private class House {
        int posX;
        int posY;

        House(int posX, int posY) {
            this.posX = posX;
            this.posY = posY;
        }
    }

    @Override
    public void part1() throws IOException {
        String entry = ExoUtils.getEntries(3)[0];

        int santaPosX = 0;
        int santaPosY = 0;

        List<House> visitedHouse = new ArrayList<>();

        for (char c : entry.toCharArray()) {
            int currentSantaPosX = santaPosX;
            int currentSantaPosY = santaPosY;
            if (visitedHouse.stream().noneMatch(house -> house.posX == currentSantaPosX && house.posY == currentSantaPosY)) {
                visitedHouse.add(new House(currentSantaPosX, currentSantaPosY));
            }

            switch (c) {
                case '^':
                    santaPosX--;
                    break;
                case '>':
                    santaPosY++;
                    break;
                case 'v':
                    santaPosX++;
                    break;
                case '<':
                    santaPosY--;
                    break;
            }
        }

        System.out.println("Number of house visited by Santa : " + visitedHouse.size());
    }

    @Override
    public void part2() throws IOException {
        String entry = ExoUtils.getEntries(3)[0];

        int santaPosX = 0;
        int santaPosY = 0;

        int robotSantaPosX = 0;
        int robotSantaPosY = 0;

        List<House> visitedHouse = new ArrayList<>();

        for (int i = 0; i < entry.length(); i += 2) {
            int currentSantaPosX = santaPosX;
            int currentSantaPosY = santaPosY;
            if (visitedHouse.stream().noneMatch(house -> house.posX == currentSantaPosX && house.posY == currentSantaPosY)) {
                visitedHouse.add(new House(currentSantaPosX, currentSantaPosY));
            }

            int currentRobotSantaPosX = robotSantaPosX;
            int currentRobotSantaPosY = robotSantaPosY;
            if (visitedHouse.stream().noneMatch(house -> house.posX == currentRobotSantaPosX && house.posY == currentRobotSantaPosY)) {
                visitedHouse.add(new House(currentRobotSantaPosX, currentRobotSantaPosY));
            }


            switch (entry.charAt(i)) {
                case '^':
                    santaPosX--;
                    break;
                case '>':
                    santaPosY++;
                    break;
                case 'v':
                    santaPosX++;
                    break;
                case '<':
                    santaPosY--;
                    break;
            }

            switch (entry.charAt(i + 1)) {
                case '^':
                    robotSantaPosX--;
                    break;
                case '>':
                    robotSantaPosY++;
                    break;
                case 'v':
                    robotSantaPosX++;
                    break;
                case '<':
                    robotSantaPosY--;
                    break;
            }
        }

        System.out.println("Number of house visited by Santa and Robot Santa : " + visitedHouse.size());

    }
}
