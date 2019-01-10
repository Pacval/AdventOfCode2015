package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;

public class Day5 implements DayInterface {

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(5);

        int niceStrings = 0;
        for (String str : entries) {

            // Voyelle
            boolean vowelle = true;
            if ((int) str.chars()
                    .mapToObj(c -> (char) c)
                    .filter(c -> "aeiou".indexOf(c) > -1)
                    .count() < 3) {
                vowelle = false;
            }

            // lettres consécutives
            boolean consecutivLetters = false;
            for (int i = 0; i < str.length() - 1; i++) {
                if (str.charAt(i) == str.charAt(i + 1)) {
                    consecutivLetters = true;
                }
            }

            boolean noChain = !str.contains("ab") && !str.contains("cd") && !str.contains("pq") && !str.contains("xy");

            if (vowelle && consecutivLetters && noChain) {
                niceStrings++;
            }
        }
        System.out.println("Number of nice strings : " + niceStrings);
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(5);

        int niceStrings = 0;
        for (String str : entries) {

            boolean pair = false;
            for (int i = 0; i < str.length() - 1; i++) {
                String comp = str.substring(i, i + 2);
                for (int j = i + 2; j < str.length() - 1; j++) {
                    if (comp.equals(str.substring(j, j + 2))) {
                        pair = true;
                        break;
                    }
                    if (pair) {
                        break; // on sort de la boucle pour éviter de perdre du temps
                    }
                }
            }

            boolean repeat = false;
            for (int i = 0; i < str.length() - 2; i++) {
                if (str.charAt(i) == str.charAt(i + 2)) {
                    repeat = true;
                }
            }

            if (pair && repeat) {
                niceStrings++;
            }
        }

        System.out.println("Number of nice strings : " + niceStrings);
    }
}
