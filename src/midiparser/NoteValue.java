/*
 * Decompiled with CFR 0.150.
 */
package midiparser;

import java.util.TreeMap;

public class NoteValue {
    private TreeMap<String, Integer> tmMidiParsedData = null;
    protected long octupleNote;
    protected long quadrupleNote;
    protected long wholeNote;
    protected long halfNote;
    protected long quarterNote;
    protected long eighthNote;
    protected long sixteenthNote;
    protected long thirtySecondNote;
    protected long sixtyFourthNote;
    protected long hundredTwentyEighthNote;
    protected long twoHundredFiftySixthNote;

    public void setupNotesByQuarterNote(int quarterNote) {
        this.quarterNote = quarterNote;
        this.octupleNote = quarterNote * 16;
        this.quadrupleNote = quarterNote * 8;
        this.wholeNote = quarterNote * 4;
        this.halfNote = quarterNote * 2;
        this.eighthNote = this.wholeNote / 8L;
        this.sixteenthNote = this.wholeNote / 16L;
        this.thirtySecondNote = this.wholeNote / 32L;
        this.sixtyFourthNote = this.wholeNote / 64L;
        this.hundredTwentyEighthNote = this.wholeNote / 128L;
        this.twoHundredFiftySixthNote = this.wholeNote / 256L;
    }

    public String determineNoteValue(long noteValue) {
        String noteStringValue = null;
        if (noteValue == this.octupleNote) {
            noteStringValue = "octupleNote";
        }
        if (noteValue == this.quadrupleNote) {
            noteStringValue = "quadrupleNote";
        }
        if (noteValue == this.wholeNote) {
            noteStringValue = "wholeNote";
        }
        if (noteValue == this.halfNote) {
            noteStringValue = "halfNote";
        }
        if (noteValue == this.quarterNote) {
            noteStringValue = "quarterNote";
        }
        if (noteValue == this.eighthNote) {
            noteStringValue = "eighthNote";
        }
        if (noteValue == this.sixteenthNote) {
            noteStringValue = "sixteenthNote";
        }
        if (noteValue == this.thirtySecondNote) {
            noteStringValue = "thirtySecondNote";
        }
        if (noteValue == this.sixtyFourthNote) {
            noteStringValue = "sixtyFourthNote";
        }
        if (noteValue == this.hundredTwentyEighthNote) {
            noteStringValue = "hundredTwentyEighthNote";
        }
        if (noteValue == this.twoHundredFiftySixthNote) {
            noteStringValue = "twoHundredFiftySixthNote";
        }
        return noteStringValue;
    }

    protected long getOctupleNote() {
        return this.octupleNote;
    }

    protected long getQuadrupleNote() {
        return this.quadrupleNote;
    }

    protected long getWholeNote() {
        return this.wholeNote;
    }

    protected long getHalfNote() {
        return this.halfNote;
    }

    protected long getQuarterNote() {
        return this.quarterNote;
    }

    protected long getEighthNote() {
        return this.eighthNote;
    }

    protected long getSixteenthNote() {
        return this.sixteenthNote;
    }

    protected long getThirtySecondNote() {
        return this.thirtySecondNote;
    }

    protected long getSixtyFourthNote() {
        return this.sixtyFourthNote;
    }

    protected long getHundredTwentyEighthNote() {
        return this.hundredTwentyEighthNote;
    }

    protected long getTwoHundredFiftySixthNote() {
        return this.twoHundredFiftySixthNote;
    }
}

