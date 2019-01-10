package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 implements DayInterface {

    @Override
    public void part1() throws IOException {
        String entry = ExoUtils.getEntries(12)[0];

        String regex = "-?\\d+";
        Matcher matcher = Pattern.compile(regex).matcher(entry);

        int total = 0;
        while (matcher.find()) {
            total += Integer.parseInt(matcher.group(0));
        }

        System.out.println("Total of numbers : " + total);
    }

    @Override
    public void part2() throws IOException {

    }
}
