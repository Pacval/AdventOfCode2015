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