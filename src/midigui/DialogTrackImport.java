/*
 * Decompiled with CFR 0.150.
 */
package midigui;

import midiparser.MIDIParser;
import midiparser.PatchConverter;
import midiplayer.MIDIPlayer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import javax.sound.midi.Track;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSliderUI;

public class DialogTrackImport
extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JScrollPane spTracks;
    private JList list = null;
    private JLayeredPane lpPreviewSelectedTracks;
    private JSlider sliderTime;
    private JButton btnPlay;
    private JButton btnStop;
    private JSlider sliderVolume;
    private JLabel lblVolume;
    MIDIPlayer midiPlayer = null;
    private boolean isPlaying = false;
    Timer timer = null;
    DefaultListModel<String> listModel = null;
    private ListSelectionListener listSelectionListener = null;
    private static final PatchConverter pc = new PatchConverter();
    private DefaultListModel<String> listModelPlayIndicator = new DefaultListModel();
    public static final int OK_BUTTON = 1;
    public static final int CANCEL_BUTTON = 0;
    private int selectedButton;
    private JList<String> listTracks;
    private JLayeredPane layeredPane;
    private JRadioButton rdbtnNoMeasure;
    private JRadioButton rdbtnMeasureBar;
    private JFormattedTextField tfMeasure;
    private JLabel label;
    private JLabel label_1;
    private JSeparator separator_1;
    private JLabel lbltimeSignatureDetermines;
    private JRadioButton rdbtnAutoTimeSig;
    private JRadioButton rdbtnCustomTimeSig;
    private JTextField tfTop;
    private JTextField tfBottom;
    private JLabel label_3;
    private JLayeredPane lpTrackList;
    private MIDIParser midiParser = null;
    private JTabbedPane tabbedPane;
    private JPanel pnlImport;
    private JPanel pnlLineFormat;
    private JPanel pnlNoteValueFormat;
    private JList listNoteIndicator;
    private JPanel pnlNoteValueColour;
    private JCheckBox chckbxColourizeNoteValues;
    private JLayeredPane lpColour;
    private JCheckBox chckbxUSeAdvanced;
    private JLayeredPane layeredPane_2;
    private JLabel lblOnlySelectedTracks;
    private JLabel lblOctuplenote;
    private JLabel lblQuadruplenote;
    private JLabel lblWholenote;
    private JLabel lblHalfnote;
    private JLabel lblQuarternote;
    private JLabel lblEighthnote;
    private JLabel lblSixteenthnote;
    private JLabel lblThirtysecondnote;
    private JLabel lblSixtyfourthnote;
    private JLabel lblHundredtwentyeighthnote;
    private JLabel lblTwohundredfiftysixthnote;
    private JButton btnColorchooserTest;
    private JList listPlayIndicator;
    private JList list_1;
    private JLabel lblSelectMidiTracks;
    private JLabel lblEvents;
    private JCheckBox chckbxShowColourReference;

    public DialogTrackImport(JFrame parent, final File file) {
        this.setTitle("Advanced Import Options");
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setResizable(false);
        this.setLocationRelativeTo(parent);
        this.setBounds(100, 100, 625, 545);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("jdialog window closed event received");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("jdialog window closing event received");
                DialogTrackImport.this.midiPlayer.stopMIDI();
                if (DialogTrackImport.this.timer != null) {
                    DialogTrackImport.this.timer.stop();
                }
            }
        });
        this.getContentPane().setLayout(new BorderLayout());
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setDefaultCloseOperation(2);
        this.getContentPane().add((Component)this.contentPanel, "Center");
        this.contentPanel.setLayout(null);
        SwingWorker<Void, Void> classInitialiserWorker = new SwingWorker<Void, Void>(){

            @Override
            protected Void doInBackground() throws Exception {
                DialogTrackImport.this.midiParser = new MIDIParser(file);
                DialogTrackImport.this.midiParser.retrieveMidiData(null);
                DialogTrackImport.this.midiPlayer = new MIDIPlayer(file);
                DialogTrackImport.this.midiPlayer.printPatchList();
                return null;
            }

            @Override
            protected void done() {
                DialogTrackImport.this.continueInit();
            }
        };
        this.midiParser = new MIDIParser(file);
        this.midiParser.retrieveMidiData(null);
        this.midiPlayer = new MIDIPlayer(file);
        this.midiPlayer.printPatchList();
        this.continueInit();
    }

    private void setupPreviewPlayer() {
        this.lpPreviewSelectedTracks = new JLayeredPane();
        this.lpPreviewSelectedTracks.setBorder(new TitledBorder(new EtchedBorder(1, null, null), "Preview Selected Tracks", 4, 2, null, null));
        this.lpPreviewSelectedTracks.setBounds(10, 335, 599, 147);
        this.contentPanel.add(this.lpPreviewSelectedTracks);
        this.sliderTime = new JSlider();
        this.sliderTime.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent arg0) {
                DialogTrackImport.this.sliderTime.setValue(((BasicSliderUI)DialogTrackImport.this.sliderTime.getUI()).valueForXPosition(arg0.getX()));
                DialogTrackImport.this.midiPlayer.setTick(DialogTrackImport.this.sliderTime.getValue());
            }
        });
        this.sliderTime.addMouseMotionListener(new MouseMotionAdapter(){

            @Override
            public void mouseDragged(MouseEvent arg0) {
                DialogTrackImport.this.midiPlayer.setTick(DialogTrackImport.this.sliderTime.getValue());
                try {
                    Thread.sleep(40L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        this.sliderTime.setMaximum((int)this.midiPlayer.getTickLength());
        this.sliderTime.setPaintTicks(true);
        this.sliderTime.setValue(0);
        this.sliderTime.setOpaque(true);
        this.sliderTime.setForeground(Color.WHITE);
        this.sliderTime.setBackground(SystemColor.menu);
        this.btnPlay = new JButton("Play");
        this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_play_blue.png")));
        ArrayList<Long> eventTriggerTime = this.midiParser.getEventTriggerTime();
        this.btnPlay.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!DialogTrackImport.this.midiPlayer.isRunning()) {
                    DialogTrackImport.this.listPlayIndicator.setEnabled(true);
                    DialogTrackImport.this.btnPlay.setText("Pause");
                    DialogTrackImport.this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_pause_blue.png")));
                    DialogTrackImport.this.midiPlayer.unmuteAllTracks();
                    boolean isSelected = false;
                    for (int x = 0; x <= DialogTrackImport.this.listModel.getSize(); ++x) {
                        if (!DialogTrackImport.this.listTracks.isSelectedIndex(x)) {
                            DialogTrackImport.this.midiPlayer.muteTrack(x);
                            continue;
                        }
                        isSelected = true;
                    }
                    if (isSelected) {
                        DialogTrackImport.this.isPlaying = true;
                    }
                    DialogTrackImport.this.midiPlayer.playMIDI();
                    final TreeMap<Integer, ArrayList<Long>> tmTracksEvents = DialogTrackImport.this.midiParser.retrieveTracksEvents();
                    for (Map.Entry<Integer, ArrayList<Long>> entry : tmTracksEvents.entrySet()) {
                        Integer key = entry.getKey();
                        ArrayList<Long> value = entry.getValue();
                        System.out.println(key + " => " + value.size());
                    }
                    System.out.println("midiParser.getTriggerTimeSize() : " + DialogTrackImport.this.midiParser.getTriggerTimeSize());
                    ActionListener updateListener = new ActionListener(){
                        int iterator = 0;
                        long nextEventTick = 0L;
                        long currentTick = 0L;
                        long timeInBetween = 0L;

                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            this.timeInBetween = (DialogTrackImport.this.midiParser.getNextTriggerTime(this.iterator + 1) - DialogTrackImport.this.midiParser.getNextTriggerTime(this.iterator)) / 6L;
                            if (DialogTrackImport.this.midiPlayer.getCurrentTick() <= DialogTrackImport.this.midiPlayer.getTickLength()) {
                                DialogTrackImport.this.sliderTime.setValue((int)DialogTrackImport.this.midiPlayer.getCurrentTick());
                                this.iterator = DialogTrackImport.this.midiParser.getNextTriggerTimeElement(DialogTrackImport.this.midiPlayer.getCurrentTick());
                                if (DialogTrackImport.this.midiPlayer.getCurrentTick() >= DialogTrackImport.this.midiParser.getNextTriggerTime(this.iterator)) {
                                    long currentTick = DialogTrackImport.this.midiPlayer.getCurrentTick();
                                    int trackNum = -1;
                                    for (Map.Entry entry : tmTracksEvents.entrySet()) {
                                        ++trackNum;
                                        if (!((ArrayList)entry.getValue()).contains(DialogTrackImport.this.midiParser.getNextTriggerTime(this.iterator))) continue;
                                        DialogTrackImport.this.listPlayIndicator.addSelectionInterval((Integer)entry.getKey() - 1, (Integer)entry.getKey() - 1);
                                    }
                                    if ((long)(this.iterator + 1) != DialogTrackImport.this.midiParser.getTriggerTimeSize() - 1L) {
                                        ++this.iterator;
                                    }
                                }
                                if (DialogTrackImport.this.midiPlayer.getCurrentTick() >= DialogTrackImport.this.midiParser.getNextTriggerTime(this.iterator) - this.timeInBetween) {
                                    for (int x = 0; x <= DialogTrackImport.this.listModelPlayIndicator.getSize() - 1; ++x) {
                                        DialogTrackImport.this.listModelPlayIndicator.set(x, "\u266b Event");
                                    }
                                    DialogTrackImport.this.listPlayIndicator.clearSelection();
                                }
                            } else {
                                DialogTrackImport.this.listPlayIndicator.setEnabled(false);
                                DialogTrackImport.this.sliderTime.setValue(0);
                                DialogTrackImport.this.isPlaying = false;
                                DialogTrackImport.this.midiPlayer.stopMIDI();
                                DialogTrackImport.this.timer.stop();
                                DialogTrackImport.this.btnPlay.setText("Play");
                                DialogTrackImport.this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_play_blue.png")));
                            }
                            if (!DialogTrackImport.this.midiPlayer.isRunning()) {
                                DialogTrackImport.this.listPlayIndicator.setEnabled(false);
                                DialogTrackImport.this.btnPlay.setText("Play");
                                DialogTrackImport.this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_play_blue.png")));
                            } else {
                                DialogTrackImport.this.listPlayIndicator.setEnabled(true);
                            }
                        }
                    };
                    DialogTrackImport.this.timer = new Timer(1, updateListener);
                    DialogTrackImport.this.timer.start();
                } else {
                    DialogTrackImport.this.listPlayIndicator.setEnabled(false);
                    DialogTrackImport.this.midiPlayer.pauseMIDI();
                    DialogTrackImport.this.btnPlay.setText("Play");
                    DialogTrackImport.this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_play_blue.png")));
                }
            }
        });
        this.btnPlay.setForeground(Color.GRAY);
        this.btnStop = new JButton("Stop");
        this.btnStop.setIcon(new ImageIcon(getClass().getResource("/images/control_stop_blue.png")));
        this.btnStop.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DialogTrackImport.this.listTracks.setEnabled(true);
                DialogTrackImport.this.sliderTime.setValue(0);
                DialogTrackImport.this.isPlaying = false;
                if (DialogTrackImport.this.timer != null) {
                    DialogTrackImport.this.timer.stop();
                }
                DialogTrackImport.this.btnPlay.setText("Play");
                DialogTrackImport.this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_play_blue.png")));
                DialogTrackImport.this.midiPlayer.stopMIDI();
                DialogTrackImport.this.listPlayIndicator.setEnabled(false);
            }
        });
        this.btnStop.setForeground(Color.GRAY);
        this.btnStop.setBackground(Color.GRAY);
        this.sliderVolume = new JSlider();
        this.sliderVolume.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                DialogTrackImport.this.midiPlayer.setVolume(DialogTrackImport.this.sliderVolume.getValue());
            }
        });
        this.sliderVolume.setValue(100);
        this.sliderVolume.setPaintTicks(true);
        this.sliderVolume.setPaintLabels(true);
        this.sliderVolume.setBackground(SystemColor.menu);
        this.sliderVolume.putClientProperty("JSlider.isFilled", Boolean.TRUE);
        this.sliderVolume.setValue(100);
        this.lblVolume = new JLabel("Volume:");
    }

    private void setupImportListing(Track[] tracks) {
        this.pnlImport = new JPanel();
        this.tabbedPane.addTab("Import", null, this.pnlImport, null);
        this.pnlImport.setLayout(null);
        this.lpTrackList = new JLayeredPane();
        this.lpTrackList.setBounds(10, 11, 554, 263);
        this.pnlImport.add(this.lpTrackList);
        this.lpTrackList.setBorder(new TitledBorder(new EtchedBorder(1, null, null), "Instrument Import", 4, 2, null, null));
        this.spTracks = new JScrollPane();
        this.spTracks.setBounds(10, 39, 534, 213);
        this.lpTrackList.add(this.spTracks);
        this.spTracks.setHorizontalScrollBarPolicy(31);
        this.spTracks.setVerticalScrollBarPolicy(20);
        this.listModel = new DefaultListModel();
        this.listModelPlayIndicator = new DefaultListModel();
        ArrayList<Integer> trackEvents = this.midiParser.getTotalTrackEventsNumber();
        int trackNum = -1;
        Track[] arrtrack = tracks;
        int n = tracks.length;
        for (int i = 0; i < n; ++i) {
            Track track = arrtrack[i];
            System.out.println("Track : " + ++trackNum);
            this.listModel.addElement(String.valueOf(pc.getPatchName(this.midiParser.getPatchNumber(trackNum))) + " (" + trackEvents.get(trackNum + 1) + " notes)");
            this.listModelPlayIndicator.addElement("\u266b Event");
        }
        this.listTracks = new JList<String>(this.listModel);
        this.listTracks.setBackground(Color.LIGHT_GRAY);
        this.listTracks.setFont(new Font("Arial", 0, 12));
        this.spTracks.setViewportView(this.listTracks);
        this.listTracks.setBorder(new BevelBorder(1, null, null, null, null));
        this.listTracks.setSelectionMode(2);
        this.listTracks.setLayoutOrientation(0);
        this.listTracks.setVisibleRowCount(-1);
        this.listTracks.setSelectionModel(new DefaultListSelectionModel(){

            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });
        this.listPlayIndicator = new JList<String>(this.listModelPlayIndicator);
        this.listPlayIndicator.setEnabled(false);
        this.listPlayIndicator.setFont(new Font("Arial", 0, 12));
        this.listPlayIndicator.setOpaque(true);
        this.listPlayIndicator.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                for (int value : DialogTrackImport.this.listPlayIndicator.getSelectedIndices()) {
                    DialogTrackImport.this.listModelPlayIndicator.set(value, "\u266b Event");
                }
            }
        });
        this.spTracks.setRowHeaderView(this.listPlayIndicator);
        this.listPlayIndicator.setBackground(Color.LIGHT_GRAY);
        this.listPlayIndicator.setBorder(new BevelBorder(1, null, null, null, null));
        this.listPlayIndicator.setSelectionModel(new DefaultListSelectionModel(){

            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });
        this.lblSelectMidiTracks = new JLabel("Select Instruments to import:");
        this.lblSelectMidiTracks.setBounds(69, 24, 179, 14);
        this.lpTrackList.add(this.lblSelectMidiTracks);
        this.lblEvents = new JLabel("Events:");
        this.lblEvents.setBounds(10, 24, 59, 14);
        this.lpTrackList.add(this.lblEvents);
        this.listSelectionListener = new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (DialogTrackImport.this.listTracks.isSelectedIndex(listSelectionEvent.getFirstIndex())) {
                    DialogTrackImport.this.midiPlayer.unmuteTrack(listSelectionEvent.getFirstIndex());
                    System.out.println("Unmuted Track " + (listSelectionEvent.getFirstIndex() + 1));
                } else {
                    DialogTrackImport.this.midiPlayer.muteTrack(listSelectionEvent.getFirstIndex());
                    System.out.println("Muted Track " + (listSelectionEvent.getFirstIndex() + 1));
                }
                if (DialogTrackImport.this.listTracks.isSelectedIndex(listSelectionEvent.getLastIndex())) {
                    DialogTrackImport.this.midiPlayer.unmuteTrack(listSelectionEvent.getLastIndex());
                    System.out.println("Unmuted Track " + (listSelectionEvent.getLastIndex() + 1));
                } else {
                    DialogTrackImport.this.midiPlayer.muteTrack(listSelectionEvent.getLastIndex());
                    System.out.println("Muted Track " + (listSelectionEvent.getLastIndex() + 1));
                }
                int[] result = DialogTrackImport.this.listTracks.getSelectedIndices();
                for (int x = 0; x <= result.length - 1; ++x) {
                    result[x] = result[x] + 1;
                }
                System.out.println("Selected Track Indexes : " + Arrays.toString(result));
            }
        };
        this.listTracks.addListSelectionListener(this.listSelectionListener);
        for (int x = 0; x < this.listModel.getSize(); ++x) {
            this.listTracks.setSelectedIndex(x);
        }
    }

    private void continueInit() {
        this.tabbedPane = new JTabbedPane(1);
        this.tabbedPane.setBounds(10, 11, 599, 313);
        this.contentPanel.add(this.tabbedPane);
        this.setupImportListing(this.midiPlayer.getTracks());
        this.setupPreviewPlayer();
        this.lblOnlySelectedTracks = new JLabel("<html>Only highlighted tracks above (on Import tab) will be played. This is useful to exclude any drums or percussions in the MIDI file, or to exclude unnecessary tracks. Toggle the tracks while playing to hear what will be imported.</html>");
        GroupLayout gl_lpPreviewSelectedTracks = new GroupLayout(this.lpPreviewSelectedTracks);
        gl_lpPreviewSelectedTracks.setHorizontalGroup(gl_lpPreviewSelectedTracks.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(gl_lpPreviewSelectedTracks.createSequentialGroup().addGap(2).addGroup(gl_lpPreviewSelectedTracks.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.lblOnlySelectedTracks, -1, 575, 32767).addComponent(this.sliderTime, GroupLayout.Alignment.TRAILING, -1, 575, 32767).addGroup(GroupLayout.Alignment.TRAILING, gl_lpPreviewSelectedTracks.createSequentialGroup().addComponent(this.btnPlay, -2, 85, -2).addGap(6).addComponent(this.btnStop, -2, 83, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 271, 32767).addComponent(this.lblVolume, -2, 47, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.sliderVolume, -2, 73, -2))).addContainerGap()));
        gl_lpPreviewSelectedTracks.setVerticalGroup(gl_lpPreviewSelectedTracks.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(gl_lpPreviewSelectedTracks.createSequentialGroup().addComponent(this.lblOnlySelectedTracks, -1, 38, 32767).addGap(8).addComponent(this.sliderTime, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(gl_lpPreviewSelectedTracks.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.btnPlay, -2, 21, -2).addGroup(gl_lpPreviewSelectedTracks.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.btnStop, -2, 21, -2).addComponent(this.lblVolume)).addGroup(gl_lpPreviewSelectedTracks.createSequentialGroup().addGap(3).addComponent(this.sliderVolume, -2, 19, -2))).addGap(12)));
        this.lpPreviewSelectedTracks.setLayout(gl_lpPreviewSelectedTracks);
        this.pnlLineFormat = new JPanel();
        this.tabbedPane.addTab("Line Format", null, this.pnlLineFormat, null);
        this.pnlLineFormat.setLayout(null);
        this.layeredPane = new JLayeredPane();
        this.layeredPane.setBounds(10, 11, 574, 176);
        this.pnlLineFormat.add(this.layeredPane);
        this.layeredPane.setBorder(new TitledBorder(new EtchedBorder(1, null, null), "New Line Placement", 4, 2, null, null));
        this.rdbtnNoMeasure = new JRadioButton("Do not add new lines");
        this.rdbtnNoMeasure.setBounds(6, 67, 143, 23);
        this.layeredPane.add(this.rdbtnNoMeasure);
        this.rdbtnMeasureBar = new JRadioButton("Place a new line every: ");
        this.rdbtnMeasureBar.setSelected(true);
        this.rdbtnMeasureBar.setBounds(6, 41, 143, 23);
        this.layeredPane.add(this.rdbtnMeasureBar);
        ButtonGroup btngrpMeasure = new ButtonGroup();
        btngrpMeasure.add(this.rdbtnMeasureBar);
        btngrpMeasure.add(this.rdbtnNoMeasure);
        this.tfMeasure = new JFormattedTextField();
        this.tfMeasure.setText("1");
        this.tfMeasure.setColumns(10);
        this.tfMeasure.setBounds(149, 42, 21, 20);
        this.layeredPane.add(this.tfMeasure);
        this.rdbtnMeasureBar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogTrackImport.this.tfMeasure.setEnabled(true);
            }
        });
        this.rdbtnNoMeasure.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogTrackImport.this.tfMeasure.setEnabled(false);
            }
        });
        this.label = new JLabel("<html><i>A new line is calculated with relation to the time signature's measure / bar</i><html>");
        this.label.setBounds(10, 11, 463, 34);
        this.layeredPane.add(this.label);
        this.label_1 = new JLabel("measures / bars");
        this.label_1.setBounds(180, 45, 89, 14);
        this.layeredPane.add(this.label_1);
        this.separator_1 = new JSeparator();
        this.separator_1.setBounds(6, 91, 558, 8);
        this.layeredPane.add(this.separator_1);
        this.lbltimeSignatureDetermines = new JLabel("<html><i>Time Signature determines how many beats and types of beats there are in one measure/bar</i><html>");
        this.lbltimeSignatureDetermines.setEnabled(false);
        this.lbltimeSignatureDetermines.setBounds(10, 91, 554, 28);
        this.layeredPane.add(this.lbltimeSignatureDetermines);
        this.rdbtnAutoTimeSig = new JRadioButton("Auto detect Time Signature");
        this.rdbtnAutoTimeSig.setEnabled(false);
        this.rdbtnAutoTimeSig.setSelected(true);
        this.rdbtnAutoTimeSig.setBounds(6, 115, 178, 23);
        this.layeredPane.add(this.rdbtnAutoTimeSig);
        this.rdbtnCustomTimeSig = new JRadioButton("Set up a custom Time Signature :");
        this.rdbtnCustomTimeSig.setEnabled(false);
        this.rdbtnCustomTimeSig.setBounds(6, 141, 190, 23);
        this.layeredPane.add(this.rdbtnCustomTimeSig);
        ButtonGroup btngrpTimeSig = new ButtonGroup();
        btngrpTimeSig.add(this.rdbtnAutoTimeSig);
        btngrpTimeSig.add(this.rdbtnCustomTimeSig);
        this.tfTop = new JTextField();
        this.tfTop.setText("4");
        this.tfTop.setColumns(10);
        this.tfTop.setBounds(202, 142, 28, 20);
        this.tfTop.setEnabled(false);
        this.layeredPane.add(this.tfTop);
        this.tfBottom = new JTextField();
        this.tfBottom.setText("4");
        this.tfBottom.setColumns(10);
        this.tfBottom.setBounds(250, 142, 28, 20);
        this.tfBottom.setEnabled(false);
        this.layeredPane.add(this.tfBottom);
        this.label_3 = new JLabel("/");
        this.label_3.setEnabled(false);
        this.label_3.setBounds(240, 145, 11, 14);
        this.layeredPane.add(this.label_3);
        this.rdbtnCustomTimeSig.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogTrackImport.this.tfTop.setEnabled(true);
                DialogTrackImport.this.tfBottom.setEnabled(true);
            }
        });
        this.rdbtnAutoTimeSig.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogTrackImport.this.tfTop.setEnabled(false);
                DialogTrackImport.this.tfBottom.setEnabled(false);
            }
        });
        this.pnlNoteValueColour = new JPanel();
        this.tabbedPane.addTab("Note Value Colour", null, this.pnlNoteValueColour, null);
        this.tabbedPane.setEnabledAt(2, true);
        this.pnlNoteValueColour.setLayout(null);
        this.chckbxColourizeNoteValues = new JCheckBox("Indicate Note Values by Colour?");
        this.chckbxColourizeNoteValues.setSelected(true);
        this.chckbxColourizeNoteValues.setBounds(6, 10, 578, 23);
        this.pnlNoteValueColour.add(this.chckbxColourizeNoteValues);
        this.lpColour = new JLayeredPane();
        this.lpColour.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(1, null, null), "Colour Options", 4, 2, null, null), "Colour Options", 4, 2, null, null));
        this.lpColour.setBounds(6, 40, 578, 234);
        this.pnlNoteValueColour.add(this.lpColour);
        this.lblOctuplenote = new JLabel("octupleNote");
        this.lblOctuplenote.setBounds(10, 23, 95, 14);
        this.lblOctuplenote.setForeground(new Color(0, 100, 0));
        this.lpColour.add(this.lblOctuplenote);
        this.lblQuadruplenote = new JLabel("quadrupleNote");
        this.lblQuadruplenote.setBounds(10, 48, 95, 14);
        this.lblQuadruplenote.setForeground(new Color(0, 204, 0));
        this.lpColour.add(this.lblQuadruplenote);
        this.lblWholenote = new JLabel("wholeNote");
        this.lblWholenote.setBounds(10, 73, 95, 14);
        this.lblWholenote.setForeground(new Color(51, 255, 51));
        this.lpColour.add(this.lblWholenote);
        this.lblHalfnote = new JLabel("halfNote");
        this.lblHalfnote.setBounds(10, 98, 100, 14);
        this.lblHalfnote.setForeground(new Color(128, 255, 0));
        this.lpColour.add(this.lblHalfnote);
        this.lblQuarternote = new JLabel("quarterNote");
        this.lblQuarternote.setBounds(10, 123, 95, 14);
        this.lblQuarternote.setForeground(new Color(204, 204, 0));
        this.lpColour.add(this.lblQuarternote);
        this.lblEighthnote = new JLabel("eighthNote");
        this.lblEighthnote.setBounds(10, 148, 95, 14);
        this.lblEighthnote.setForeground(new Color(255, 128, 0));
        this.lpColour.add(this.lblEighthnote);
        this.lblSixteenthnote = new JLabel("sixteenthNote");
        this.lblSixteenthnote.setBounds(10, 173, 95, 14);
        this.lblSixteenthnote.setForeground(new Color(255, 151, 151));
        this.lpColour.add(this.lblSixteenthnote);
        this.lblThirtysecondnote = new JLabel("thirtySecondNote");
        this.lblThirtysecondnote.setBounds(10, 198, 95, 14);
        this.lblThirtysecondnote.setForeground(new Color(255, 0, 0));
        this.lpColour.add(this.lblThirtysecondnote);
        this.lblSixtyfourthnote = new JLabel("sixtyFourthNote");
        this.lblSixtyfourthnote.setBounds(153, 23, 158, 14);
        this.lblSixtyfourthnote.setForeground(new Color(153, 0, 0));
        this.lpColour.add(this.lblSixtyfourthnote);
        this.lblHundredtwentyeighthnote = new JLabel("hundredTwentyEighthNote");
        this.lblHundredtwentyeighthnote.setBounds(153, 48, 158, 14);
        this.lblHundredtwentyeighthnote.setForeground(new Color(153, 0, 0));
        this.lpColour.add(this.lblHundredtwentyeighthnote);
        this.lblTwohundredfiftysixthnote = new JLabel("twoHundredFiftySixthNote");
        this.lblTwohundredfiftysixthnote.setBounds(153, 73, 158, 14);
        this.lblTwohundredfiftysixthnote.setForeground(new Color(153, 0, 0));
        this.lpColour.add(this.lblTwohundredfiftysixthnote);
        this.btnColorchooserTest = new JButton("ColorChooser Test");
        this.btnColorchooserTest.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JColorChooser tcc = new JColorChooser(new Color(0, 0, 0));
                JColorChooser.showDialog(DialogTrackImport.this, "Colour Chooser", new Color(255, 255, 255));
            }
        });
        this.btnColorchooserTest.setBounds(153, 144, 158, 23);
        this.lpColour.add(this.btnColorchooserTest);
        this.chckbxShowColourReference = new JCheckBox("Show Colour Reference Window after import");
        this.chckbxShowColourReference.setBounds(153, 114, 331, 23);
        this.lpColour.add(this.chckbxShowColourReference);
        this.pnlNoteValueFormat = new JPanel();
        this.tabbedPane.addTab("Note Value Format", null, this.pnlNoteValueFormat, null);
        this.pnlNoteValueFormat.setLayout(null);
        this.chckbxUSeAdvanced = new JCheckBox("Use Advanced Formatting");
        this.chckbxUSeAdvanced.setBounds(6, 8, 578, 23);
        this.pnlNoteValueFormat.add(this.chckbxUSeAdvanced);
        this.layeredPane_2 = new JLayeredPane();
        this.layeredPane_2.setBorder(new TitledBorder(new EtchedBorder(1, null, null), "Advanced Note Options", 4, 2, null, null));
        this.layeredPane_2.setBounds(6, 38, 578, 236);
        this.pnlNoteValueFormat.add(this.layeredPane_2);
        this.tabbedPane.setEnabledAt(3, false);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(2));
        this.getContentPane().add((Component)buttonPane, "South");
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogTrackImport.this.midiPlayer.stopMIDI();
                if (DialogTrackImport.this.timer != null) {
                    DialogTrackImport.this.timer.stop();
                }
                DialogTrackImport.this.listTracks.getSelectedIndices();
                DialogTrackImport.this.selectedButton = 1;
                DialogTrackImport.this.setVisible(false);
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        this.getRootPane().setDefaultButton(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogTrackImport.this.selectedButton = 0;
                DialogTrackImport.this.setVisible(false);
                DialogTrackImport.this.midiPlayer.stopMIDI();
                if (DialogTrackImport.this.timer != null) {
                    DialogTrackImport.this.timer.stop();
                }
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    public int getSelectedButton() {
        return this.selectedButton;
    }

    public int getMeasures() {
        int returnValue = 0;
        returnValue = !this.rdbtnMeasureBar.isSelected() ? 0 : Integer.valueOf(this.tfMeasure.getText());
        return returnValue;
    }

    public int[] getSelectedTracks() {
        int[] result = this.listTracks.getSelectedIndices();
        for (int x = 0; x <= result.length - 1; ++x) {
            result[x] = result[x] + 1;
        }
        System.out.println("Selected Track Indexes : " + Arrays.toString(result));
        return result;
    }

    public boolean isCustomMeasure() {
        return this.rdbtnMeasureBar.isSelected();
    }

    public boolean isColourise() {
        return this.chckbxColourizeNoteValues.isSelected();
    }

    public boolean showColourRefAfterImport() {
        return this.chckbxShowColourReference.isSelected();
    }
}

