package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * --- Day 12: JSAbacusFramework.io ---
 * <p>
 * Santa's Accounting-Elves need help balancing the books after a recent order. Unfortunately, their accounting software uses a peculiar storage format. That's where you come in.
 * <p>
 * They have a JSON document which contains a variety of things: arrays ([1,2,3]), objects ({"a":1, "b":2}), numbers, and strings.
 * Your first job is to simply find all of the numbers throughout the document and add them together.
 * <p>
 * For example:
 * <p>
 * [1,2,3] and {"a":2,"b":4} both have a sum of 6.
 * [[[3]]] and {"a":{"b":4},"c":-1} both have a sum of 3.
 * {"a":[-1,1]} and [-1,{"a":1}] both have a sum of 0.
 * [] and {} both have a sum of 0.
 * <p>
 * You will not encounter any strings containing numbers.
 * <p>
 * What is the sum of all numbers in the document?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * Uh oh - the Accounting-Elves have realized that they double-counted everything red.
 * <p>
 * Ignore any object (and all of its children) which has any property with the value "red". Do this only for objects ({...}), not arrays ([...]).
 * <p>
 * [1,2,3] still has a sum of 6.
 * [1,{"c":"red","b":2},3] now has a sum of 4, because the middle object is ignored.
 * {"d":"red","e":[1,2,3,4],"f":5} now has a sum of 0, because the entire structure is ignored.
 * [1,"red",5] has a sum of 6, because "red" in an array has no effect.
 */
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
        String entry = ExoUtils.getEntries(12)[0];

        JSONObject jsonObject = new JSONObject(entry);
        Map<String, Object> jsonMap = jsonObject.toMap();

        int jsonValue = getValue(jsonMap);

        System.out.println("Total value of JSON without \"red\" : " + jsonValue);
    }

    private int getValue(Object jsonObj) {
        if (jsonObj instanceof Map) {
            Map<String, Object> innerMap = (Map<String, Object>) jsonObj;
            if (innerMap.containsValue("red")) {
                return 0;
            } else {
                int innerTotal = 0;
                for (Object obj : innerMap.values()) {
                    innerTotal += getValue(obj);
                }
                return innerTotal;
            }
        } else if (jsonObj instanceof List) {
            int innerTotal = 0;
            for (Object obj : (List) jsonObj) {
                innerTotal += getValue(obj);
            }
            return innerTotal;
        } else {
            try {
                return Integer.valueOf(jsonObj.toString());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }
}
