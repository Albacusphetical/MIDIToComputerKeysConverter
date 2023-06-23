package utils;

import java.io.File;

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
}

