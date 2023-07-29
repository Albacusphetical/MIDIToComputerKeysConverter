package midiparser;

import java.util.HashMap;
import java.util.Map;

public class AutoTransposer {
    // TODO: implemented brianops1 algorithm closely as originally written, see if it needs to change in future for 88-key support
    private Map<Object, Object> keyMap = new HashMap<>();
    private String str;

    public AutoTransposer(String str) {
        this.str = str;

        // for digify
        keyMap.put("𝟏", -15);
        keyMap.put("𝟐", -14);
        keyMap.put("𝟑", -13);
        keyMap.put("𝟒", -12);
        keyMap.put("𝟓", -11);
        keyMap.put("𝟔", -10);
        keyMap.put("𝟕", -9);
        keyMap.put("𝟴", -8);
        keyMap.put("𝟵", -7);
        keyMap.put("𝟎", -6);
        keyMap.put("𝑸", -5);
        keyMap.put("𝑾", -4);
        keyMap.put("𝑬", -3);
        keyMap.put("𝓡", -2);
        keyMap.put("𝑻", -1);
        keyMap.put("1", 1);
        keyMap.put("!", 2);
        keyMap.put("2", 3);
        keyMap.put("@", 4);
        keyMap.put("3", 5);
        keyMap.put("4", 6);
        keyMap.put("$", 7);
        keyMap.put("5", 8);
        keyMap.put("%", 9);
        keyMap.put("6", 10);
        keyMap.put("^", 11);
        keyMap.put("7", 12);
        keyMap.put("8", 13);
        keyMap.put("*", 14);
        keyMap.put("9", 15);
        keyMap.put("(", 16);
        keyMap.put("0", 17);
        keyMap.put("q", 18);
        keyMap.put("Q", 19);
        keyMap.put("w", 20);
        keyMap.put("W", 21);
        keyMap.put("e", 22);
        keyMap.put("E", 23);
        keyMap.put("r", 24);
        keyMap.put("t", 25);
        keyMap.put("T", 26);
        keyMap.put("y", 27);
        keyMap.put("Y", 28);
        keyMap.put("u", 29);
        keyMap.put("i", 30);
        keyMap.put("I", 31);
        keyMap.put("o", 32);
        keyMap.put("O", 33);
        keyMap.put("p", 34);
        keyMap.put("P", 35);
        keyMap.put("a", 36);
        keyMap.put("s", 37);
        keyMap.put("S", 38);
        keyMap.put("d", 39);
        keyMap.put("D", 40);
        keyMap.put("f", 41);
        keyMap.put("g", 42);
        keyMap.put("G", 43);
        keyMap.put("h", 44);
        keyMap.put("H", 45);
        keyMap.put("j", 46);
        keyMap.put("J", 47);
        keyMap.put("k", 48);
        keyMap.put("l", 49);
        keyMap.put("L", 50);
        keyMap.put("z", 51);
        keyMap.put("Z", 52);
        keyMap.put("x", 53);
        keyMap.put("c", 54);
        keyMap.put("C", 55);
        keyMap.put("v", 56);
        keyMap.put("V", 57);
        keyMap.put("b", 58);
        keyMap.put("B", 59);
        keyMap.put("n", 60);
        keyMap.put("m", 61);
        keyMap.put("𝒀", 62);
        keyMap.put("𝑼", 63);
        keyMap.put("𝑰", 64);
        keyMap.put("𝑶", 65);
        keyMap.put("𝑷", 66);
        keyMap.put("𝘼", 67);
        keyMap.put("𝑺", 68);
        keyMap.put("𝑫", 69);
        keyMap.put("𝑭", 70);
        keyMap.put("𝑮", 71);
        keyMap.put("𝑯", 72);
        keyMap.put("𝑱", 73);

        // for undigify, above but in reverse
        keyMap.put(-15, "𝟏");
        keyMap.put(-14, "𝟐");
        keyMap.put(-13, "𝟑");
        keyMap.put(-12, "𝟒");
        keyMap.put(-11, "𝟓");
        keyMap.put(-10, "𝟔");
        keyMap.put(-9, "𝟕");
        keyMap.put(-8, "𝟴");
        keyMap.put(-7, "𝟵");
        keyMap.put(-6, "𝟎");
        keyMap.put(-5, "𝑸");
        keyMap.put(-4, "𝑾");
        keyMap.put(-3, "𝑬");
        keyMap.put(-2, "𝓡");
        keyMap.put(-1, "𝑻");
        keyMap.put(1, "1");
        keyMap.put(2, "!");
        keyMap.put(3, "2");
        keyMap.put(4, "@");
        keyMap.put(5, "3");
        keyMap.put(6, "4");
        keyMap.put(7, "$");
        keyMap.put(8, "5");
        keyMap.put(9, "%");
        keyMap.put(10, "6");
        keyMap.put(11, "^");
        keyMap.put(12, "7");
        keyMap.put(13, "8");
        keyMap.put(14, "*");
        keyMap.put(15, "9");
        keyMap.put(16, "(");
        keyMap.put(17, "0");
        keyMap.put(18, "q");
        keyMap.put(19, "Q");
        keyMap.put(20, "w");
        keyMap.put(21, "W");
        keyMap.put(22, "e");
        keyMap.put(23, "E");
        keyMap.put(24, "r");
        keyMap.put(25, "t");
        keyMap.put(26, "T");
        keyMap.put(27, "y");
        keyMap.put(28, "Y");
        keyMap.put(29, "u");
        keyMap.put(30, "i");
        keyMap.put(31, "I");
        keyMap.put(32, "o");
        keyMap.put(33, "O");
        keyMap.put(34, "p");
        keyMap.put(35, "P");
        keyMap.put(36, "a");
        keyMap.put(37, "s");
        keyMap.put(38, "S");
        keyMap.put(39, "d");
        keyMap.put(40, "D");
        keyMap.put(41, "f");
        keyMap.put(42, "g");
        keyMap.put(43, "G");
        keyMap.put(44, "h");
        keyMap.put(45, "H");
        keyMap.put(46, "j");
        keyMap.put(47, "J");
        keyMap.put(48, "k");
        keyMap.put(49, "l");
        keyMap.put(50, "L");
        keyMap.put(51, "z");
        keyMap.put(52, "Z");
        keyMap.put(53, "x");
        keyMap.put(54, "c");
        keyMap.put(55, "C");
        keyMap.put(56, "v");
        keyMap.put(57, "V");
        keyMap.put(58, "b");
        keyMap.put(59, "B");
        keyMap.put(60, "n");
        keyMap.put(61, "m");
        keyMap.put(62, "𝒀");
        keyMap.put(63, "𝑼");
        keyMap.put(64, "𝑰");
        keyMap.put(65, "𝑶");
        keyMap.put(66, "𝑷");
        keyMap.put(67, "𝘼");
        keyMap.put(68, "𝑺");
        keyMap.put(69, "𝑫");
        keyMap.put(70, "𝑭");
        keyMap.put(71, "𝑮");
        keyMap.put(72, "𝑯");
        keyMap.put(73, "𝑱");
    }

    private Integer digify(String key) {
        return (Integer) keyMap.getOrDefault(key, -1);
    }

    private String undigify(Integer digit) {
        if (digit < -15 || digit > 73) {
            return "";
        } else {
            return (String) keyMap.getOrDefault(digit, "?");
        }
    }

    private String transposeNotes(String str, int transpose) {
        String newStr = "";
        String cNotes = "";
        String note = "";
        boolean chord = false;

        str = str.replaceAll("\\?", "");
        for (int i = 0; i < str.length(); i++) {
            note = String.valueOf(str.charAt(i));
            if (note.equals(" ") || note.equals("") || note.equals("\n") || note.equals("-") || note.equals("|")) {
                newStr = newStr.concat(note);
            }
            else if (note.equals("]")) {
                if (cNotes.length() < 2) {
                    newStr = newStr.concat(cNotes); // TODO: see if needed
                    chord = false;
                    cNotes = "";
                }
                else {
                    newStr += "[" + cNotes + note;
                    chord = false;
                    cNotes = "";
                }
            }
            else if (note.equals("[") || chord) {
                if (note.equals("[")) {
                    chord = true;
                }
                else {
                    cNotes += undigify(digify(note) + transpose);
                }
            }
            else {
                newStr += undigify(digify(note) + transpose);
            }
        }

        newStr = newStr.replaceAll("%[%]", "");
        newStr = newStr.replaceAll("\\s+", " ");
        newStr = newStr.replaceAll("\n ", "\n");
        return newStr;
    }

    private Integer easyNotes(String str) {
        Integer iter = 0;

        String character;
        for (int i = 0; i < str.length(); i++) {
            character = String.valueOf(str.charAt(i));
            if (character.matches("\\p{Lower}") || character.matches("\\d")) {
                iter++;
            }
        }

        return iter;
    }

    public Integer autoTranspose() {
        Integer[] transposes = {
            easyNotes(transposeNotes(str, -1)),
            easyNotes(transposeNotes(str, -2)),
            easyNotes(transposeNotes(str, -3)),
            easyNotes(transposeNotes(str, -4)),
            easyNotes(transposeNotes(str, -5)),
            easyNotes(transposeNotes(str, -6)),
            easyNotes(transposeNotes(str, -7)),
            easyNotes(transposeNotes(str, 0)),
            easyNotes(transposeNotes(str, 1)),
            easyNotes(transposeNotes(str, 2)),
            easyNotes(transposeNotes(str, 3)),
            easyNotes(transposeNotes(str, 4)),
            easyNotes(transposeNotes(str, 5)),
            easyNotes(transposeNotes(str, 6)),
            easyNotes(transposeNotes(str, 7)),
        };

        Integer best = 0;
        Integer bestPos = 0;
        for (int i = 0; i < transposes.length; i++) {
            int v = transposes[i];
            if (v > best) {
                best = v;
                bestPos = i;
            }
        }

        bestPos++;

        if (bestPos == 8) {
            System.out.println("Transpose 0 \n");
        } else if (bestPos < 8) {
            System.out.println("Transpose +" + bestPos + "\n");
        } else if (bestPos > 8) {
            System.out.println("Transpose -" + (bestPos - 8) + "\n");
        }

        return bestPos == 8 ? 0 : bestPos < 8 ? -bestPos : bestPos - 8;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
