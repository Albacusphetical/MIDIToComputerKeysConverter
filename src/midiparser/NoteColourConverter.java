/*
 * Decompiled with CFR 0.150.
 */
package midiparser;

import midiparser.NoteValue;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class NoteColourConverter
extends NoteValue {
    StyledDocument sd = null;
    ArrayList<Long> eventTriggerTime = null;
    int quarterNote = 0;

    public NoteColourConverter(StyledDocument sd, ArrayList<Long> eventTriggerTime, int quarterNote) {
        this.sd = sd;
        this.eventTriggerTime = eventTriggerTime;
        this.quarterNote = quarterNote;
        this.setupNotesByQuarterNote(quarterNote);
    }

    public StyledDocument ColouriseNotes() {
        System.out.println();
        System.out.println("Note Values: ");
        int elementIndex = 0;
        String space = " ";
        String fullText = null;
        try {
            fullText = this.sd.getText(0, this.sd.getLength());
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
        boolean iterator = false;
        long nextEventTick = 0L;
        long currentTick = 0L;
        int length = 0;
        int currentPosition = 0;
        int endPosition = 0;
        int lastCurrentPosition = 0;
        int lastLength = 0;
        StyleContext cont = StyleContext.getDefaultStyleContext();
        AttributeSet grey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.LIGHT_GRAY);
        AttributeSet darkGreen = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 100, 0));
        AttributeSet green = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 204, 0));
        AttributeSet lightGreen = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(51, 255, 51));
        AttributeSet lime = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(128, 255, 0));
        AttributeSet darkYellow = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(204, 204, 0));
        AttributeSet orange = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 128, 0));
        AttributeSet lightRed = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 151, 151));
        AttributeSet red = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 51, 51));
        AttributeSet darkRed = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(153, 0, 0));
        for (long currentTrigger : this.eventTriggerTime) {
            elementIndex = this.eventTriggerTime.indexOf(currentTrigger);
            if (elementIndex + 1 > this.eventTriggerTime.size() - 1) continue;
            long noteValue = this.eventTriggerTime.get(elementIndex + 1) - currentTrigger;
            endPosition = fullText.indexOf(space, currentPosition);
            length = endPosition - currentPosition;
//            if (currentPosition > endPosition) {
//                break;
//            }
            this.sd.setCharacterAttributes(currentPosition, length, red, false);
            if (noteValue > this.octupleNote) {
                this.sd.setCharacterAttributes(currentPosition, length, darkGreen, false);
            }
            if (noteValue <= this.octupleNote && (double)noteValue >= (double)this.octupleNote * 0.75) {
                this.sd.setCharacterAttributes(currentPosition, length, darkGreen, false);
            }
            if ((double)noteValue <= (double)this.quadrupleNote * 1.25 && (double)noteValue >= (double)this.quadrupleNote * 0.75) {
                this.sd.setCharacterAttributes(currentPosition, length, green, false);
            }
            if ((double)noteValue <= (double)this.wholeNote * 1.25 && (double)noteValue >= (double)this.wholeNote * 0.75) {
                this.sd.setCharacterAttributes(currentPosition, length, lightGreen, false);
            }
            if ((double)noteValue <= (double)this.halfNote * 1.25 && (double)noteValue >= (double)this.halfNote * 0.75) {
                this.sd.setCharacterAttributes(currentPosition, length, lime, false);
            }
            if ((double)noteValue <= (double)this.quarterNote * 1.25 && (double)noteValue >= (double)this.quarterNote * 0.75) {
                this.sd.setCharacterAttributes(currentPosition, length, darkYellow, false);
            }
            if ((double)noteValue <= (double)this.eighthNote * 1.25 && (double)noteValue >= (double)this.eighthNote * 0.75) {
                this.sd.setCharacterAttributes(currentPosition, length, orange, false);
            }
            if ((double)noteValue <= (double)this.sixteenthNote * 1.25 && (double)noteValue >= (double)this.sixteenthNote * 0.75) {
                this.sd.setCharacterAttributes(currentPosition, length, lightRed, false);
            }
            if ((double)noteValue <= (double)this.thirtySecondNote * 1.25 && (double)noteValue >= (double)this.thirtySecondNote * 0.75) {
                this.sd.setCharacterAttributes(currentPosition, length, red, false);
            }
            if ((double)noteValue <= (double)this.sixtyFourthNote * 1.25 && (double)noteValue >= (double)this.sixtyFourthNote * 0.75) {
                this.sd.setCharacterAttributes(currentPosition, length, darkRed, false);
            }
            if ((double)noteValue <= (double)this.hundredTwentyEighthNote * 1.25 && (double)noteValue >= (double)this.hundredTwentyEighthNote * 0.75) {
                this.sd.setCharacterAttributes(currentPosition, length, darkRed, false);
            }
            if ((double)noteValue <= (double)this.twoHundredFiftySixthNote * 1.25 && (double)noteValue >= (double)this.twoHundredFiftySixthNote * 0.75) {
                this.sd.setCharacterAttributes(currentPosition, length, darkRed, false);
            }
            if (noteValue <= this.twoHundredFiftySixthNote) {
                this.sd.setCharacterAttributes(currentPosition, length, darkRed, false);
            }
            lastLength = length;
            lastCurrentPosition = currentPosition;
            if (endPosition + 1 != fullText.length()) {
                while (fullText.charAt(endPosition + 1) == ' ') {
                    System.out.println("Yes, space is there");
                    ++endPosition;
                }
            }
            currentPosition = ++endPosition;
            length = 0;
        }
        System.out.println("quarterNote : " + this.quarterNote);
        return this.sd;
    }
}

