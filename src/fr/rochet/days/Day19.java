package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.*;

public class Day19 implements DayInterface {

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(19);

        HashMap<String, List<String>> replacements = new HashMap<>();
        for (int i = 0; i < entries.length - 2; i++) {
            if (!replacements.containsKey(entries[i].split(" => ")[0])) {
                replacements.put(entries[i].split(" => ")[0], new ArrayList<>());
            }
            replacements.get(entries[i].split(" => ")[0]).add(entries[i].split(" => ")[1]);
        }
        String molecule = entries[entries.length - 1];

        Set<String> results = new HashSet<>();

        for (Map.Entry<String, List<String>> replacement : replacements.entrySet()) {
            if (molecule.contains(replacement.getKey())) {
                int position = 0;
                while ((position = molecule.indexOf(replacement.getKey(), position)) >= 0) {
                    for (String newStr : replacement.getValue()) {
                        results.add(molecule.substring(0, position) + newStr + molecule.substring(position + replacement.getKey().length(), molecule.length()));
                    }
                    position++;
                }
            }
        }

        System.out.println("Number of possibles molecules : " + results.size());
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(19);

        replacements = new ArrayList<>();
        for (int i = 0; i < entries.length - 2; i++) {
            replacements.add(new String[]{
                    entries[i].split(" => ")[0],
                    entries[i].split(" => ")[1]});
        }
        String startMolecule = entries[entries.length - 1];

        minSteps = Integer.MAX_VALUE;
        seenMolecules = new ArrayList<>();
        getPreviousMolecule(startMolecule, 0);

        System.out.println("The molecule can be made in " + minSteps + " steps");

    }

    private List<String[]> replacements;
    private List<String> seenMolecules;
    private int minSteps;

    // partie 2 on utilise la récursivité
    private void getPreviousMolecule(String currentMolecule, int steps) {
        if (!seenMolecules.contains(currentMolecule)) {
            seenMolecules.add(currentMolecule);
            System.out.println(steps + "\t" + currentMolecule);
            if (currentMolecule.equals("e")) {
                if (steps < minSteps) {
                    minSteps = steps;
                    System.out.println("Found : " + minSteps);
                }
            } else {
                for (String[] replacement : replacements) {
                    if (currentMolecule.contains(replacement[1])) {
                        int position = 0;
                        while ((position = currentMolecule.indexOf(replacement[1], position)) >= 0) {
                            // on récupère la molécule d'avant
                            getPreviousMolecule(currentMolecule.substring(0, position) +
                                            replacement[0] +
                                            currentMolecule.substring(position + replacement[1].length(), currentMolecule.length()),
                                    steps + 1);
                            position++;
                        }
                    }
                }
            }
        }
    }
}
