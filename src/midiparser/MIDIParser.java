/*
 * Decompiled with CFR 0.150.
 */
package midiparser;

import com.sun.source.tree.Tree;
import midigui.JFrameMIDIPianoSheetCreator;
import midiparser.KeyConverter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MIDIParser {
    private static final int NOTE_ON = 144;
    private static final int NOTE_OFF = 128;
    private static final String outOfRangeOctaveDivider = ".";
    public static final int TIME_SIGNATURE = 88;
    private String completeNotes = "";
    private ArrayList<SheetSection> sheetSections = new ArrayList<>();
    private Integer parsingLineNum = 0;
    private HashMap<Integer, TreeMap<Long, ArrayList<Integer>>> midiNotesPerLine = new HashMap<>();
    private Sequencer sequencer = null;
    private Sequence sequence = null;
    private static KeyConverter keyConverter = new KeyConverter();
    private String fileName = null;
    private String strTimeSignatureInfo = null;
    private ArrayList<Long> eventTriggerTime = new ArrayList();
    private ArrayList<String> eventTriggerNote = new ArrayList();
    private TreeMap<Long, ArrayList<Integer>> tmMidiParsedData = null;
    private TreeMap<Integer, ArrayList<Long>> tmTracksEvents = null;
    private long checkTick = 0L;
    private long midiTickLength = 0L;
    private int beatsPerMeasure = 0;
    private int noteValueInMeasure = 0;
    private float measureLength = 0.0f;
    private int measureMultiplier = 1;
    private float currentMeasure = 0.0f;
    private String upperKeys = "";
    private String lowerKeys = "";
    private String numericKeys = "";
    private String outOfRangeLowOctaveKeys = "";
    private String outOfRangeHighOctaveKeys = "";
    private String otherKeys = "";

    public MIDIParser(File file) {
        SheetSection.idCount = 0;
        midiNotesPerLine.put(0, new TreeMap<Long, ArrayList<Integer>>());
        try {
            this.fileName = file.getName().substring(0, file.getName().length() - 4);
            this.sequence = MidiSystem.getSequence(file);
            this.sequencer = MidiSystem.getSequencer();
            this.sequencer.setSequence(this.sequence);
            this.midiTickLength = this.sequence.getTickLength();
        }
        catch (InvalidMidiDataException e) {
            System.out.println();
            System.out.println("MidiParser: InvalidMidiDataException");
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Unable to load " + file.getName() + " (Corrupt MIDI Header?)", "Invalid MIDI File", 0);
        }
        catch (IOException e) {
            System.out.println();
            System.out.println("MidiParser: IOException");
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Unable to find file, please try again.", "File Not Found", 0);
        }
        catch (MidiUnavailableException e) {
            System.out.println();
            System.out.println("MidiParser: MidiUnavailableException");
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Unable to find file, please try again.", "File Not Found", 0);
        }
    }

    @Deprecated
    public String parseMIDI() {
        String currentKeyNotes = "";
        boolean firstParse = true;
        ArrayList<Integer> currentMidiNumbers = new ArrayList<Integer>();
        int trackNumber = 0;
        long checkNextTick = 0L;
        boolean firstRun = true;
        System.out.println();
        System.out.println("Number of Tracks: " + this.sequence.getTracks().length);
        System.out.println();
        for (long checkTick = 0L; checkTick <= this.sequence.getTickLength(); ++checkTick) {
            trackNumber = 0;
            block1: for (Track track : this.sequence.getTracks()) {
                ++trackNumber;
                for (int i = 0; i < track.size(); ++i) {
                    ShortMessage sm;
                    MidiEvent event = track.get(i);
                    MidiMessage message = event.getMessage();
                    if (checkTick != event.getTick()) continue;
                    if (message instanceof ShortMessage && (sm = (ShortMessage)message).getCommand() == 144) {
                        int key = sm.getData1();
                        int velocity = sm.getData2();
                        if (velocity != 0) {
                            if (firstRun) {
                                if (this.strTimeSignatureInfo != null) {
                                    System.out.println();
                                    System.out.println("Parsing:");
                                }
                                this.eventTriggerTime.add(event.getTick());
                                firstRun = false;
                            } else {
                                int size = this.eventTriggerTime.size() - 1;
                                if (this.eventTriggerTime.get(size).longValue() != event.getTick()) {
                                    this.eventTriggerTime.add(event.getTick());
                                }
                            }
                            if (!currentMidiNumbers.contains(key)) {
                                currentMidiNumbers.add(key);
                            }
                        }
                    }
                    if (event.getTick() > checkTick) continue block1;
                }
            }
            if (currentMidiNumbers.size() == 1) {
                String currentKey = keyConverter.convertMidiToKeyboard((Integer)currentMidiNumbers.get(0));
                currentKeyNotes = String.valueOf(currentKeyNotes) + currentKey;
            }
            if (currentMidiNumbers.size() > 1) {
                currentKeyNotes = this.convertMidiNumToKeys(currentMidiNumbers, 0);
            }
            if (currentKeyNotes.isEmpty()) continue;
            System.out.print(String.valueOf(currentKeyNotes) + " ");
            boolean hasNewLine = false;
            if ((float)checkTick + 1.0f > this.currentMeasure) {
                this.currentMeasure += this.measureLength;
                this.completeNotes = String.valueOf(this.completeNotes) + " \r\n";
                hasNewLine = true;
            }
            if (currentKeyNotes.length() > 1) {
                if (firstParse || hasNewLine) {
                    this.completeNotes = String.valueOf(this.completeNotes) + "[" + currentKeyNotes + "]";
                    firstParse = false;
                    hasNewLine = false;
                } else {
                    this.completeNotes = String.valueOf(this.completeNotes) + " [" + currentKeyNotes + "]";
                }
            } else if (firstParse || hasNewLine) {
                this.completeNotes = String.valueOf(this.completeNotes) + currentKeyNotes;
                firstParse = false;
                hasNewLine = false;
            } else {
                this.completeNotes = String.valueOf(this.completeNotes) + " " + currentKeyNotes;
            }
            currentKeyNotes = "";
            currentMidiNumbers.clear();
            this.otherKeys = "";
            this.numericKeys = "";
            this.lowerKeys = "";
            this.upperKeys = "";
            this.outOfRangeLowOctaveKeys = "";
            this.outOfRangeHighOctaveKeys = "";
        }
        currentMidiNumbers = null;
        System.out.println();
        System.out.println("Final Output: ");
        System.out.println("Hidden for now, refer to textPane");
        System.out.println();
        System.out.println("Parsing Complete");
        this.setTimeSignature(false);
        System.out.println();
        System.out.println("eventTriggerTime: " + this.eventTriggerTime.size());
        for (int i = 0; i < this.eventTriggerTime.size() - 1; ++i) {
            System.out.print(String.valueOf(this.eventTriggerTime.get(i).toString()) + " ");
        }
        return this.completeNotes;
    }

    public void retrieveMidiData(int[] selectedTracks) {
        System.out.println();
        System.out.println("retrieveMidiData()");
        this.getTimeSignature();
        this.setTimeSignature(false);
        this.tmMidiParsedData = new TreeMap();
        System.out.println("Selected Track Indexesss : " + Arrays.toString(selectedTracks));
        int trackNumber = 0;
        for (Track track : this.sequence.getTracks()) {
            ++trackNumber;
            if (selectedTracks != null) {
                int[] arrn = selectedTracks;
                int n = selectedTracks.length;
                for (int i = 0; i < n; ++i) {
                    int num = arrn[i];
                    if (num != trackNumber) continue;
                    for (int i2 = 0; i2 < track.size(); ++i2) {
                        MidiEvent event = track.get(i2);
                        MidiMessage message = event.getMessage();
                        if (!(message instanceof ShortMessage)) continue;
                        ShortMessage sm = (ShortMessage)message;
                        if (sm.getCommand() == 144) {
                            int key = sm.getData1();
                            int octave = key / 12 - 1;
                            int note = key % 12;
                            int velocity = sm.getData2();
                            if (velocity != 0) {
                                if (!this.tmMidiParsedData.containsKey(event.getTick())) {
                                    this.tmMidiParsedData.put(event.getTick(), new ArrayList());
                                    this.tmMidiParsedData.get(event.getTick()).add(key);
                                } else if (!this.tmMidiParsedData.get(event.getTick()).contains(key)) {
                                    this.tmMidiParsedData.get(event.getTick()).add(key);
                                }
                            }
                        }
                        if (sm.getCommand() != 192) continue;
                        int programNum = sm.getData1();
                        System.out.println("patchNum : " + programNum);
                        System.out.println("Channel : " + sm.getChannel());
                    }
                }
                continue;
            }
            for (int i = 0; i < track.size(); ++i) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (!(message instanceof ShortMessage)) continue;
                ShortMessage sm = (ShortMessage)message;
                if (sm.getCommand() == 144) {
                    int key = sm.getData1();
                    int octave = key / 12 - 1;
                    int note = key % 12;
                    int velocity = sm.getData2();
                    if (velocity != 0) {
                        if (!this.tmMidiParsedData.containsKey(event.getTick())) {
                            this.tmMidiParsedData.put(event.getTick(), new ArrayList());
                            this.tmMidiParsedData.get(event.getTick()).add(key);
                        } else if (!this.tmMidiParsedData.get(event.getTick()).contains(key)) {
                            this.tmMidiParsedData.get(event.getTick()).add(key);
                        }
                    }
                }
                if (sm.getCommand() != 192) continue;
                int programNum = sm.getData1();
                System.out.println("patchNum : " + programNum);
                System.out.println("Channel : " + sm.getChannel());
            }
        }

        this.parseData(this.tmMidiParsedData);
        System.out.println();
    }

    public void reparse(TreeMap<Long, ArrayList<Integer>> rawData, Integer transpose) {
        // reset
        parsingLineNum = 0;
        this.setTimeSignature(true);
        this.currentMeasure = this.measureLength;
        this.eventTriggerNote.clear();
        this.eventTriggerTime.clear();
        this.completeNotes = "";

        String currentNotes = null;
        for (Map.Entry<Long, ArrayList<Integer>> entry : rawData.entrySet()) {
            currentNotes = this.convertMidiNumToKeys(entry.getValue(), transpose);
            currentNotes = this.formatBrackets(currentNotes);
            currentNotes = this.formatTime(entry.getKey(), currentNotes);
            this.completeNotes = String.valueOf(this.completeNotes) + currentNotes;
            this.otherKeys = "";
            this.numericKeys = "";
            this.lowerKeys = "";
            this.upperKeys = "";
            this.outOfRangeLowOctaveKeys = "";
            this.outOfRangeHighOctaveKeys = "";
            this.eventTriggerTime.add(entry.getKey());
            this.eventTriggerNote.add(currentNotes);
        }

        if (JFrameMIDIPianoSheetCreator.dialogTrackImport.isColourise()) {
            JFrameMIDIPianoSheetCreator.tpKeyEditor.setStyledDocument(JFrameMIDIPianoSheetCreator.noteColourConverter.ColouriseNotes());
        }
    }

    public TreeMap<Long, ArrayList<Integer>> getTmMidiParsedData() {
        return this.tmMidiParsedData;
    }

    private void addKey(String keyToAdd) {
        // special key
        if (keyToAdd.length() > 1) {
            // special unicode character, out of range note
            if (keyConverter.isSpecialLowOctaveNote(keyToAdd)) {
                this.outOfRangeLowOctaveKeys = this.outOfRangeLowOctaveKeys + keyToAdd;
            }
            else if (keyConverter.isSpecialHighOctaveNote(keyToAdd)) {
                this.outOfRangeHighOctaveKeys = this.outOfRangeHighOctaveKeys + keyToAdd;
            }

            return;
        }

        // normal key
        char key = keyToAdd.charAt(0);
        if (Character.isUpperCase(key)) {
            this.upperKeys = String.valueOf(this.upperKeys) + keyToAdd;
        } else if (Character.isLowerCase(key)) {
            this.lowerKeys = String.valueOf(this.lowerKeys) + keyToAdd;
        } else if (Character.isDigit(key)) {
            this.numericKeys = String.valueOf(this.numericKeys) + keyToAdd;
        } else {
            this.otherKeys = String.valueOf(this.otherKeys) + keyToAdd;
        }
    }

    private String addBarForSpecialNotes(String specialLowOctave, String specialHighOctave) {
        return specialLowOctave.length() > 0 || specialHighOctave.length() > 0 ? "|" : "";
    }

    private String convertMidiNumToKeys(ArrayList<Integer> MIDINumbers, Integer transpose) {
        String sortedKeys = "";
        if (MIDINumbers.size() > 1) {
            Collections.sort(MIDINumbers);
            for (int x = 0; x <= MIDINumbers.size() - 1; ++x) {
                String currentKey = keyConverter.convertMidiToKeyboard(MIDINumbers.get(x) + transpose);
                char currentKeyChar = currentKey.charAt(0);

                this.addKey(currentKey);
                if (x != MIDINumbers.size() - 1) continue;
                sortedKeys = Character.isUpperCase(currentKeyChar) || !Character.isLetterOrDigit(currentKeyChar) ? this.outOfRangeLowOctaveKeys + addBarForSpecialNotes(this.outOfRangeLowOctaveKeys, this.outOfRangeHighOctaveKeys) + String.valueOf(this.numericKeys) + this.lowerKeys + this.otherKeys + this.upperKeys + addBarForSpecialNotes(this.outOfRangeLowOctaveKeys, this.outOfRangeHighOctaveKeys) + this.outOfRangeHighOctaveKeys: (this.outOfRangeLowOctaveKeys + addBarForSpecialNotes(this.outOfRangeLowOctaveKeys, this.outOfRangeHighOctaveKeys) + String.valueOf(this.otherKeys) + this.upperKeys + this.numericKeys + this.lowerKeys + addBarForSpecialNotes(this.outOfRangeLowOctaveKeys, this.outOfRangeHighOctaveKeys) + this.outOfRangeHighOctaveKeys);
            }
        } else {
            String currentKey = keyConverter.convertMidiToKeyboard(MIDINumbers.get(0) + transpose);
            sortedKeys = String.valueOf(currentKey);
        }
        return sortedKeys;
    }

    private String formatBrackets(String currentNotes) {
        currentNotes = currentNotes.length() > 1 && !KeyConverter.specialKeys.contains(currentNotes) ? "[" + currentNotes + "] " : keyConverter.isSpecialLowOctaveNote(currentNotes) || keyConverter.isSpecialHighOctaveNote(currentNotes) ? outOfRangeOctaveDivider + currentNotes + " " : String.valueOf(currentNotes) + " ";
        return currentNotes;
    }

    private String formatTime(long time, String currentNotes) {
        // places new line *before a new measure* if we are past the previous measure
        if ((float)time >= this.currentMeasure) {
            this.currentMeasure += this.measureLength;
            currentNotes = "\r\n" + currentNotes;
        }

        return currentNotes;
    }

    private void parseData(TreeMap<Long, ArrayList<Integer>> rawData) {
        // initially, the first section is the entire sheet
        sheetSections.add(new SheetSection(rawData));

        parsingLineNum = 0;

        String currentNotes = null;
        for (Map.Entry<Long, ArrayList<Integer>> entry : rawData.entrySet()) {
            if ((float)entry.getKey() >= this.currentMeasure) {
                parsingLineNum++;
                midiNotesPerLine.put(parsingLineNum, new TreeMap<Long, ArrayList<Integer>>());
            }

            midiNotesPerLine.get(parsingLineNum).put(entry.getKey(), entry.getValue());

            currentNotes = this.convertMidiNumToKeys(entry.getValue(), 0);
            currentNotes = this.formatBrackets(currentNotes);
            currentNotes = this.formatTime(entry.getKey(), currentNotes);
            this.completeNotes = String.valueOf(this.completeNotes) + currentNotes;
            this.otherKeys = "";
            this.numericKeys = "";
            this.lowerKeys = "";
            this.upperKeys = "";
            this.outOfRangeLowOctaveKeys = "";
            this.outOfRangeHighOctaveKeys = "";
            this.eventTriggerTime.add(entry.getKey());
            this.eventTriggerNote.add(currentNotes);
        }

        System.out.println();
        System.out.println("eventTriggerTime: " + this.eventTriggerTime.size());
        for (int i = 0; i < this.eventTriggerTime.size() - 1; ++i) {
            System.out.print(String.valueOf(this.eventTriggerTime.get(i).toString()) + " ");
        }
    }

    private void setTimeSignature(boolean isCustom) {
        if (this.strTimeSignatureInfo != null) {
            System.out.println("MIDI MetaMessage Time Signature detected: ");
            System.out.println(this.strTimeSignatureInfo);
        } else if (!isCustom) {
            System.out.println("MIDI has no Time Signature MetaMessage!! Defaulting to 4/4");
            this.beatsPerMeasure = 4;
            this.noteValueInMeasure = 4;
        }
        if (this.sequence.getDivisionType() == 0.0f) {
            System.out.println("MIDI Division Type is PPQ (0)");
        } else {
            System.out.println("MIDI Division Type is SMPTE: " + this.sequence.getDivisionType());
        }
        System.out.println();
        System.out.println("TickLength: " + this.sequencer.getTickLength());
        System.out.println("MicrosecondLength: " + this.sequencer.getMicrosecondLength());
        System.out.println("TempoFactor: " + this.sequencer.getTempoFactor());
        System.out.println("TempoInBPM: " + this.sequencer.getTempoInBPM());
        System.out.println("TempoInMPQ: " + this.sequencer.getTempoInMPQ());
        System.out.println();
        this.measureLength = 0.0f;
        this.currentMeasure = 0.0f;
        this.measureLength = this.sequence.getResolution() * this.beatsPerMeasure;
        this.measureLength = this.measureMultiplier == 0 ? (float)this.sequence.getTickLength() : (this.measureLength *= (float)this.measureMultiplier);
        this.currentMeasure += this.measureLength;
        System.out.println("One measure/bar has: " + this.measureLength + " Ticks");
    }

    private void getTimeSignature() {
        int trackNumber = 0;
        System.out.println();
        block0: for (Track track : this.sequence.getTracks()) {
            ++trackNumber;
            for (int i = 0; i < track.size(); ++i) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (!(message instanceof MetaMessage)) continue;
                MetaMessage metaMessage = (MetaMessage)message;
                byte[] abMessage = metaMessage.getMessage();
                byte[] abData = metaMessage.getData();
                int nDataLength = metaMessage.getLength();
                if (88 != metaMessage.getType()) continue;
                System.out.println("Track Number: " + trackNumber);
                this.strTimeSignatureInfo = "Time Signature: " + (abData[0] & 0xFF) + "/" + (1 << (abData[1] & 0xFF)) + ", MIDI clocks per metronome tick: " + (abData[2] & 0xFF) + ", 1/32 per 24 MIDI clocks: " + (abData[3] & 0xFF);
                System.out.println("strTimeSignatureInfo: " + this.strTimeSignatureInfo);
                this.beatsPerMeasure = abData[0];
                this.noteValueInMeasure = 1 << abData[1];
                break block0;
            }
        }
        System.out.println();
    }

    public void saveMidiCSV() {
        try {
            String dir = System.getProperty("user.dir");
            BufferedWriter newTextFile = new BufferedWriter(new FileWriter(String.valueOf(dir) + "/Data/" + this.fileName + ".csv"));
            BufferedWriter fw = new BufferedWriter(newTextFile);
            boolean firstRun = true;
            int trackNumber = 0;
            for (Track track : this.sequence.getTracks()) {
                ++trackNumber;
                for (int i = 0; i < track.size(); ++i) {
                    ShortMessage sm;
                    MidiEvent event = track.get(i);
                    MidiMessage message = event.getMessage();
                    if (!(message instanceof ShortMessage) || (sm = (ShortMessage)message).getCommand() != 144) continue;
                    int key = sm.getData1();
                    int octave = key / 12 - 1;
                    int note = key % 12;
                    int velocity = sm.getData2();
                    if (velocity == 0) continue;
                    if (firstRun) {
                        fw.write(String.valueOf(trackNumber) + "," + event.getTick() + "," + " Note_on_c," + sm.getChannel() + "," + key + "," + velocity);
                        firstRun = false;
                        continue;
                    }
                    fw.newLine();
                    fw.write(String.valueOf(trackNumber) + "," + event.getTick() + "," + " Note_on_c," + sm.getChannel() + "," + key + "," + velocity);
                }
            }
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getParsedData() {
        return this.completeNotes;
    }

    public ArrayList<Integer> getTotalTrackEventsNumber() {
        ArrayList<Integer> trackEvents = new ArrayList<Integer>();
        trackEvents.add(0);
        int trackNumber = 0;
        int totalEvents = 0;
        for (Track track : this.sequence.getTracks()) {
            System.out.println("trackNumber : " + ++trackNumber);
            for (int i = 0; i < track.size(); ++i) {
                ShortMessage sm;
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (!(message instanceof ShortMessage) || (sm = (ShortMessage)message).getCommand() != 144) continue;
                ++totalEvents;
            }
            trackEvents.add(totalEvents / 2);
            totalEvents = 0;
            System.out.println("trackEvents: " + trackEvents.get(trackNumber));
        }
        return trackEvents;
    }

    public TreeMap<Integer, ArrayList<Long>> retrieveTracksEvents() {
        this.tmTracksEvents = new TreeMap();
        int trackNumber = 0;
        for (Track track : this.sequence.getTracks()) {
            System.out.println("trackNumber : " + ++trackNumber);
            for (int i = 0; i < track.size(); ++i) {
                ShortMessage sm;
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (!(message instanceof ShortMessage) || (sm = (ShortMessage)message).getCommand() != 144) continue;
                if (!this.tmTracksEvents.containsKey(trackNumber)) {
                    this.tmTracksEvents.put(trackNumber, new ArrayList());
                    this.tmTracksEvents.get(trackNumber).add(event.getTick());
                    continue;
                }
                if (this.tmTracksEvents.get(trackNumber).contains(event.getTick())) continue;
                this.tmTracksEvents.get(trackNumber).add(event.getTick());
            }
        }
        return this.tmTracksEvents;
    }

    public int getPatchNumber(int trackNum) {
        System.out.println("TrackNum Value : " + trackNum);
        int programNum = -1;
        Track[] track = this.sequence.getTracks();
        for (int i = 0; i < track[trackNum].size(); ++i) {
            ShortMessage sm;
            MidiEvent event = track[trackNum].get(i);
            MidiMessage message = event.getMessage();
            if (!(message instanceof ShortMessage) || (sm = (ShortMessage)message).getCommand() != 192) continue;
            programNum = sm.getData1();
            break;
        }
        int length = track.length - 1;
        System.out.println("Track.Length = " + length);
        if (programNum == -1 && trackNum != 0) {
            for (int i = 0; i < track[trackNum].size(); ++i) {
                ShortMessage sm;
                MidiEvent event = track[trackNum].get(i);
                MidiMessage message = event.getMessage();
                if (!(message instanceof ShortMessage) || (sm = (ShortMessage)message).getCommand() != 192) continue;
                programNum = sm.getData1();
                break;
            }
        }
        return programNum;
    }

    public int getChannelNumber(int trackNum) {
        int channelNum = -1;
        Track[] track = this.sequence.getTracks();
        for (int i = 0; i < track[trackNum].size(); ++i) {
            ShortMessage sm;
            MidiEvent event = track[trackNum].get(i);
            MidiMessage message = event.getMessage();
            if (!(message instanceof ShortMessage) || (sm = (ShortMessage)message).getCommand() != 192) continue;
            channelNum = sm.getChannel();
            break;
        }
        return channelNum;
    }

    public ArrayList<Long> getEventTriggerTime() {
        return this.eventTriggerTime;
    }

    public long getNextTriggerTime(int elementValue) {
        return this.eventTriggerTime.get(elementValue);
    }

    public String getNextTriggerNote(int elementValue) {
        return this.eventTriggerNote.get(elementValue);
    }

    public int getNextTriggerTimeElement(long tick) {
        int returnValue = 0;
        for (long value : this.eventTriggerTime) {
            if (value <= tick) continue;
            returnValue = this.eventTriggerTime.indexOf(value);
            break;
        }
        if (returnValue != 0) {
            --returnValue;
        }
        return returnValue;
    }

    public long getTriggerTimeSize() {
        return this.eventTriggerTime.size();
    }

    public long getTickLength() {
        return this.sequence.getTickLength();
    }

    public void setMeasurePerLine(int measureMultiplier) {
        this.measureMultiplier = measureMultiplier;
//        this.getTimeSignature();
//        this.setTimeSignature(true);
    }

    public int getTracks() {
        return this.sequence.getTracks().length;
    }

    public int getQuarterNote() {
        return this.sequence.getResolution();
    }

    public int getBeatsPerMeasure() {
        return beatsPerMeasure;
    }

    public void setBeatsPerMeasure(int beatsPerMeasure) {
        this.beatsPerMeasure = beatsPerMeasure;
    }

    public HashMap<Integer, TreeMap<Long, ArrayList<Integer>>> getMidiNotesPerLine() {
        return midiNotesPerLine;
    }

    public TreeMap<Long, ArrayList<Integer>> getMidiNotesFromLines(int line1, int line2) {
        TreeMap<Long, ArrayList<Integer>> results = new TreeMap<>();
        for (int i = line1; i <= line2; i++) {
            results.putAll(midiNotesPerLine.get(i));
        }

        return results;
    }
}

