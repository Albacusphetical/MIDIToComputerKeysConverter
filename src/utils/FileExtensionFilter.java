/*
 * Decompiled with CFR 0.150.
 */
package utils;

import utils.Utils;
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileExtensionFilter
extends FileFilter {
    private static boolean isMIDI = false;

    public FileExtensionFilter(boolean isMIDI) {
        FileExtensionFilter.isMIDI = isMIDI;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (isMIDI) {
                return extension.equals("mid") || extension.equals("midi");
            }
            return extension.equals("txt");
        }
        return false;
    }

    @Override
    public String getDescription() {
        String x = null;
        x = isMIDI ? "MIDI File" : "Text File";
        return x;
    }
}

