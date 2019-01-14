package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;

/**
 * --- Day 5: Doesn't He Have Intern-Elves For This? ---
 * <p>
 * Santa needs help figuring out which strings in his text file are naughty or nice.
 * <p>
 * A nice string is one with all of the following properties:
 * <p>
 * It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
 * It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
 * It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
 * <p>
 * For example:
 * <p>
 * ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
 * aaa is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
 * jchzalrnumimnmhp is naughty because it has no double letter.
 * haegwjzuvuyypxyu is naughty because it contains the string xy.
 * dvszwmarrgswjxmb is naughty because it contains only one vowel.
 * <p>
 * How many strings are nice?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * Realizing the error of his ways, Santa has switched to a better model of determining whether a string is naughty or nice. None of the old rules apply, as they are all clearly ridiculous.
 * <p>
 * Now, a nice string is one with all of the following properties:
 * <p>
 * It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
 * It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
 * <p>
 * For example:
 * <p>
 * qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz).
 * xxyxx is nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap.
 * uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a single letter between them.
 * ieodomkazucvgmuy is naughty because it has a repeating letter with one between (odo), but no pair that appears twice.
 * <p>
 * How many strings are nice under these new rules?
 */
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
