/*
 * Decompiled with CFR 0.150.
 */
package midiparser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class KeyConverter {
    private static ArrayList<String> keyboardKeys = new ArrayList();

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
}

