package fr.rochet.days;

import fr.rochet.DayInterface;

public class Day10 implements DayInterface {

    @Override
    public void part1() {
        process(40);
    }

    @Override
    public void part2() {
        process(50);
    }

    private void process(int rounds) {
        String entry = "3113322113";

        String currentSeq = entry;

        for (int i = 0; i < rounds; i++) {
            StringBuilder nextSeq = new StringBuilder();
            Character previousChar = null;
            int count = 1;
            for (char c : currentSeq.toCharArray()) {
                if (previousChar == null) {
                    previousChar = c;
                } else if (previousChar == c) {
                    count++;
                } else {
                    nextSeq.append(count);
                    nextSeq.append(previousChar);
                    previousChar = c;
                    count = 1;
                }
            }
            nextSeq.append(count);
            nextSeq.append(previousChar);

            currentSeq = nextSeq.toString();
        }

        System.out.println("Length of sequence for " + rounds + " rounds : " + currentSeq.length());
    }
}
