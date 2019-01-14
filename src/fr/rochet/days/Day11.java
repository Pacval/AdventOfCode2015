package fr.rochet.days;

import fr.rochet.DayInterface;
import org.apache.commons.lang3.ArrayUtils;

/**
 * --- Day 11: Corporate Policy ---
 * <p>
 * Santa's previous password expired, and he needs help choosing a new one.
 * <p>
 * To help him remember his new password after the old one expires, Santa has devised a method of coming up with a password based on the previous one.
 * Corporate policy dictates that passwords must be exactly eight lowercase letters (for security reasons), so he finds his new password by incrementing his old password string repeatedly until it is valid.
 * <p>
 * Incrementing is just like counting with numbers: xx, xy, xz, ya, yb, and so on. Increase the rightmost letter one step; if it was z, it wraps around to a,
 * and repeat with the next letter to the left until one doesn't wrap around.
 * <p>
 * Unfortunately for Santa, a new Security-Elf recently started, and he has imposed some additional password requirements:
 * <p>
 * Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz. They cannot skip letters; abd doesn't count.
 * Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
 * Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
 * <p>
 * For example:
 * <p>
 * hijklmmn meets the first requirement (because it contains the straight hij) but fails the second requirement requirement (because it contains i and l).
 * abbceffg meets the third requirement (because it repeats bb and ff) but fails the first requirement.
 * abbcegjk fails the third requirement, because it only has one double letter (bb).
 * The next password after abcdefgh is abcdffaa.
 * The next password after ghijklmn is ghjaabcc, because you eventually skip all the passwords that start with ghi..., since i is not allowed.
 * <p>
 * Given Santa's current password (your puzzle input), what should his next password be?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * Santa's password expired again. What's the next one?
 */
public class Day11 implements DayInterface {

    // Fonction privée pour tester la validité du password
    private boolean isPasswordCorrect(char[] password) {
        boolean increasing = false;
        for (int i = 0; i < password.length - 2; i++) {
            if (password[i] + 1 == password[i + 1]
                    && password[i + 1] + 1 == password[i + 2]) {
                increasing = true;
            }
        }

        boolean contain = !ArrayUtils.contains(password, 'i')
                && !ArrayUtils.contains(password, 'o')
                && !ArrayUtils.contains(password, 'l');

        int consecutive = 0;
        int i = 0;
        while (i < password.length - 1) {
            if (password[i] == password[i + 1]) {
                consecutive++;
                i++;
            }
            i++;
        }

        return increasing && contain && (consecutive >= 2);
    }

    // Fonction récursive permettant d'incrémenter le password
    private void increasePassword(char[] password, int pos) {
        if (password[pos] == 'z') {
            password[pos] = 'a';
            if (pos != 0) {
                increasePassword(password, pos - 1);
            }
        } else {
            password[pos] = (char) (password[pos] + 1);
            if (password[pos] == 'i' || password[pos] == 'o' || password[pos] == 'l') {
                password[pos] = (char) (password[pos] + 1);
            }
        }
    }

    @Override
    public void part1() {
        String entry = "hxbxwxba";

        char[] password = entry.toCharArray();
        do {
            increasePassword(password, password.length - 1);
        } while (!isPasswordCorrect(password));

        System.out.println("Next valid password : " + String.valueOf(password));
    }

    @Override
    public void part2() {
        String entry = "hxbxxyzz";

        char[] password = entry.toCharArray();
        do {
            increasePassword(password, password.length - 1);
        } while (!isPasswordCorrect(password));

        System.out.println("Next valid password : " + String.valueOf(password));
    }
}
