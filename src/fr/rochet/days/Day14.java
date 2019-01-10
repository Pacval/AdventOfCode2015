package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day14 implements DayInterface {

    private class Reindeer {
        String name;
        int speed;
        int flyTime;
        int restTime;
        boolean flying;
        int dist;
        int timer;
        int points;

        Reindeer(String name, int speed, int flyTime, int restTime) {
            this.name = name;
            this.speed = speed;
            this.flyTime = flyTime;
            this.restTime = restTime;
            this.flying = true;
            this.dist = 0;
            this.timer = flyTime;
            this.points = 0;
        }

        void run() {
            if (flying) {
                dist += speed;
            }
            timer--;
            if (timer <= 0) {
                // On change vol / dodo
                flying = !flying;
                timer = (flying ? flyTime : restTime);
            }
        }

        void addPoint() {
            points++;
        }

        String getName() {
            return name;
        }

        int getDist() {
            return dist;
        }

        int getPoints() {
            return points;
        }
    }

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(14);

        int runTime = 2503;

        List<Reindeer> competitors = new ArrayList<>();
        for (String entry : entries) {
            competitors.add(new Reindeer(
                    entry.split(" ")[0],
                    Integer.parseInt(entry.split(" ")[3]),
                    Integer.parseInt(entry.split(" ")[6]),
                    Integer.parseInt(entry.split(" ")[13])));
        }

        for (int i = 0; i < runTime; i++) {
            competitors.forEach(Reindeer::run);
        }

        Reindeer winner = competitors.stream().max(Comparator.comparing(Reindeer::getDist)).get();

        System.out.println("Winner reindeer is " + winner.getName() + " with a distance of " + winner.getDist());
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(14);

        int runTime = 2503;

        List<Reindeer> competitors = new ArrayList<>();
        for (String entry : entries) {
            competitors.add(new Reindeer(
                    entry.split(" ")[0],
                    Integer.parseInt(entry.split(" ")[3]),
                    Integer.parseInt(entry.split(" ")[6]),
                    Integer.parseInt(entry.split(" ")[13])));
        }

        for (int i = 0; i < runTime; i++) {
            competitors.forEach(Reindeer::run);
            competitors.stream().max(Comparator.comparing(Reindeer::getDist)).get().addPoint();
        }

        Reindeer winner = competitors.stream().max(Comparator.comparing(Reindeer::getPoints)).get();

        System.out.println("New winner reindeer is " + winner.getName() + " with " + winner.getPoints() + " points");
    }
}
