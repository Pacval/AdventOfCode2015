package fr.rochet.days;

import fr.rochet.DayInterface;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * --- Day 4: The Ideal Stocking Stuffer ---
 * <p>
 * Santa needs help mining some AdventCoins (very similar to bitcoins) to use as gifts for all the economically forward-thinking little girls and boys.
 * <p>
 * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least five zeroes. The input to the MD5 hash is some secret key (your puzzle input, given below) followed by a number in decimal.
 * To mine AdventCoins, you must find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...) that produces such a hash.
 * <p>
 * For example:
 * <p>
 * If your secret key is abcdef, the answer is 609043, because the MD5 hash of abcdef609043 starts with five zeroes (000001dbbfa...), and it is the lowest such number to do so.
 * If your secret key is pqrstuv, the lowest number it combines with to make an MD5 hash starting with five zeroes is 1048970; that is, the MD5 hash of pqrstuv1048970 looks like 000006136ef....
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * Now find one that starts with six zeroes.
 */
public class Day4 implements DayInterface {
    @Override
    public void part1() {
        String privateKey = "bgvyzdsv";

        int result = -1;
        int count = 0;

        while (result == -1) {
            count++;
            String publicKey = DigestUtils.md5Hex(privateKey + count);
            if (publicKey.length() >= 5 && publicKey.substring(0, 5).equals("00000")) {
                result = count;
            }
        }

        System.out.println("Lowest number to match : " + result);
    }

    @Override
    public void part2() {
        String privateKey = "bgvyzdsv";

        int result = -1;
        int count = 0;

        while (result == -1) {
            count++;
            String publicKey = DigestUtils.md5Hex(privateKey + count);
            if (publicKey.length() >= 5 && publicKey.substring(0, 6).equals("000000")) {
                result = count;
            }
        }

        System.out.println("Lowest number to match : " + result);

    }
}
