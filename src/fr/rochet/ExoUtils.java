package fr.rochet;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ExoUtils {

    public static String[] getEntries(int day) throws IOException {
        return getEntry(day).split("\r\n");
    }

    public static String getEntry(int day) throws IOException {
        return FileUtils.readFileToString(getDayFile(day), StandardCharsets.UTF_8);
    }

    public static File getDayFile(int day) {
        return new File("./src/fr/rochet/entries/day" + day);
    }
}
