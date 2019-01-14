package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * --- Day 14: Reindeer Olympics ---
 * <p>
 * This year is the Reindeer Olympics! Reindeer can fly at high speeds, but must rest occasionally to recover their energy. Santa would like to know which of his reindeer is fastest, and so he has them race.
 * <p>
 * Reindeer can only either be flying (always at their top speed) or resting (not moving at all), and always spend whole seconds in either state.
 * <p>
 * For example, suppose you have the following Reindeer:
 * <p>
 * Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
 * Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
 * <p>
 * After one second, Comet has gone 14 km, while Dancer has gone 16 km. After ten seconds, Comet has gone 140 km, while Dancer has gone 160 km.
 * On the eleventh second, Comet begins resting (staying at 140 km), and Dancer continues on for a total distance of 176 km. On the 12th second, both reindeer are resting.
 * They continue to rest until the 138th second, when Comet flies for another ten seconds. On the 174th second, Dancer flies for another 11 seconds.
 * <p>
 * In this example, after the 1000th second, both reindeer are resting, and Comet is in the lead at 1120 km (poor Dancer has only gotten 1056 km by that point).
 * So, in this situation, Comet would win (if the race ended at 1000 seconds).
 * <p>
 * Given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, what distance has the winning reindeer traveled?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * Seeing how reindeer move in bursts, Santa decides he's not pleased with the old scoring system.
 * <p>
 * Instead, at the end of each second, he awards one point to the reindeer currently in the lead. (If there are multiple reindeer tied for the lead, they each get one point).
 * He keeps the traditional 2503 second time limit, of course, as doing otherwise would be entirely ridiculous.
 * <p>
 * Given the example reindeer from above, after the first second, Dancer is in the lead and gets one point. He stays in the lead until several seconds into Comet's second burst:
 * after the 140th second, Comet pulls into the lead and gets his first point. Of course, since Dancer had been in the lead for the 139 seconds before that, he has accumulated 139 points by the 140th second.
 * <p>
 * After the 1000th second, Dancer has accumulated 689 points, while poor Comet, our old champion, only has 312. So, with the new scoring system, Dancer would win (if the race ended at 1000 seconds).
 * <p>
 * Again given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, how many points does the winning reindeer have?
 */
public class Day14 implements DayInterface {

    private class Reindeer {
        private String name;
        private int speed;
        private int flyTime;
        private int restTime;
        private boolean flying;
        private int dist;
        private int timer;
        private int points;

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
