/*
 * Decompiled with CFR 0.150.
 */
package utils;

import utils.Utils;
import java.io.File;
import java.util.Objects;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class FileExtensionFilter
extends FileFilter {
    private boolean isMIDI = false;
    private boolean isImage = false;
    private static String fileChooserText = "";

    public FileExtensionFilter(boolean isMIDI, boolean isImage, JTextField fileChooserTextField) {
        this.isMIDI = isMIDI;
        this.isImage = isImage;
        FileExtensionFilter.fileChooserText = fileChooserTextField.getText().toLowerCase();
    }

    public FileExtensionFilter(boolean isMIDI) {
        this.isMIDI = isMIDI;
        FileExtensionFilter.fileChooserText = "";
    }

    @Override
    public boolean accept(File f) {
        String fileName = f.getName().toLowerCase();
        boolean fileNameFilter = Objects.equals(fileChooserText, "") || fileName.equals(fileChooserText) || fileName.startsWith(fileChooserText);

        if (f.isDirectory() && fileNameFilter) {
            return true;
        }
        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (isMIDI) {
                return (extension.equals("mid") || extension.equals("midi")) && fileNameFilter;
            }
            if (isImage) {
                return (extension.equals("png") || extension.equals("jpg")) && fileNameFilter;
            }
            return extension.equals("txt") && fileNameFilter;
        }
        return false;
    }

    @Override
    public String getDescription() {
        String x = "Text File";
        if (isMIDI) {
            x = "MIDI File";
        }
        if (isImage) {
            x = "Images";
        }
        return x;
    }
}

