package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * --- Day 24: It Hangs in the Balance ---
 * <p>
 * It's Christmas Eve, and Santa is loading up the sleigh for this year's deliveries. However, there's one small problem: he can't get the sleigh to balance.
 * If it isn't balanced, he can't defy physics, and nobody gets presents this year.
 * <p>
 * No pressure.
 * <p>
 * Santa has provided you a list of the weights of every package he needs to fit on the sleigh. The packages need to be split into three groups of exactly the same weight, and every package has to fit.
 * The first group goes in the passenger compartment of the sleigh, and the second and third go in containers on either side.
 * Only when all three groups weigh exactly the same amount will the sleigh be able to fly. Defying physics has rules, you know!
 * <p>
 * Of course, that's not the only problem. The first group - the one going in the passenger compartment - needs as few packages as possible so that Santa has some legroom left over.
 * It doesn't matter how many packages are in either of the other two groups, so long as all of the groups weigh the same.
 * <p>
 * Furthermore, Santa tells you, if there are multiple ways to arrange the packages such that the fewest possible are in the first group,
 * you need to choose the way where the first group has the smallest quantum entanglement to reduce the chance of any "complications".
 * The quantum entanglement of a group of packages is the product of their weights, that is, the value you get when you multiply their weights together.
 * Only consider quantum entanglement if the first group has the fewest possible number of packages in it and all groups weigh the same amount.
 * <p>
 * For example, suppose you have ten packages with weights 1 through 5 and 7 through 11. For this situation, some of the unique first groups, their quantum entanglements,
 * and a way to divide the remaining packages are as follows:
 * <p>
 * Group 1;             Group 2; Group 3
 * 11 9       (QE= 99); 10 8 2;  7 5 4 3 1
 * 10 9 1     (QE= 90); 11 7 2;  8 5 4 3
 * 10 8 2     (QE=160); 11 9;    7 5 4 3 1
 * 10 7 3     (QE=210); 11 9;    8 5 4 2 1
 * 10 5 4 1   (QE=200); 11 9;    8 7 3 2
 * 10 5 3 2   (QE=300); 11 9;    8 7 4 1
 * 10 4 3 2 1 (QE=240); 11 9;    8 7 5
 * 9 8 3      (QE=216); 11 7 2;  10 5 4 1
 * 9 7 4      (QE=252); 11 8 1;  10 5 3 2
 * 9 5 4 2    (QE=360); 11 8 1;  10 7 3
 * 8 7 5      (QE=280); 11 9;    10 4 3 2 1
 * 8 5 4 3    (QE=480); 11 9;    10 7 2 1
 * 7 5 4 3 1  (QE=420); 11 9;    10 8 2
 * <p>
 * Of these, although 10 9 1 has the smallest quantum entanglement (90), the configuration with only two packages, 11 9, in the passenger compartment gives Santa the most legroom and wins.
 * In this situation, the quantum entanglement for the ideal configuration is therefore 99. Had there been two configurations with only two packages in the first group,
 * the one with the smaller quantum entanglement would be chosen.
 * <p>
 * What is the quantum entanglement of the first group of packages in the ideal configuration?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * That's weird... the sleigh still isn't balancing.
 * <p>
 * "Ho ho ho", Santa muses to himself. "I forgot the trunk".
 * <p>
 * Balance the sleigh again, but this time, separate the packages into four groups instead of three. The other constraints still apply.
 * <p>
 * Given the example packages above, this would be some of the new unique first groups, their quantum entanglements, and one way to divide the remaining packages:
 * <p>
 * <p>
 * 11 4    (QE=44); 10 5;   9 3 2 1; 8 7
 * 10 5    (QE=50); 11 4;   9 3 2 1; 8 7
 * 9 5 1   (QE=45); 11 4;   10 3 2;  8 7
 * 9 4 2   (QE=72); 11 3 1; 10 5;    8 7
 * 9 3 2 1 (QE=54); 11 4;   10 5;    8 7
 * 8 7     (QE=56); 11 4;   10 5;    9 3 2 1
 * <p>
 * Of these, there are three arrangements that put the minimum (two) number of packages in the first group: 11 4, 10 5, and 8 7. Of these, 11 4 has the lowest quantum entanglement, and so it is selected.
 * <p>
 * Now, what is the quantum entanglement of the first group of packages in the ideal configuration?
 */
public class Day24 implements DayInterface {

    private class Sleigh {
        private List<Integer> group1;
        private List<Integer> group2;
        private List<Integer> group3;

        Sleigh() {
            group1 = new ArrayList<>();
            group2 = new ArrayList<>();
            group3 = new ArrayList<>();
        }

        Sleigh(Sleigh sleigh) {
            group1 = new ArrayList<>(sleigh.group1);
            group2 = new ArrayList<>(sleigh.group2);
            group3 = new ArrayList<>(sleigh.group3);
        }

        void addTo1(int i) {
            group1.add(i);
        }

        void addTo2(int i) {
            group2.add(i);
        }

        void addTo3(int i) {
            group3.add(i);
        }

        boolean isBalanced() {
            return group1.stream().mapToInt(e -> e).sum() == group2.stream().mapToInt(e -> e).sum()
                    && group1.stream().mapToInt(e -> e).sum() == group3.stream().mapToInt(e -> e).sum();
        }

        // le traineau ne peut plus etre équilibré si un des groupe à déja dépassé le poids requis
        // permet de virer les traineaux inutiles, donc de libérer de la mémoire
        boolean canBebalanced(int maxGroupWeight) {
            return group1.stream().mapToInt(e -> e).sum() <= maxGroupWeight
                    && group2.stream().mapToInt(e -> e).sum() <= maxGroupWeight
                    && group3.stream().mapToInt(e -> e).sum() <= maxGroupWeight;
        }

        // permet de vérifier si il est utile de continuer avec ce traineau
        // si le traineau a plus de cadeaux devant que le traineau a battre, alors on arrete
        boolean isPlaceLeft(Sleigh perfectSleigh) {
            return perfectSleigh == null || perfectSleigh.group1.size() >= this.group1.size();
        }

        BigInteger getQuantumEntanglement() {
            return group1.stream().map(BigInteger::valueOf).reduce(BigInteger.ONE, BigInteger::multiply);
        }

        @Override
        public String toString() {
            return "Sleigh{" +
                    "group1=" + group1.toString() +
                    ", group2=" + group2.toString() +
                    ", group3=" + group3.toString() +
                    '}';
        }
    }

    private Sleigh perfectSleigh;

    private void generateAllBalancedSleighs(Sleigh sleigh, List<Integer> remainingGifts, int balancedWeight) {
        if (remainingGifts.isEmpty() && sleigh.isBalanced()) {
            System.out.println("Balanced sleigh : " + sleigh.toString());
            // Succès : le traineau est équilibré
            if (perfectSleigh == null) {
                perfectSleigh = sleigh;
            } else {
                if (sleigh.group1.size() < perfectSleigh.group1.size() // si moins de cadeauw devant
                        || // ou
                        (sleigh.group1.size() == perfectSleigh.group1.size() // si autant de cadeaux devant
                                && sleigh.getQuantumEntanglement().compareTo(perfectSleigh.getQuantumEntanglement()) < 0)) { // mais plus petit "quantum entanglement"
                    perfectSleigh = sleigh;
                }
            }

        } else {
            List<Integer> subGifts = new ArrayList<>(remainingGifts);
            int gift = subGifts.remove(0);

            Sleigh tempSleigh = new Sleigh(sleigh);
            tempSleigh.addTo1(gift);
            if (tempSleigh.canBebalanced(balancedWeight) && tempSleigh.isPlaceLeft(perfectSleigh)) {
                generateAllBalancedSleighs(tempSleigh, subGifts, balancedWeight);
            }

            tempSleigh = new Sleigh(sleigh);
            tempSleigh.addTo2(gift);
            if (tempSleigh.canBebalanced(balancedWeight) && tempSleigh.isPlaceLeft(perfectSleigh)) {
                generateAllBalancedSleighs(tempSleigh, subGifts, balancedWeight);
            }

            tempSleigh = new Sleigh(sleigh);
            tempSleigh.addTo3(gift);
            if (tempSleigh.canBebalanced(balancedWeight) && tempSleigh.isPlaceLeft(perfectSleigh)) {
                generateAllBalancedSleighs(tempSleigh, subGifts, balancedWeight);
            }
        }
    }

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(24);

        List<Integer> gifts = Arrays.stream(entries).map(Integer::parseInt).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        int balancedWeight = gifts.stream().mapToInt(e -> e).sum() / 3;

        generateAllBalancedSleighs(new Sleigh(), gifts, balancedWeight);

        System.out.println("Most balanced sleigh is : " + perfectSleigh.toString());
        System.out.println("With a quantum entanglement of " + perfectSleigh.getQuantumEntanglement());
    }

    /* -------------------------------------------------------------
    ----------------------------------------------------------------
    ---------------------------- PART 2 ----------------------------
    ----------------------------------------------------------------
    ---------------------------------------------------------------- */

    private class SleighWithTrunk {
        private List<Integer> group1;
        private List<Integer> group2;
        private List<Integer> group3;
        private List<Integer> group4;

        SleighWithTrunk() {
            group1 = new ArrayList<>();
            group2 = new ArrayList<>();
            group3 = new ArrayList<>();
            group4 = new ArrayList<>();
        }

        SleighWithTrunk(SleighWithTrunk sleigh) {
            group1 = new ArrayList<>(sleigh.group1);
            group2 = new ArrayList<>(sleigh.group2);
            group3 = new ArrayList<>(sleigh.group3);
            group4 = new ArrayList<>(sleigh.group4);
        }

        void addTo1(int i) {
            group1.add(i);
        }

        void addTo2(int i) {
            group2.add(i);
        }

        void addTo3(int i) {
            group3.add(i);
        }

        void addTo4(int i) {
            group4.add(i);
        }

        boolean isBalanced() {
            return group1.stream().mapToInt(e -> e).sum() == group2.stream().mapToInt(e -> e).sum()
                    && group1.stream().mapToInt(e -> e).sum() == group3.stream().mapToInt(e -> e).sum()
                    && group1.stream().mapToInt(e -> e).sum() == group4.stream().mapToInt(e -> e).sum();
        }

        // le traineau ne peut plus etre équilibré si un des groupe à déja dépassé le poids requis
        // permet de virer les traineaux inutiles, donc de libérer de la mémoire
        boolean canBebalanced(int maxGroupWeight) {
            return group1.stream().mapToInt(e -> e).sum() <= maxGroupWeight
                    && group2.stream().mapToInt(e -> e).sum() <= maxGroupWeight
                    && group3.stream().mapToInt(e -> e).sum() <= maxGroupWeight
                    && group4.stream().mapToInt(e -> e).sum() <= maxGroupWeight;
        }

        // permet de vérifier si il est utile de continuer avec ce traineau
        // si le traineau a plus de cadeaux devant que le traineau a battre, alors on arrete
        boolean isPlaceLeft(SleighWithTrunk sleighWithTrunk) {
            return sleighWithTrunk == null || sleighWithTrunk.group1.size() >= this.group1.size();
        }

        BigInteger getQuantumEntanglement() {
            return group1.stream().map(BigInteger::valueOf).reduce(BigInteger.ONE, BigInteger::multiply);
        }

        @Override
        public String toString() {
            return "SleighWithTrunk{" +
                    "group1=" + group1 +
                    ", group2=" + group2 +
                    ", group3=" + group3 +
                    ", group4=" + group4 +
                    '}';
        }
    }

    private SleighWithTrunk perfectSleighWithTrunk;

    private void generateAllBalancedSleighsWithTrunks(SleighWithTrunk sleigh, List<Integer> remainingGifts, int balancedWeight) {
        if (remainingGifts.isEmpty() && sleigh.isBalanced()) {
            System.out.println("Balanced sleigh : " + sleigh.toString());
            // Succès : le traineau est équilibré
            if (perfectSleighWithTrunk == null) {
                perfectSleighWithTrunk = sleigh;
            } else {
                if (sleigh.group1.size() < perfectSleighWithTrunk.group1.size() // si moins de cadeauw devant
                        || // ou
                        (sleigh.group1.size() == perfectSleighWithTrunk.group1.size() // si autant de cadeaux devant
                                && sleigh.getQuantumEntanglement().compareTo(perfectSleighWithTrunk.getQuantumEntanglement()) < 0)) { // mais plus petit "quantum entanglement"
                    perfectSleighWithTrunk = sleigh;
                }
            }

        } else {
            List<Integer> subGifts = new ArrayList<>(remainingGifts);
            int gift = subGifts.remove(0);

            SleighWithTrunk tempSleigh = new SleighWithTrunk(sleigh);
            tempSleigh.addTo1(gift);
            if (tempSleigh.canBebalanced(balancedWeight) && tempSleigh.isPlaceLeft(perfectSleighWithTrunk)) {
                generateAllBalancedSleighsWithTrunks(tempSleigh, subGifts, balancedWeight);
            }

            tempSleigh = new SleighWithTrunk(sleigh);
            tempSleigh.addTo2(gift);
            if (tempSleigh.canBebalanced(balancedWeight) && tempSleigh.isPlaceLeft(perfectSleighWithTrunk)) {
                generateAllBalancedSleighsWithTrunks(tempSleigh, subGifts, balancedWeight);
            }

            tempSleigh = new SleighWithTrunk(sleigh);
            tempSleigh.addTo3(gift);
            if (tempSleigh.canBebalanced(balancedWeight) && tempSleigh.isPlaceLeft(perfectSleighWithTrunk)) {
                generateAllBalancedSleighsWithTrunks(tempSleigh, subGifts, balancedWeight);
            }

            tempSleigh = new SleighWithTrunk(sleigh);
            tempSleigh.addTo4(gift);
            if (tempSleigh.canBebalanced(balancedWeight) && tempSleigh.isPlaceLeft(perfectSleighWithTrunk)) {
                generateAllBalancedSleighsWithTrunks(tempSleigh, subGifts, balancedWeight);
            }
        }
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(24);

        List<Integer> gifts = Arrays.stream(entries).map(Integer::parseInt).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        int balancedWeight = gifts.stream().mapToInt(e -> e).sum() / 4;

        generateAllBalancedSleighsWithTrunks(new SleighWithTrunk(), gifts, balancedWeight);

        System.out.println("Most balanced sleigh with trunk is : " + perfectSleighWithTrunk.toString());
        System.out.println("With a quantum entanglement of " + perfectSleighWithTrunk.getQuantumEntanglement());
    }
}