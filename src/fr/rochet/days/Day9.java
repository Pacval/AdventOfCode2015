package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * --- Day 9: All in a Single Night ---
 * <p>
 * Every year, Santa manages to deliver all of his presents in a single night.
 * <p>
 * This year, however, he has some new locations to visit; his elves have provided him the distances between every pair of locations. He can start and end at any two (different) locations he wants,
 * but he must visit each location exactly once. What is the shortest distance he can travel to achieve this?
 * <p>
 * For example, given the following distances:
 * <p>
 * London to Dublin = 464
 * London to Belfast = 518
 * Dublin to Belfast = 141
 * <p>
 * The possible routes are therefore:
 * <p>
 * Dublin -> London -> Belfast = 982
 * London -> Dublin -> Belfast = 605
 * London -> Belfast -> Dublin = 659
 * Dublin -> Belfast -> London = 659
 * Belfast -> Dublin -> London = 605
 * Belfast -> London -> Dublin = 982
 * <p>
 * The shortest of these is London -> Dublin -> Belfast = 605, and so the answer is 605 in this example.
 * <p>
 * What is the distance of the shortest route?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * The next year, just to show off, Santa decides to take the route with the longest distance instead.
 * <p>
 * He can still start and end at any two (different) locations he wants, and he still must visit each location exactly once.
 * <p>
 * For example, given the distances above, the longest route would be 982 via (for example) Dublin -> London -> Belfast.
 * <p>
 * What is the distance of the longest route?
 */
public class Day9 implements DayInterface {

    private class Travel {
        String start;
        String end;
        int dist;

        public Travel(String start, String end, int dist) {
            this.start = start;
            this.end = end;
            this.dist = dist;
        }

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }

        public int getDist() {
            return dist;
        }
    }

    // Fonction récursive utilisant la permutation pour générer tous les trajets possibles
    private List<List<String>> generatePerm(List<String> original) {
        if (original.size() == 0) {
            List<List<String>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        String firstElement = original.remove(0);
        List<List<String>> returnValue = new ArrayList<>();
        List<List<String>> permutations = generatePerm(original);

        for (List<String> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                List<String> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }


    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(9);

        List<Travel> travels = new ArrayList<>();
        for (String entry : entries) {
            travels.add(new Travel(entry.split(" ")[0],
                    entry.split(" ")[2],
                    Integer.parseInt(entry.split(" ")[4])));
            travels.add(new Travel(entry.split(" ")[2],
                    entry.split(" ")[0],
                    Integer.parseInt(entry.split(" ")[4])));
        }

        // Permet de générer et stocker tous les trajets possibles
        List<String> cities = travels.stream().map(Travel::getStart).distinct().collect(Collectors.toList());
        List<List<String>> permutations = this.generatePerm(cities);

        int minDist = Integer.MAX_VALUE;
        String travelPlan = "";
        for (List<String> travel : permutations) {
            int dist = 0;
            for (int i = 0; i < travel.size() - 1; i++) {
                String currentCity = travel.get(i);
                String nextCity = travel.get(i + 1);
                dist += travels.stream()
                        .filter(item -> item.getStart().equals(currentCity))
                        .filter(item -> item.getEnd().equals(nextCity))
                        .min(Comparator.comparing(Travel::getDist))
                        .get()
                        .getDist();
            }

            if (dist < minDist) {
                minDist = dist;
                travelPlan = String.join(" -> ", travel);
            }
        }

        System.out.println("Fastest travel is : " + minDist);
        System.out.println("Travel plan : " + travelPlan);
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(9);

        List<Travel> travels = new ArrayList<>();
        for (String entry : entries) {
            travels.add(new Travel(entry.split(" ")[0],
                    entry.split(" ")[2],
                    Integer.parseInt(entry.split(" ")[4])));
            travels.add(new Travel(entry.split(" ")[2],
                    entry.split(" ")[0],
                    Integer.parseInt(entry.split(" ")[4])));
        }

        // Permet de générer et stocker tous les trajets possibles
        List<String> cities = travels.stream().map(Travel::getStart).distinct().collect(Collectors.toList());
        List<List<String>> permutations = this.generatePerm(cities);

        int maxDist = 0;
        String travelPlan = "";
        for (List<String> travel : permutations) {
            int dist = 0;
            for (int i = 0; i < travel.size() - 1; i++) {
                String currentCity = travel.get(i);
                String nextCity = travel.get(i + 1);
                dist += travels.stream()
                        .filter(item -> item.getStart().equals(currentCity))
                        .filter(item -> item.getEnd().equals(nextCity))
                        .min(Comparator.comparing(Travel::getDist))
                        .get()
                        .getDist();
            }

            if (dist > maxDist) {
                maxDist = dist;
                travelPlan = String.join(" -> ", travel);
            }
        }

        System.out.println("Longest travel is : " + maxDist);
        System.out.println("Travel plan : " + travelPlan);
    }
}
