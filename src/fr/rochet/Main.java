package fr.rochet;

import fr.rochet.days.Day23;

public class Main {

    public static void main(String[] args) {
        try {
            DayInterface day = new Day23();
            day.part1();
            day.part2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
