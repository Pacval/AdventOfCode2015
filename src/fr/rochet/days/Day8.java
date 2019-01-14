package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * --- Day 8: Matchsticks ---
 * <p>
 * Space on the sleigh is limited this year, and so Santa will be bringing his list as a digital copy. He needs to know how much space it will take up when stored.
 * <p>
 * It is common in many programming languages to provide a way to escape special characters in strings. For example, C, JavaScript, Perl, Python, and even PHP handle special characters in very similar ways.
 * <p>
 * However, it is important to realize the difference between the number of characters in the code representation of the string literal and the number of characters in the in-memory string itself.
 * <p>
 * For example:
 * <p>
 * "" is 2 characters of code (the two double quotes), but the string contains zero characters.
 * "abc" is 5 characters of code, but 3 characters in the string data.
 * "aaa\"aaa" is 10 characters of code, but the string itself contains six "a" characters and a single, escaped quote character, for a total of 7 characters in the string data.
 * "\x27" is 6 characters of code, but the string itself contains just one - an apostrophe ('), escaped using hexadecimal notation.
 * <p>
 * Santa's list is a file that contains many double-quoted string literals, one on each line.
 * The only escape sequences used are \\ (which represents a single backslash), \" (which represents a lone double-quote character), and \x plus two hexadecimal characters
 * (which represents a single character with that ASCII code).
 * <p>
 * Disregarding the whitespace in the file, what is the number of characters of code for string literals minus the number of characters in memory for the values of the strings in total for the entire file?
 * <p>
 * For example, given the four strings above, the total number of characters of string code (2 + 5 + 10 + 6 = 23) minus the total number of characters in memory for string values
 * (0 + 3 + 7 + 1 = 11) is 23 - 11 = 12.
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * Now, let's go the other way. In addition to finding the number of characters of code, you should now encode each code representation as a new string and find the number of characters
 * of the new encoded representation, including the surrounding double quotes.
 * <p>
 * For example:
 * <p>
 * "" encodes to "\"\"", an increase from 2 characters to 6.
 * "abc" encodes to "\"abc\"", an increase from 5 characters to 9.
 * "aaa\"aaa" encodes to "\"aaa\\\"aaa\"", an increase from 10 characters to 16.
 * "\x27" encodes to "\"\\x27\"", an increase from 6 characters to 11.
 * <p>
 * Your task is to find the total number of characters to represent the newly encoded strings minus the number of characters of code in each original string literal.
 * For example, for the strings above, the total encoded length (6 + 9 + 16 + 11 = 42) minus the characters in the original code representation (23, just like in the first part of this puzzle) is 42 - 23 = 19.
 */
public class Day8 implements DayInterface {

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(8);

        int totalString = Arrays.stream(entries).mapToInt(String::length).sum();
        int totalMemoryString = 0;

        for (String str : entries) {
            char[] cArray = str.toCharArray();
            int i = 0;
            while (i < cArray.length) {
                if (cArray[i] == '\\') {
                    totalMemoryString++;
                    i++;
                    if (cArray[i] == 'x') {
                        i += 2;
                    }
                } else if (Character.isLetter(cArray[i])) {
                    totalMemoryString++;
                }
                i++;
            }
        }
        System.out.println("Total code string : " + totalString);
        System.out.println("Total characters in memory : " + totalMemoryString);
        System.out.println("Result : " + (totalString - totalMemoryString));
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(8);

        int totalOriginalString = Arrays.stream(entries).mapToInt(String::length).sum();
        int totalNewString = Arrays.stream(entries).mapToInt(item -> item.length() // longueur string
                + (int) item.chars().filter(c -> c == '\\').count() // ajout 1 char pour chaque \
                + (int) item.chars().filter(c -> c == '"').count() // ajout 1 char pour chaque "
                + 2) // ajout des 2 guillemets début et fin
                .sum();

        System.out.println("Total code original string : " + totalOriginalString);
        System.out.println("Total characters of new strings : " + totalNewString);
        System.out.println("Result : " + (totalNewString - totalOriginalString));
    }
}
