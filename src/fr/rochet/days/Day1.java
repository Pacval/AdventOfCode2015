package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;

/**
 * --- Day 1: Not Quite Lisp ---
 * <p>
 * Santa was hoping for a white Christmas, but his weather machine's "snow" function is powered by stars, and he's fresh out! To save Christmas, he needs you to collect fifty stars by December 25th.
 * <p>
 * Collect stars by helping Santa solve puzzles. Two puzzles will be made available on each day in the advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star.
 * Good luck!
 * <p>
 * Here's an easy puzzle to warm you up.
 * <p>
 * Santa is trying to deliver presents in a large apartment building, but he can't find the right floor - the directions he got are a little confusing.
 * He starts on the ground floor (floor 0) and then follows the instructions one character at a time.
 * <p>
 * An opening parenthesis, (, means he should go up one floor, and a closing parenthesis, ), means he should go down one floor.
 * <p>
 * The apartment building is very tall, and the basement is very deep; he will never find the top or bottom floors.
 * <p>
 * For example:
 * <p>
 * (()) and ()() both result in floor 0.
 * ((( and (()(()( both result in floor 3.
 * ))((((( also results in floor 3.
 * ()) and ))( both result in floor -1 (the first basement level).
 * ))) and )())()) both result in floor -3.
 * <p>
 * To what floor do the instructions take Santa?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * Now, given the same instructions, find the position of the first character that causes him to enter the basement (floor -1).
 * The first character in the instructions has position 1, the second character has position 2, and so on.
 * <p>
 * For example:
 * <p>
 * ) causes him to enter the basement at character position 1.
 * ()()) causes him to enter the basement at character position 5.
 * <p>
 * What is the position of the character that causes Santa to first enter the basement?
 */
public class Day1 implements DayInterface {

    @Override
    public void part1() throws IOException {
        String entry = ExoUtils.getEntries(1)[0];

        int floor = 0;
        for (char c : entry.toCharArray()) {
            if (c == '(') {
                floor++;
            } else if (c == ')') {
                floor--;
            } else {
                System.out.println("Unknown character : " + c);
            }
        }
        System.out.println("Floor to go : " + floor);
    }

    @Override
    public void part2() throws IOException {
        String entry = ExoUtils.getEntries(1)[0];

        int floor = 0;
        int count = 0;
        while (floor >= 0) {
            if (entry.charAt(count) == '(') {
                floor++;
            } else if (entry.charAt(count) == ')') {
                floor--;
            } else {
                System.out.println("Unknown character : " + entry.charAt(count));
            }
            count++;
        }
        System.out.println("Entering 1st basement at position : " + count);
    }
}
