package utils;

import java.io.File;
import java.util.List;

public class Utils {
    public static final String midi = "midi";
    public static final String mid = "mid";
    public static final String txt = "txt";

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf(46);
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public static int getLineNumberOfCharacterIndex(int charIndex, String text) {
        List<String> lines = List.of(text.split("\n"));

        int currentLnum = 0;
        int currentCharIndex = 0;
        for (String line : lines) {
            for (Character character : line.toCharArray()) {
                if (currentCharIndex == charIndex + 1) {
                    break;
                }
                currentCharIndex++;
            }

            if (currentCharIndex == charIndex + 1) {
                break;
            }

            currentLnum++;
        }

        return currentLnum;
    }
}

