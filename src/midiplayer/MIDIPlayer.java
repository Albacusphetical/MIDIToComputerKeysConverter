/*
 * Decompiled with CFR 0.150.
 */
package midiplayer;


import midiparser.KeyConverter;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

public class MIDIPlayer {
    private int channel = 0;
    private int volume = 80;
    private int duration = 200;
    private int currentInstrumentPatchNum = -1;
    private Sequence sequence = null;
    private Sequencer sequencer = null;
    private Synthesizer synth;
    private KeyConverter keyConverter = new KeyConverter();
    private String textToPlay = null;
    private ControllerEventListener controllerEventListener = null;
    private MetaEventListener mel = null;
    private Receiver receiver = null;
    private static final int NOTE_ON = 144;

    public MIDIPlayer(File file) {
        try {
            Sequencer sequencer;
            this.sequence = MidiSystem.getSequence(file);
            this.sequencer = sequencer = MidiSystem.getSequencer(false);
            this.sequencer.open();
            this.receiver = MidiSystem.getReceiver();
            this.sequencer.getTransmitter().setReceiver(this.receiver);
            this.sequencer.setSequence(this.sequence);
            System.out.println(MidiSystem.getMidiDeviceInfo());
            MidiDevice.Info[] info = MidiSystem.getMidiDeviceInfo();
            for (int i = 0; i < info.length; ++i) {
                System.out.println(info[i].getName());
            }
        }
        catch (InvalidMidiDataException e1) {
            e1.printStackTrace();
        }
        catch (IOException | MidiUnavailableException e1) {
            e1.printStackTrace();
        }
    }

    public void playKeys(int[] keys) {
        try {
            try {
                this.synth = MidiSystem.getSynthesizer();
                this.synth.open();
                MidiChannel[] channels = this.synth.getChannels();
                int channel = 0;
                channels[0].programChange(0);
                channels[channel].noteOn(60, this.volume);
                Thread.sleep(this.duration);
                channels[channel].noteOff(60);
            }
            catch (MidiUnavailableException e1) {
                e1.printStackTrace();
                this.synth.close();
            }
            catch (Exception e) {
                e.printStackTrace();
                this.synth.close();
            }
        }
        finally {
            this.synth.close();
        }
    }

    public void playMIDI() {
        if (!this.sequencer.isRunning()) {
            this.sequencer.stop();
            this.sequencer.start();
            this.setInstrument(this.currentInstrumentPatchNum);
            System.out.println("currentInstrumentPatchNum : " + this.currentInstrumentPatchNum);
        } else {
            this.setInstrument(this.currentInstrumentPatchNum);
        }
        ControllerEventListener controllerEventListener = new ControllerEventListener(){

            @Override
            public void controlChange(ShortMessage event) {
                System.out.println(event);
            }
        };
        this.sequencer.addMetaEventListener(new MetaEventListener(){

            @Override
            public void meta(MetaMessage event) {
                event.getType();
                if (event.getType() == 47) {
                    System.out.println("event 47");
                    MIDIPlayer.this.sequencer.stop();
                    MIDIPlayer.this.sequencer.setTickPosition(0L);
                }
                event.getType();
                if (event.getType() == 4) {
                    System.out.println("MetaEvent: Instrument Name Triggered");
                    System.out.println("Msg : " + event.toString());
                }
            }
        });
    }

    public void stopMIDI() {
        this.sequencer.stop();
        this.sequencer.setTickPosition(0L);
    }

    public void pauseMIDI() {
        this.sequencer.stop();
    }

    public void setVolume(double volume) {
        ShortMessage volMessage = new ShortMessage();
        for (int i = 0; i < 16; ++i) {
            try {
                volMessage.setMessage(176, i, 7, (int)volume);
                this.receiver.send(volMessage, -1L);
                continue;
            }
            catch (InvalidMidiDataException invalidMidiDataException) {
                // empty catch block
            }
        }
    }

    public void setInstrument(int progNum) {
        this.currentInstrumentPatchNum = progNum;
        if (progNum == -1) {
            try {
                long tmp = this.sequencer.getTickPosition();
                this.sequencer.setSequence(this.sequence);
                this.sequencer.setTickPosition(tmp);
                this.currentInstrumentPatchNum = progNum;
            }
            catch (InvalidMidiDataException e) {
                e.printStackTrace();
            }
            return;
        }
        if (this.sequencer.isRunning()) {
            ShortMessage progMsg = new ShortMessage();
            for (int i = 0; i < 16; ++i) {
                try {
                    for (Track track : this.sequence.getTracks()) {
                        progMsg.setMessage(192, i, progNum, -1);
                        track.add(new MidiEvent(progMsg, 0L));
                        this.receiver.send(progMsg, (this.sequencer.getTickPosition() + 1L) * 1000L + 100000L);
                        track.get(0);
                    }
                    continue;
                }
                catch (InvalidMidiDataException invalidMidiDataException) {
                    // empty catch block
                }
            }
            this.currentInstrumentPatchNum = progNum;
        } else {
            this.currentInstrumentPatchNum = progNum;
        }
    }

    public void setSpeed(float speed) {
        this.sequencer.setTempoFactor(speed);
    }

    public void play() {
    }

    public void printTickPos() {
        System.out.println("TickPosition is : " + this.sequencer.getTickPosition());
    }

    public boolean isRunning() {
        return this.sequencer.isRunning();
    }

    public void setTick(long tick) {
        this.sequencer.setTickPosition(tick);
    }

    public long getCurrentTick() {
        return this.sequencer.getTickPosition();
    }

    public long getTickLength() {
        return this.sequencer.getTickLength();
    }

    public Track[] getTracks() {
        return this.sequence.getTracks();
    }

    public void muteTrack(int trackNum) {
        this.sequencer.setTrackMute(trackNum, true);
    }

    public void unmuteTrack(int trackNum) {
        this.sequencer.setTrackMute(trackNum, false);
    }

    public void unmuteAllTracks() {
        for (int x = 0; x < this.sequence.getTracks().length; ++x) {
            this.sequencer.setTrackMute(x, false);
        }
    }

    public void printPatchList() {
    }
}

