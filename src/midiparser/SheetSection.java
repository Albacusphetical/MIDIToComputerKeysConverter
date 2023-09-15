package midiparser;

import java.util.ArrayList;
import java.util.TreeMap;

public class SheetSection {
    public static Integer idCount = 0;

    Integer id;
    TreeMap<Long, ArrayList<Integer>> midiData;
//    String sectionNotes;

    public SheetSection(TreeMap<Long, ArrayList<Integer>> midiData) {//, String sectionNotes) {
        idCount++;
        id = idCount;

        this.midiData = midiData;
//        sectionNotes = sectionNotes;
    }
}
