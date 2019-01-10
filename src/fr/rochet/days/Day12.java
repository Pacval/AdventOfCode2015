package fr.rochet.days;

<<<<<<< HEAD
import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
=======
import com.sun.javafx.collections.MappingChange;
import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
>>>>>>> 8fdbe75db2267690fca9b9fcdceeb53ba38ad367

public class Day12 implements DayInterface {

    @Override
    public void part1() throws IOException {
<<<<<<< HEAD
        String entry = ExoUtils.getEntries(12)[0];

        String regex = "-?\\d+";
        Matcher matcher = Pattern.compile(regex).matcher(entry);

        int total = 0;
        while (matcher.find()) {
            total += Integer.parseInt(matcher.group(0));
        }

        System.out.println("Total of numbers : " + total);
=======

>>>>>>> 8fdbe75db2267690fca9b9fcdceeb53ba38ad367
    }

    @Override
    public void part2() throws IOException {
<<<<<<< HEAD

=======
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
                for(Object obj : innerMap.values()) {
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
>>>>>>> 8fdbe75db2267690fca9b9fcdceeb53ba38ad367
    }
}
