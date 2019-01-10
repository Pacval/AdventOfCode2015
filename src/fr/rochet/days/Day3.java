package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
