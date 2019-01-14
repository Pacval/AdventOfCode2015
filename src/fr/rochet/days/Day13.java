package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * --- Day 13: Knights of the Dinner Table ---
 * <p>
 * In years past, the holiday feast with your family hasn't gone so well. Not everyone gets along! This year, you resolve, will be different.
 * You're going to find the optimal seating arrangement and avoid all those awkward conversations.
 * <p>
 * You start by writing up a list of everyone invited and the amount their happiness would increase or decrease if they were to find themselves sitting next to each other person.
 * You have a circular table that will be just big enough to fit everyone comfortably, and so each person will have exactly two neighbors.
 * <p>
 * For example, suppose you have only four attendees planned, and you calculate their potential happiness as follows:
 * <p>
 * Alice would gain 54 happiness units by sitting next to Bob.
 * Alice would lose 79 happiness units by sitting next to Carol.
 * Alice would lose 2 happiness units by sitting next to David.
 * Bob would gain 83 happiness units by sitting next to Alice.
 * Bob would lose 7 happiness units by sitting next to Carol.
 * Bob would lose 63 happiness units by sitting next to David.
 * Carol would lose 62 happiness units by sitting next to Alice.
 * Carol would gain 60 happiness units by sitting next to Bob.
 * Carol would gain 55 happiness units by sitting next to David.
 * David would gain 46 happiness units by sitting next to Alice.
 * David would lose 7 happiness units by sitting next to Bob.
 * David would gain 41 happiness units by sitting next to Carol.
 * <p>
 * Then, if you seat Alice next to David, Alice would lose 2 happiness units (because David talks so much), but David would gain 46 happiness units (because Alice is such a good listener), for a total change of 44.
 * <p>
 * If you continue around the table, you could then seat Bob next to Alice (Bob gains 83, Alice gains 54).
 * Finally, seat Carol, who sits next to Bob (Carol gains 60, Bob loses 7) and David (Carol gains 55, David gains 41). The arrangement looks like this:
 * <p>
 *      +41 +46
 * +55   David    -2
 * Carol       Alice
 * +60    Bob    +54
 *      -7  +83
 * <p>
 * After trying every other seating arrangement in this hypothetical scenario, you find that this one is the most optimal, with a total change in happiness of 330.
 * <p>
 * What is the total change in happiness for the optimal seating arrangement of the actual guest list?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * In all the commotion, you realize that you forgot to seat yourself. At this point, you're pretty apathetic toward the whole thing, and your happiness wouldn't really go up or down regardless of who you sit next to. You assume everyone else would be just as ambivalent about sitting next to you, too.
 * <p>
 * So, add yourself to the list, and give all happiness relationships that involve you a score of 0.
 * <p>
 * What is the total change in happiness for the optimal seating arrangement that actually includes yourself?
 */
public class Day13 implements DayInterface {

    private class Arrangement {
        String person;
        String nextTo;
        Integer happiness;

        Arrangement(String person, String nextTo, Integer happiness) {
            this.person = person;
            this.nextTo = nextTo;
            this.happiness = happiness;
        }

        String getPerson() {
            return person;
        }

        String getNextTo() {
            return nextTo;
        }

        Integer getHappiness() {
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
            for (int i = 0; i < planSize; i++) {
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

                if (position == planSize - 1) {
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
            for (int i = 0; i < planSize; i++) {
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

                if (position == planSize - 1) {
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
