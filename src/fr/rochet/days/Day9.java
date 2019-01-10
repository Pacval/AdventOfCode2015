package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
