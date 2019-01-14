package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.*;

/**
 * --- Day 19: Medicine for Rudolph ---
 * <p>
 * Rudolph the Red-Nosed Reindeer is sick! His nose isn't shining very brightly, and he needs medicine.
 * <p>
 * Red-Nosed Reindeer biology isn't similar to regular reindeer biology; Rudolph is going to need custom-made medicine. Unfortunately, Red-Nosed Reindeer chemistry isn't similar to regular reindeer chemistry, either.
 * <p>
 * The North Pole is equipped with a Red-Nosed Reindeer nuclear fusion/fission plant, capable of constructing any Red-Nosed Reindeer molecule you need.
 * It works by starting with some input molecule and then doing a series of replacements, one per step, until it has the right molecule.
 * <p>
 * However, the machine has to be calibrated before it can be used. Calibration involves determining the number of molecules that can be generated in one step from a given starting point.
 * <p>
 * For example, imagine a simpler machine that supports only the following replacements:
 * <p>
 * H => HO
 * H => OH
 * O => HH
 * <p>
 * Given the replacements above and starting with HOH, the following molecules could be generated:
 * <p>
 * HOOH (via H => HO on the first H).
 * HOHO (via H => HO on the second H).
 * OHOH (via H => OH on the first H).
 * HOOH (via H => OH on the second H).
 * HHHH (via O => HH).
 * <p>
 * So, in the example above, there are 4 distinct molecules (not five, because HOOH appears twice) after one replacement from HOH.
 * Santa's favorite molecule, HOHOHO, can become 7 distinct molecules (over nine replacements: six from H, and three from O).
 * <p>
 * The machine replaces without regard for the surrounding characters. For example, given the string H2O, the transition H => OO would result in OO2O.
 * <p>
 * Your puzzle input describes all of the possible replacements and, at the bottom, the medicine molecule for which you need to calibrate the machine.
 * How many distinct molecules can be created after all the different ways you can do one replacement on the medicine molecule?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * Now that the machine is calibrated, you're ready to begin molecule fabrication.
 * <p>
 * Molecule fabrication always begins with just a single electron, e, and applying replacements one at a time, just like the ones during calibration.
 * <p>
 * For example, suppose you have the following replacements:
 * <p>
 * e => H
 * e => O
 * H => HO
 * H => OH
 * O => HH
 * <p>
 * If you'd like to make HOH, you start with e, and then make the following replacements:
 * <p>
 * e => O to get O
 * O => HH to get HH
 * H => OH (on the second H) to get HOH
 * <p>
 * So, you could make HOH after 3 steps. Santa's favorite molecule, HOHOHO, can be made in 6 steps.
 * <p>
 * How long will it take to make the medicine? Given the available replacements and the medicine molecule in your puzzle input, what is the fewest number of steps to go from e to the medicine molecule?
 */
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

        // En analysant les changements, on remarque que les "Ca" sont inutiles
        int stepsIgnored = startMolecule.split("Ca").length - 1;
        startMolecule = startMolecule.replaceAll("Ca", "");

        minSteps = Integer.MAX_VALUE;
        seenMolecules = new ArrayList<>();
        getPreviousMolecule(startMolecule, stepsIgnored);

        System.out.println("The molecule can be made in " + minSteps + " steps");

    }

    private List<String[]> replacements;
    private List<String> seenMolecules;
    private int minSteps;

    // partie 2 on utilise la récursivité
    private void getPreviousMolecule(String currentMolecule, int steps) {
        if (!seenMolecules.contains(currentMolecule)) {
            seenMolecules.add(currentMolecule);
            //System.out.println(steps + "\t" + currentMolecule);
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
