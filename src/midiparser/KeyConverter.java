/*
 * Decompiled with CFR 0.150.
 */
package midiparser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class KeyConverter {
    public static ArrayList<String> specialKeys = new ArrayList();
    private static ArrayList<String> keyboardKeys = new ArrayList();
    public static Set<String> specialLowOctaveNotes = new HashSet<>();
    public static Set<String> specialHighOctaveNotes = new HashSet<>();


    public KeyConverter() {
        this.setupDefaultKeys();
        this.init();
    }

    private void setupDefaultKeys() {
        for (int x = 0; x < 129; ++x) {
            keyboardKeys.add(x, "?");
        }
    }

    private void init() {
        InputStream input = this.getClass().getResourceAsStream("/data/MIDIGMOD_MSDOS.csv");
        Scanner sc = new Scanner(input).useDelimiter(",");
        while (sc.hasNextLine()) {
            int midiNum = Integer.parseInt(sc.next());
            String keyChar = sc.nextLine();

            keyChar = keyChar.replace(",", "");

            if (keyChar.length() > 1) {
                specialKeys.add(keyChar); // for 88-key support using special characters
                if (midiNum >= 21 && midiNum <= 35) {
                    // out of range, low octave
                    specialLowOctaveNotes.add(keyChar);
                }
                else if (midiNum >= 97 && midiNum <= 108) {
                    // out of range, high octave
                    specialHighOctaveNotes.add(keyChar);
                }
            }

            keyboardKeys.set(midiNum, keyChar);
        }
        sc.close();
    }

    public String convertMidiToKeyboard(int midiNum) {
        return keyboardKeys.get(midiNum);
    }

    public int convertKeyboardToMidi(String keyboardChar) {
        int y = 0;
        for (int x = 0; x < 129; ++x) {
            if (!keyboardKeys.get(x).contains(keyboardChar)) continue;
            y = x;
            break;
        }
        return y;
    }

    public boolean isSpecialLowOctaveNote(String note) {
        return specialLowOctaveNotes.contains(note);
    }

    public boolean isSpecialHighOctaveNote(String note) {
        return specialHighOctaveNotes.contains(note);
    }
}

