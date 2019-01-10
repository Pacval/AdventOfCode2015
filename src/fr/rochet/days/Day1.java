package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;

public class Day1 implements DayInterface {

    @Override
    public void part1() throws IOException {
        String entry = ExoUtils.getEntries(1)[0];

        int floor = 0;
        for (char c : entry.toCharArray()) {
            if (c == '(') {
                floor++;
            } else if (c == ')') {
                floor--;
            } else {
                System.out.println("Unknown character : " + c);
            }
        }
        System.out.println("Floor to go : " + floor);
    }

    @Override
    public void part2() throws IOException {
        String entry = ExoUtils.getEntries(1)[0];

        int floor = 0;
        int count = 0;
        while (floor >= 0) {
            if (entry.charAt(count) == '(') {
                floor++;
            } else if (entry.charAt(count) == ')') {
                floor--;
            } else {
                System.out.println("Unknown character : " + entry.charAt(count));
            }
            count++;
        }
        System.out.println("Entering 1st basement at position : " + count);
    }
}
