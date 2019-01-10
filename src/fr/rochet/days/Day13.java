package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 implements DayInterface {

    private class Arrangement {
        String person;
        String nextTo;
        Integer happiness;

        public Arrangement(String person, String nextTo, Integer happiness) {
            this.person = person;
            this.nextTo = nextTo;
            this.happiness = happiness;
        }

        public String getPerson() {
            return person;
        }

        public String getNextTo() {
            return nextTo;
        }

        public Integer getHappiness() {
            return happiness;
        }
    }

    // Fonction récursive utilisant la permutation pour générer tous les plans de table possibles
    private List<List<String>> generatePerm(List<String> names) {
        if (names.size() == 0) {
            List<List<String>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        String firstElement = names.remove(0);
        List<List<String>> returnValue = new ArrayList<>();
        List<List<String>> permutations = generatePerm(names);

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
        String[] entries = ExoUtils.getEntries(13);

        List<Arrangement> arrangements = new ArrayList<>();
        for (String arrangement : entries) {
            arrangements.add(new Arrangement(
                    arrangement.split(" ")[0],
                    arrangement.split(" ")[10].replaceAll("\\.", ""),
                    Integer.parseInt(arrangement.split(" ")[3]) * (arrangement.split(" ")[2].equals("gain") ? 1 : -1)
            ));
        }

        List<List<String>> tablePlans = generatePerm(arrangements.stream().map(Arrangement::getPerson).distinct().collect(Collectors.toList()));

        int maxHappiness = 0;
        List<String> maxHappinessPlan = null;
        for (List<String> plan : tablePlans) {
            int planHappiness = 0;
            int planSize = plan.size();
            for(int i = 0; i < planSize; i++) {
                int position = i;
                // personne d'avant
                if (position == 0) {
                    planHappiness += arrangements.stream()
                            .filter(item -> item.getPerson().equals(plan.get(0)))
                            .filter(item -> item.getNextTo().equals(plan.get(planSize - 1)))
                            .mapToInt(Arrangement::getHappiness)
                            .min()
                            .getAsInt();
                } else {
                    planHappiness += arrangements.stream()
                            .filter(item -> item.getPerson().equals(plan.get(position)))
                            .filter(item -> item.getNextTo().equals(plan.get(position - 1)))
                            .mapToInt(Arrangement::getHappiness)
                            .min()
                            .getAsInt();
                }

                    // personne d'après

                if(position == planSize - 1) {
                    planHappiness += arrangements.stream()
                            .filter(item -> item.getPerson().equals(plan.get(position)))
                            .filter(item -> item.getNextTo().equals(plan.get(0)))
                            .mapToInt(Arrangement::getHappiness)
                            .min()
                            .getAsInt();

                } else {
                    planHappiness += arrangements.stream()
                            .filter(item -> item.getPerson().equals(plan.get(position)))
                            .filter(item -> item.getNextTo().equals(plan.get(position + 1)))
                            .mapToInt(Arrangement::getHappiness)
                            .min()
                            .getAsInt();
                }
            }

            if (planHappiness > maxHappiness) {
                maxHappiness = planHappiness;
                maxHappinessPlan = plan;
            }
        }

        System.out.println("Maximum happiness is : " + maxHappiness);
        System.out.println("With plan : " + String.join(" - ", maxHappinessPlan));
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(13);

        List<Arrangement> arrangements = new ArrayList<>();
        for (String arrangement : entries) {
            arrangements.add(new Arrangement(
                    arrangement.split(" ")[0],
                    arrangement.split(" ")[10].replaceAll("\\.", ""),
                    Integer.parseInt(arrangement.split(" ")[3]) * (arrangement.split(" ")[2].equals("gain") ? 1 : -1)
            ));
        }

        List<String> persons = arrangements.stream().map(Arrangement::getPerson).distinct().collect(Collectors.toList());
        String me = "Me";
        persons.add(me);
        for (String person : persons) {
            arrangements.add(new Arrangement(me, person, 0));
            arrangements.add(new Arrangement(person, me, 0));
        }

        List<List<String>> tablePlans = generatePerm(persons);

        int maxHappiness = 0;
        List<String> maxHappinessPlan = null;
        for (List<String> plan : tablePlans) {
            int planHappiness = 0;
            int planSize = plan.size();
            for(int i = 0; i < planSize; i++) {
                int position = i;
                // personne d'avant
                if (position == 0) {
                    planHappiness += arrangements.stream()
                            .filter(item -> item.getPerson().equals(plan.get(0)))
                            .filter(item -> item.getNextTo().equals(plan.get(planSize - 1)))
                            .mapToInt(Arrangement::getHappiness)
                            .min()
                            .getAsInt();
                } else {
                    planHappiness += arrangements.stream()
                            .filter(item -> item.getPerson().equals(plan.get(position)))
                            .filter(item -> item.getNextTo().equals(plan.get(position - 1)))
                            .mapToInt(Arrangement::getHappiness)
                            .min()
                            .getAsInt();
                }

                // personne d'après

                if(position == planSize - 1) {
                    planHappiness += arrangements.stream()
                            .filter(item -> item.getPerson().equals(plan.get(position)))
                            .filter(item -> item.getNextTo().equals(plan.get(0)))
                            .mapToInt(Arrangement::getHappiness)
                            .min()
                            .getAsInt();

                } else {
                    planHappiness += arrangements.stream()
                            .filter(item -> item.getPerson().equals(plan.get(position)))
                            .filter(item -> item.getNextTo().equals(plan.get(position + 1)))
                            .mapToInt(Arrangement::getHappiness)
                            .min()
                            .getAsInt();
                }
            }

            if (planHappiness > maxHappiness) {
                maxHappiness = planHappiness;
                maxHappinessPlan = plan;
            }
        }

        System.out.println("Maximum happiness with you is : " + maxHappiness);
        System.out.println("With plan : " + String.join(" - ", maxHappinessPlan));
    }
}
