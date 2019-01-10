package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.Arrays;

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
                if(cArray[i] == '\\'){
                    totalMemoryString++;
                    i++;
                    if(cArray[i] == 'x') {
                        i+=2;
                    }
                } else if(Character.isLetter(cArray[i])){
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
                + (int)item.chars().filter(c -> c == '\\').count() // ajout 1 char pour chaque \
                + (int)item.chars().filter(c -> c == '"').count() // ajout 1 char pour chaque "
                + 2) // ajout des 2 guillemets début et fin
                .sum();

        System.out.println("Total code original string : " + totalOriginalString);
        System.out.println("Total characters of new strings : " + totalNewString);
        System.out.println("Result : " + (totalNewString - totalOriginalString));
    }
}
