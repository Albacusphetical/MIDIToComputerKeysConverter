/*
 * Decompiled with CFR 0.150.
 */
package midiparser;

import java.util.ArrayList;
import java.util.Scanner;

public class StringNotesParser {
    private String stringToParse = null;
    private Scanner sc = null;
    private ArrayList<String> eventTriggerNotes = new ArrayList();
    private int counter = 0;

    public StringNotesParser(String stringToParse) {
        this.stringToParse = stringToParse;
        this.sc = new Scanner(this.stringToParse).useDelimiter(" ");
        this.setupEventTriggerNotes();
    }

    private void setupEventTriggerNotes() {
        while (this.sc.hasNext()) {
            this.eventTriggerNotes.add(this.sc.next());
        }
    }

    public String getNextNotes() {
        return this.sc.next();
    }

    public int getOffSet() {
        return 0;
    }

    public int getLength() {
        return 0;
    }
}

