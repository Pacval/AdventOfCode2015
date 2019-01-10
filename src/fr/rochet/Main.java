package fr.rochet;

import fr.rochet.days.Day20;

public class Main {

    public static void main(String[] args) {
        try {
            DayInterface day = new Day20();
            day.part1();
            day.part2();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
