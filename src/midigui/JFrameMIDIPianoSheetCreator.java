/*
 * Decompiled with CFR 0.150.
 */
package midigui;

import midigui.DialogAbout;
import midigui.DialogColourReference;
import midigui.DialogOnScreenKeyboard;
import midigui.DialogOnScreenPiano;
import midigui.DialogPreferences;
import midigui.DialogTrackImport;
import midiparser.MIDIParser;
import midiparser.NoteColourConverter;
import midiplayer.MIDIPlayer;
import utils.FileExtensionFilter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class JFrameMIDIPianoSheetCreator
extends JFrame {
    private static JFrame frameHolder = null;
    public static JTextPane tpKeyEditor;
    public static MIDIParser midiParser = null;
    public static NoteColourConverter noteColourConverter = null;
    private JPanel contentPane = null;
    private JScrollPane spKeyEditor;
    private static JMenuItem mntmOpen;
    private JMenuItem mntmSave;
    private JProgressBar progressBar;
    private JSlider sliderTime;
    private JLabel lblSpeed;
    private JSpinner spinnerSpeed;
    private JLabel lblVolume;
    private JSlider sliderVolume;
    private JButton btnPlay;
    private JButton btnStop;
    private static DialogAbout dialogAbout;
    private static DialogPreferences dialogPreferences;
    public static DialogTrackImport dialogTrackImport;
    private static DialogColourReference dialogColourReference;
    private static DialogOnScreenKeyboard dialogOnScreenKeyboard;
    private static DialogOnScreenPiano dialogOnScreenPiano;
    private MIDIPlayer midiPlayer = null;
    private String openFileName = null;
    private String dirFileName = null;
    private String lastDirectory = null;
    private String currentFont = "Verdana";
    private int currentFontSize = 12;
    private Timer autoplayTimer = null;
    private int currentPatchNum = -1;
    private JMenuBar menuBar;
    private JMenu mnFont;
    private JCheckBoxMenuItem chckbxmntmAcousticGrandPiano;
    private JCheckBoxMenuItem chckbxmntmBrightAcousticPiano;
    private JCheckBoxMenuItem chckbxmntmElectricGrandPiano;
    private JCheckBoxMenuItem chckbxmntmUseMidiDefault;
    private JCheckBoxMenuItem chckbxmntmHonkyTonkPiano;
    private JCheckBoxMenuItem chckbxmntmElectricPiano1;
    private JCheckBoxMenuItem chckbxmntmElectricPiano2;
    private JCheckBoxMenuItem chckbxmntmHarpsichord;
    private JCheckBoxMenuItem chckbxmntmClavinet;
    private JCheckBoxMenuItem mntmSize_8;
    private JCheckBoxMenuItem mntmSize_10;
    private JCheckBoxMenuItem mntmSize_12;
    private JCheckBoxMenuItem mntmSize_14;
    private JCheckBoxMenuItem mntmSize_16;
    private JCheckBoxMenuItem mntmSize_18;
    private JCheckBoxMenuItem mntmSize_24;
    private JCheckBoxMenuItem mntmSize_36;
    private JCheckBoxMenuItem mntmSize_48;
    private JCheckBoxMenuItem mntmLucidaConsole;
    private JCheckBoxMenuItem mntmTahoma;
    private JCheckBoxMenuItem mntmVerdana;
    private JCheckBoxMenuItem mntmSegoeUi;
    private JMenu mnView;
    private JMenuItem mntmColourNoteValues;
    private JMenuItem mntmOnscreenKeyboard;
    private JMenuItem mntmOnscreenPiano;
    private JMenu mnFile;
    private JLabel lblTranspose;
    private JSpinner spinnerSpeed_1;

    static {
        dialogAbout = null;
        dialogPreferences = null;
        dialogTrackImport = null;
        dialogColourReference = null;
        dialogOnScreenKeyboard = null;
        dialogOnScreenPiano = null;
    }

    JFrameMIDIPianoSheetCreator() {
        this.setMinimumSize(new Dimension(505, 585));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setIconImage(new ImageIcon(getClass().getResource("/images/icongrey.png")).getImage());
        this.setResizable(true);
        this.setTitle("MIDI to Computer Keys Converter - v0.8 alpha");
        this.setDefaultCloseOperation(3);
        this.contentPane = new JPanel();
        this.contentPane.setBackground(new Color(0x585858));
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBorder(new TitledBorder(new EtchedBorder(1, null, null), "Key Editor", 4, 2, null, null));
        this.initDialogs();
        this.initProgressSliders();
        this.initTextPane();
        this.initMenuBar();
        this.initButtons();
        this.lblSpeed = new JLabel("Speed:");
        this.spinnerSpeed = new JSpinner();
        this.spinnerSpeed.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent evt) {
                JFrameMIDIPianoSheetCreator.this.midiPlayer.setSpeed((Float) JFrameMIDIPianoSheetCreator.this.spinnerSpeed.getValue());
            }
        });
        this.spinnerSpeed.setModel(new SpinnerNumberModel(new Float(1.0f), new Float(0.1), null, new Float(0.1)));
        this.spinnerSpeed.setEnabled(false);
        GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
        gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(layeredPane, GroupLayout.Alignment.LEADING, -1, 449, 32767).addComponent(this.progressBar, -1, 449, 32767)).addGap(0)));
        gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(layeredPane, -2, 492, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.progressBar, -2, 20, -2).addGap(9)));

        lblTranspose = new JLabel("Transpose:");

        spinnerSpeed_1 = new JSpinner();
        spinnerSpeed_1.setModel(new SpinnerNumberModel(0, -12, 12, 1));
        spinnerSpeed_1.setEnabled(false);
        this.spinnerSpeed_1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                midiParser.changeTranspose(midiParser.getTmMidiParsedData(), (Integer) JFrameMIDIPianoSheetCreator.this.spinnerSpeed_1.getValue());
                tpKeyEditor.setText(midiParser.getParsedData());

                if (dialogTrackImport.isColourise()) {
                    tpKeyEditor.setStyledDocument(noteColourConverter.ColouriseNotes());
                } else {
                    StyleContext cont = StyleContext.getDefaultStyleContext();
                    AttributeSet grey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(192, 192, 192));
                    tpKeyEditor.getStyledDocument().setCharacterAttributes(0, tpKeyEditor.getDocument().getLength(), grey, false);
                }
                tpKeyEditor.setCaretPosition(0);
            }
        });
        GroupLayout gl_layeredPane = new GroupLayout(layeredPane);
        gl_layeredPane.setHorizontalGroup(
        	gl_layeredPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_layeredPane.createSequentialGroup()
        			.addGap(4)
        			.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
        				.addComponent(spKeyEditor, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        				.addGroup(gl_layeredPane.createSequentialGroup()
        					.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
        						.addGroup(gl_layeredPane.createSequentialGroup()
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
        							.addGap(15)
        							.addComponent(lblTranspose)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(spinnerSpeed_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
        							.addGap(15))
        						.addGroup(Alignment.TRAILING, gl_layeredPane.createSequentialGroup()
        							.addComponent(lblVolume)
        							.addGap(41)))
        					.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblSpeed, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
        						.addComponent(spinnerSpeed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        				.addComponent(sliderTime, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_layeredPane.setVerticalGroup(
        	gl_layeredPane.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_layeredPane.createSequentialGroup()
        			.addGap(5)
        			.addComponent(spKeyEditor, GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
        			.addGap(8)
        			.addComponent(sliderTime, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_layeredPane.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE)
        					.addComponent(btnPlay, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        					.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_layeredPane.createSequentialGroup()
        					.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE)
        						.addComponent(lblSpeed)
        						.addComponent(lblVolume))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_layeredPane.createParallelGroup(Alignment.LEADING)
        						.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
        						.addGroup(gl_layeredPane.createParallelGroup(Alignment.BASELINE)
        							.addComponent(lblTranspose)
        							.addComponent(spinnerSpeed_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addComponent(spinnerSpeed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        			.addGap(10))
        );
        layeredPane.setLayout(gl_layeredPane);
        this.contentPane.setLayout(gl_contentPane);
    }

    private void initDialogs() {
        dialogAbout = new DialogAbout(this);
        dialogAbout.setLocationRelativeTo(this);
        dialogPreferences = new DialogPreferences(this);
        dialogPreferences.setLocationRelativeTo(this);
        dialogColourReference = new DialogColourReference(this);
        dialogColourReference.setLocationRelativeTo(this);
        dialogOnScreenKeyboard = new DialogOnScreenKeyboard(this);
        dialogOnScreenKeyboard.setLocationRelativeTo(this);
        dialogOnScreenPiano = new DialogOnScreenPiano(this);
        dialogOnScreenPiano.setLocationRelativeTo(this);
        frameHolder = this;
    }

    private void initButtons() {
        this.initButtonPlay();
        this.initButtonStop();
    }

    private void initButtonPlay() {
        this.btnPlay = new JButton("Play");
        this.btnPlay.setForeground(Color.GRAY);
        this.btnPlay.setBackground(Color.GRAY);
        this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_play_blue.png")));
        this.btnPlay.setEnabled(false);
        this.btnPlay.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!JFrameMIDIPianoSheetCreator.this.midiPlayer.isRunning()) {
                    JFrameMIDIPianoSheetCreator.this.btnPlay.setText("Pause");
                    JFrameMIDIPianoSheetCreator.this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_pause_blue.png")));
                    JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setEnabled(true);
                    JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setEditable(false);
                    JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setCaretPosition(0);
                    JFrameMIDIPianoSheetCreator.this.sliderTime.setMaximum((int)JFrameMIDIPianoSheetCreator.this.midiPlayer.getTickLength());
                    JFrameMIDIPianoSheetCreator.this.midiPlayer.playMIDI();
                    JFrameMIDIPianoSheetCreator.this.midiPlayer.setInstrument(JFrameMIDIPianoSheetCreator.this.currentPatchNum);
                    System.out.println("spKeyEditor Vertical Visible : " + JFrameMIDIPianoSheetCreator.this.spKeyEditor.getVerticalScrollBar().getVisibleAmount());
                    System.out.println("spKeyEditor Vertical Current Value : " + JFrameMIDIPianoSheetCreator.this.spKeyEditor.getVerticalScrollBar().getValue());
                    System.out.println("spKeyEditor Vertical Total : " + JFrameMIDIPianoSheetCreator.this.spKeyEditor.getVerticalScrollBar().getMaximum());
                    ActionListener updateListener = new ActionListener(){
                        private String space = " ";
                        private String fullText = null;
                        private int iterator = 0;
                        private long nextEventTick = 0L;
                        private long currentTick = 0L;
                        private int length = 0;
                        private int currentPosition = 0;
                        private int endPosition = 0;
                        private int lastCurrentPosition = 0;
                        private int lastLength = 0;

                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            JFrameMIDIPianoSheetCreator.this.sliderTime.setValue((int)JFrameMIDIPianoSheetCreator.this.midiPlayer.getCurrentTick());
                            StyleContext cont = StyleContext.getDefaultStyleContext();
                            AttributeSet grey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(122, 122, 122));
                            AttributeSet orange = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.ORANGE);
                            SimpleAttributeSet sas = new SimpleAttributeSet();
                            SimpleAttributeSet sasNothing = new SimpleAttributeSet();
                            StyleConstants.setUnderline(sas, true);
                            if ((long)this.iterator != JFrameMIDIPianoSheetCreator.this.midiParser.getTriggerTimeSize()) {
                                this.nextEventTick = JFrameMIDIPianoSheetCreator.this.midiParser.getNextTriggerTime(this.iterator);
                                this.currentTick = JFrameMIDIPianoSheetCreator.this.midiPlayer.getCurrentTick();
                                if (this.currentTick > this.nextEventTick) {
                                    ++this.iterator;
                                    if (dialogOnScreenKeyboard.isVisible()) {
                                        dialogOnScreenKeyboard.highlightKeys(JFrameMIDIPianoSheetCreator.this.midiParser.getNextTriggerNote(this.iterator - 1));
                                    }
                                    try {
                                        this.fullText = JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getDocument().getText(0, JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getDocument().getLength());
                                    }
                                    catch (BadLocationException e2) {
                                        e2.printStackTrace();
                                    }
                                    this.endPosition = this.fullText.indexOf(this.space, this.currentPosition);
                                    this.length = this.endPosition - this.currentPosition;
                                    if (dialogTrackImport.isColourise()) {
                                        JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getStyledDocument().setCharacterAttributes(this.currentPosition, this.length, sas, false);
                                    } else {
                                        JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getStyledDocument().setCharacterAttributes(this.currentPosition, this.length, orange, false);
                                    }
                                    if (this.iterator != 1) {
                                        JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getStyledDocument().setCharacterAttributes(this.lastCurrentPosition, this.lastLength, sasNothing, true);
                                        JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getStyledDocument().setCharacterAttributes(this.lastCurrentPosition, this.lastLength, grey, false);
                                    }
                                    this.lastLength = this.length;
                                    this.lastCurrentPosition = this.currentPosition;
                                    if (this.endPosition + 1 != this.fullText.length()) {
                                        while (this.fullText.charAt(this.endPosition + 1) == ' ') {
                                            System.out.println("Yes, space is there");
                                            ++this.endPosition;
                                        }
                                    }
                                    ++this.endPosition;
                                    this.currentPosition = this.endPosition;
                                    this.length = 0;
                                }
                            } else {
                                this.iterator = 0;
                                this.nextEventTick = 0L;
                                this.currentTick = 0L;
                                this.length = 0;
                                this.currentPosition = 0;
                                this.endPosition = 0;
                                this.lastCurrentPosition = 0;
                                this.lastLength = 0;
                                JFrameMIDIPianoSheetCreator.this.midiPlayer.stopMIDI();
                                JFrameMIDIPianoSheetCreator.this.midiPlayer.setInstrument(JFrameMIDIPianoSheetCreator.this.currentPatchNum);
                                JFrameMIDIPianoSheetCreator.this.autoplayTimer.stop();
                                JFrameMIDIPianoSheetCreator.this.sliderTime.setValue(0);
                                JFrameMIDIPianoSheetCreator.this.btnPlay.setText("Play");
                                JFrameMIDIPianoSheetCreator.this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_play_blue.png")));
                                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setEnabled(true);
                                AttributeSet lightgrey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(192, 192, 192));
                                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getStyledDocument().setCharacterAttributes(0, JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getDocument().getLength(), lightgrey, false);
                                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setText(JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getText());
                                if (dialogTrackImport.isColourise()) {
                                    JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setStyledDocument(JFrameMIDIPianoSheetCreator.this.noteColourConverter.ColouriseNotes());
                                }
                                JFrameMIDIPianoSheetCreator.this.btnPlay.setEnabled(true);
                                JFrameMIDIPianoSheetCreator.this.autoplayTimer.stop();
                            }
                        }
                    };
                    JFrameMIDIPianoSheetCreator.this.autoplayTimer = new Timer(1, updateListener);
                    JFrameMIDIPianoSheetCreator.this.autoplayTimer.start();
                } else {
                    JFrameMIDIPianoSheetCreator.this.btnPlay.setText("Play");
                    JFrameMIDIPianoSheetCreator.this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_play_blue.png")));
                    JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setEnabled(true);
                    JFrameMIDIPianoSheetCreator.this.midiPlayer.pauseMIDI();
                    JFrameMIDIPianoSheetCreator.this.btnPlay.setEnabled(true);
                    JFrameMIDIPianoSheetCreator.this.autoplayTimer.stop();
                }
            }
        });
    }

    private void initButtonStop() {
        this.btnStop = new JButton("Stop");
        this.btnStop.setBackground(Color.GRAY);
        this.btnStop.setForeground(Color.GRAY);
        this.btnStop.setIcon(new ImageIcon(getClass().getResource("/images/control_stop_blue.png")));
        this.btnStop.setEnabled(false);
        this.btnStop.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.btnPlay.setEnabled(true);
                JFrameMIDIPianoSheetCreator.this.btnPlay.setText("Play");
                JFrameMIDIPianoSheetCreator.this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_play_blue.png")));
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setEnabled(true);
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setCaretPosition(0);
                JFrameMIDIPianoSheetCreator.this.sliderTime.setValue(0);
                if (JFrameMIDIPianoSheetCreator.this.midiPlayer != null) {
                    JFrameMIDIPianoSheetCreator.this.midiPlayer.stopMIDI();
                    JFrameMIDIPianoSheetCreator.this.midiPlayer.setInstrument(JFrameMIDIPianoSheetCreator.this.currentPatchNum);
                }
                if (JFrameMIDIPianoSheetCreator.this.autoplayTimer != null) {
                    JFrameMIDIPianoSheetCreator.this.autoplayTimer.stop();
                }
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setText(JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getText());
                if (dialogTrackImport.isColourise()) {
                    JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setStyledDocument(JFrameMIDIPianoSheetCreator.this.noteColourConverter.ColouriseNotes());
                } else {
                    StyleContext cont = StyleContext.getDefaultStyleContext();
                    AttributeSet grey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(192, 192, 192));
                    JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getStyledDocument().setCharacterAttributes(0, JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getDocument().getLength(), grey, false);
                }

                tpKeyEditor.setCaretPosition(0);
            }
        });
    }

    private void initProgressSliders() {
        this.progressBar = new JProgressBar(0, 100);
        this.progressBar.setValue(0);
        this.progressBar.setString("Load a MIDI File to Parse");
        this.progressBar.setStringPainted(true);
        this.sliderTime = new JSlider();
        this.sliderTime.setForeground(Color.WHITE);
        this.sliderTime.setBackground(Color.GRAY);
        this.sliderTime.setPaintTicks(true);
        this.sliderTime.setPaintTrack(true);
        this.sliderTime.setOpaque(true);
        this.sliderTime.setValue(0);
        this.sliderTime.setEnabled(false);
        this.lblVolume = new JLabel("Volume:");
        this.sliderVolume = new JSlider();
        this.sliderVolume.setPaintLabels(true);
        this.sliderVolume.setPaintTicks(true);
        this.sliderVolume.setBackground(Color.GRAY);
        this.sliderVolume.putClientProperty("JSlider.isFilled", Boolean.TRUE);
        this.sliderVolume.setValue(100);
        this.sliderVolume.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.midiPlayer.setVolume(JFrameMIDIPianoSheetCreator.this.sliderVolume.getValue());
            }
        });
    }

    private void initTextPane() {
        this.tpKeyEditor = new JTextPane(){

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return this.getUI().getPreferredSize((JComponent)this).width <= this.getParent().getSize().width;
            }
        };
        this.tpKeyEditor.setBackground(Color.DARK_GRAY);
        this.tpKeyEditor.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
        this.tpKeyEditor.setFont(new Font(this.currentFont, 0, 12));
        this.tpKeyEditor.setEnabled(false);
        this.spKeyEditor = new JScrollPane();
        this.spKeyEditor.setVerticalScrollBarPolicy(22);
        this.spKeyEditor.setHorizontalScrollBarPolicy(30);
        this.spKeyEditor.setViewportView(this.tpKeyEditor);
        this.wordWrapOn();
        this.tpKeyEditor.setFont(new Font(this.currentFont, 0, this.currentFontSize));
        this.tpKeyEditor.setText("Welcome to MIDI to Computer Keys Converter 2023! (v0.1)\r\n\u266b\r\nThis program was originally created for Garry's Mod's \"Playable Piano\" Addon (by MacDGuy) from MIDI files:\r\nhttp://steamcommunity.com/sharedfiles/filedetails/?id=104548572\r\nand Virtual Piano ( virtualpiano.net )\r\n\r\n------------------------------------------------------\r\nProgrammed in Java using Eclipse Luna IDE, with WindowBuilder (GUI) and IntelliJ IDEA.\r\nSource code and this software is free and open source, and is under GNU GPL v3 license.\r\nOpening of Jar file permitted (Unencrypted and Unobfuscated). Written by Little Cute Lion, 2014. Modified by Albacusphetical, 2023. \r\n\r\nPlay and Stop button Silk Icons are created by Mark James under Creative Commons Attribution 2.5 License. \r\n( http://www.famfamfam.com/lab/icons/silk/ )\r\n\r\nNote! This software is still in development and has incomplete features.\r\n\r\nEnjoy!" +
                "\r\n------------------------------------------------------\r\n\r\nRelease Notes\r\n\r\n" +
                "● Added Transpose function" + "\r\n● Adjusted theme color to improve readability of coloured notes."
        );
        this.tpKeyEditor.setCaretPosition(0);
    }

    private void wordWrapOn() {
        String previousText = this.tpKeyEditor.getText();
        this.tpKeyEditor = new JTextPane();
        this.tpKeyEditor.setEditable(false);
        this.tpKeyEditor.setForeground(Color.LIGHT_GRAY);
        this.tpKeyEditor.setBackground(Color.DARK_GRAY);
        this.tpKeyEditor.setFont(new Font(this.currentFont, 0, this.currentFontSize));
        this.spKeyEditor.setViewportView(this.tpKeyEditor);
        this.tpKeyEditor.setText(previousText);
    }

    private void wordWrapOff() {
        String previousText = this.tpKeyEditor.getText();
        this.tpKeyEditor = new JTextPane(){

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return this.getUI().getPreferredSize((JComponent)this).width <= this.getParent().getSize().width;
            }
        };
        this.tpKeyEditor.setBackground(Color.DARK_GRAY);
        this.tpKeyEditor.setFont(new Font(this.currentFont, 0, this.currentFontSize));
        this.tpKeyEditor.setEditable(false);
        this.tpKeyEditor.setEnabled(true);
        this.spKeyEditor.setViewportView(this.tpKeyEditor);
        this.tpKeyEditor.setText(previousText);
    }

    private void initMenuBar() {
        this.menuBar = new JMenuBar();
        this.setJMenuBar(this.menuBar);
        this.initMenuFile();
        this.initMenuInstruments();
        this.mnFont = new JMenu("Font");
        this.menuBar.add(this.mnFont);
        this.initMenuFontSize();
        this.initMenuFontFamily();
        this.initMenuHelp();
    }

    private void initMenuHelp() {
        this.mnView = new JMenu("View");
        this.menuBar.add(this.mnView);
        this.mntmColourNoteValues = new JMenuItem("Colour Reference");
        this.mntmColourNoteValues.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!dialogColourReference.isVisible()) {
                    dialogColourReference.setLocationRelativeTo(frameHolder);
                    dialogColourReference.setVisible(true);
                } else {
                    dialogColourReference.toFront();
                }
            }
        });
        this.mntmOnscreenKeyboard = new JMenuItem("On-Screen Keyboard");
        this.mntmOnscreenKeyboard.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!dialogOnScreenKeyboard.isVisible()) {
                    dialogOnScreenKeyboard.setLocationRelativeTo(frameHolder);
                    dialogOnScreenKeyboard.setVisible(true);
                } else {
                    dialogOnScreenKeyboard.toFront();
                }
            }
        });
        this.mntmOnscreenPiano = new JMenuItem("On-Screen Piano");
        this.mntmOnscreenPiano.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!dialogOnScreenPiano.isVisible()) {
                    dialogOnScreenPiano.setLocationRelativeTo(frameHolder);
                    dialogOnScreenPiano.setVisible(true);
                } else {
                    dialogOnScreenPiano.toFront();
                }
            }
        });
        this.mnView.add(this.mntmOnscreenPiano);
        this.mnView.add(this.mntmOnscreenKeyboard);
        this.mnView.add(this.mntmColourNoteValues);
        JMenu mnHelp = new JMenu("Help");
        this.menuBar.add(mnHelp);
        JMenuItem mntmViewHelp = new JMenuItem("View Help");
        mntmViewHelp.setEnabled(false);
        mntmViewHelp.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new JFrame(), "nope.", "Help", -1);
            }
        });
        mnHelp.add(mntmViewHelp);
        JMenuItem mntmAbout = new JMenuItem("About");
        mntmAbout.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dialogAbout.setLocationRelativeTo(frameHolder);
                dialogAbout.setVisible(true);
            }
        });
        mnHelp.add(mntmAbout);
    }

    private static void disableNewFolderButton(Container c) {
        int len = c.getComponentCount();
        for (int i = 0; i < len; ++i) {
            Component comp = c.getComponent(i);
            if (comp instanceof JButton) {
                JButton b = (JButton)comp;
                Icon icon = b.getIcon();
                if (icon == null || icon != UIManager.getIcon("FileChooser.newFolderIcon")) continue;
                b.setEnabled(false);
                continue;
            }
            if (!(comp instanceof Container)) continue;
            JFrameMIDIPianoSheetCreator.disableNewFolderButton((Container)comp);
        }
    }

    private void initMenuFile() {
        this.mnFile = new JMenu("File");
        this.menuBar.add(this.mnFile);
        this.initMntmOpen();
        this.initMntmSave();
        this.mnFile.addSeparator();
        JMenuItem mntmPreferences = new JMenuItem("Preferences");
        this.mnFile.add(mntmPreferences);
        mntmPreferences.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dialogPreferences.setLocationRelativeTo(frameHolder);
                dialogPreferences.setVisible(true);
            }
        });
        this.mnFile.addSeparator();
        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.mnFile.add(mntmExit);
    }

    private void initMntmSave() {
        this.mntmSave = new JMenuItem("Save To Text");
        this.mntmSave.setEnabled(false);
        this.mntmSave.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setEnabled(true);
                JFileChooser fileChooserDialogueSave = new JFileChooser(JFrameMIDIPianoSheetCreator.this.lastDirectory){

                    @Override
                    public void approveSelection() {
                        File f = this.getSelectedFile();
                        if (f.exists() && this.getDialogType() == 1) {
                            Toolkit.getDefaultToolkit().beep();
                            int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", 0);
                            switch (result) {
                                case 0: {
                                    super.approveSelection();
                                    return;
                                }
                                case 2: {
                                    this.cancelSelection();
                                    return;
                                }
                            }
                            return;
                        }
                        super.approveSelection();
                    }
                };
                FileExtensionFilter fileFilter = new FileExtensionFilter(false);
                fileChooserDialogueSave.addChoosableFileFilter(fileFilter);
                fileChooserDialogueSave.setFileFilter(fileFilter);
                fileChooserDialogueSave.setAcceptAllFileFilterUsed(true);
                fileChooserDialogueSave.setPreferredSize(new Dimension(575, 495));
                String noExtensionName = JFrameMIDIPianoSheetCreator.this.openFileName.substring(0, JFrameMIDIPianoSheetCreator.this.openFileName.length() - 4);
                fileChooserDialogueSave.setSelectedFile(new File(String.valueOf(noExtensionName) + ".txt"));
                int returnVal = fileChooserDialogueSave.showSaveDialog(JFrameMIDIPianoSheetCreator.this);
                if (returnVal == 0) {
                    File file = fileChooserDialogueSave.getSelectedFile();
                    String file_name = file.toString();
                    if (!file_name.endsWith(".txt")) {
                        file_name = String.valueOf(file_name) + ".txt";
                        file.renameTo(new File(file_name));
                    }
                    try {
                        BufferedWriter newTextFile = new BufferedWriter(new FileWriter(file));
                        BufferedWriter fw = new BufferedWriter(newTextFile);
                        fw.write(JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getText());
                        fw.close();
                    }
                    catch (IOException iox) {
                        iox.printStackTrace();
                    }
                }
            }
        });
        this.mnFile.add(this.mntmSave);
    }

    private void initMntmOpen() {
        mntmOpen = new JMenuItem("Open MIDI");
        this.mnFile.add(mntmOpen);
        mntmOpen.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (JFrameMIDIPianoSheetCreator.this.lastDirectory == null) {
                    JFrameMIDIPianoSheetCreator.this.lastDirectory = String.valueOf(System.getProperty("user.home")) + "\\Desktop";
                }
                JFileChooser fileChooserOpenDialogue = new JFileChooser(JFrameMIDIPianoSheetCreator.this.lastDirectory);
                fileChooserOpenDialogue.setPreferredSize(new Dimension(575, 495));
                JFrameMIDIPianoSheetCreator.disableNewFolderButton(fileChooserOpenDialogue);
                FileExtensionFilter fileFilter = new FileExtensionFilter(true);
                fileChooserOpenDialogue.addChoosableFileFilter(fileFilter);
                fileChooserOpenDialogue.setFileFilter(fileFilter);
                fileChooserOpenDialogue.setAcceptAllFileFilterUsed(false);
                int returnVal = fileChooserOpenDialogue.showOpenDialog(JFrameMIDIPianoSheetCreator.this);
                File file = fileChooserOpenDialogue.getSelectedFile();
                if (file != null) {
                    JFrameMIDIPianoSheetCreator.this.openFileName = file.getName();
                    JFrameMIDIPianoSheetCreator.this.dirFileName = file.getAbsolutePath();
                    JFrameMIDIPianoSheetCreator.this.lastDirectory = file.getParent();
                }
                if (returnVal == 1) {
                    JFrameMIDIPianoSheetCreator.this.progressBar.setIndeterminate(false);
                }
                if (returnVal == 0) {
                    JFrameMIDIPianoSheetCreator.this.progressBar.setIndeterminate(true);
                    System.out.println("----------------------------------");
                    if (JFrameMIDIPianoSheetCreator.this.midiPlayer != null) {
                        JFrameMIDIPianoSheetCreator.this.midiPlayer.stopMIDI();
                    }
                    if (JFrameMIDIPianoSheetCreator.this.autoplayTimer != null) {
                        JFrameMIDIPianoSheetCreator.this.autoplayTimer.stop();
                    }
                    if (dialogPreferences.isAdvancedTrackImport()) {
                        SwingWorker midiParserWorker = JFrameMIDIPianoSheetCreator.this.setupSwingWorkerMIDIParse(file);
                        JFrameMIDIPianoSheetCreator.dialogTrackImport = new DialogTrackImport(frameHolder, file);
                        dialogTrackImport.setLocationRelativeTo(frameHolder);
                        dialogTrackImport.setVisible(true);
                        if (dialogTrackImport.getSelectedButton() == 1) {
                            JFrameMIDIPianoSheetCreator.this.progressBar.setIndeterminate(false);
                            System.out.println();
                            System.out.println("Loading " + JFrameMIDIPianoSheetCreator.this.openFileName);
                            if (!dialogTrackImport.isCustomMeasure()) {
                                JFrameMIDIPianoSheetCreator.this.wordWrapOn();
                            } else {
                                JFrameMIDIPianoSheetCreator.this.wordWrapOff();
                            }
                            JFrameMIDIPianoSheetCreator.this.progressBar.setIndeterminate(true);
                            JFrameMIDIPianoSheetCreator.this.progressBar.setString("Please wait, Parsing: " + JFrameMIDIPianoSheetCreator.this.openFileName);
                            JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setText("Please Wait...");
                            System.out.println("dialogImportOptions.getMeasures() : " + dialogPreferences.getMeasures());
                            midiParserWorker.execute();
                        } else if (dialogTrackImport.getSelectedButton() == 0) {
                            JFrameMIDIPianoSheetCreator.this.progressBar.setIndeterminate(false);
                        }
                    } else {
                        SwingWorker midiParserWorker = JFrameMIDIPianoSheetCreator.this.setupSwingWorkerMIDIParse(file);
                        if (!dialogPreferences.isCustomMeasure()) {
                            JFrameMIDIPianoSheetCreator.this.wordWrapOn();
                        } else {
                            JFrameMIDIPianoSheetCreator.this.wordWrapOff();
                        }
                        midiParserWorker.execute();
                    }
                }
            }
        });
    }

    private SwingWorker<String, File> setupSwingWorkerMIDIParse(File file) {
        final File tmpFile = file;
        SwingWorker<String, File> midiParserWorker = new SwingWorker<String, File>(){

            @Override
            public String doInBackground() {
                JFrameMIDIPianoSheetCreator.this.midiParser = new MIDIParser(tmpFile);
                if (dialogPreferences.isAdvancedTrackImport()) {
                    JFrameMIDIPianoSheetCreator.this.midiParser.setMeasurePerLine(dialogTrackImport.getMeasures());
                    JFrameMIDIPianoSheetCreator.this.midiParser.retrieveMidiData(dialogTrackImport.getSelectedTracks());
                } else {
                    JFrameMIDIPianoSheetCreator.this.midiParser.setMeasurePerLine(dialogPreferences.getMeasures());
                    JFrameMIDIPianoSheetCreator.this.midiParser.retrieveMidiData(null);
                }
                return null;
            }

            @Override
            public void done() {
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setText(JFrameMIDIPianoSheetCreator.this.midiParser.getParsedData());
                dialogOnScreenKeyboard.setText(JFrameMIDIPianoSheetCreator.this.midiParser.getParsedData());
                JFrameMIDIPianoSheetCreator.this.noteColourConverter = new NoteColourConverter(JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getStyledDocument(), JFrameMIDIPianoSheetCreator.this.midiParser.getEventTriggerTime(), JFrameMIDIPianoSheetCreator.this.midiParser.getQuarterNote());
                if (dialogTrackImport.isColourise()) {
                    JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setStyledDocument(JFrameMIDIPianoSheetCreator.this.noteColourConverter.ColouriseNotes());
                }
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setCaretPosition(0);
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setEnabled(true);
                JFrameMIDIPianoSheetCreator.this.progressBar.setIndeterminate(false);
                JFrameMIDIPianoSheetCreator.this.progressBar.setValue(100);
                JFrameMIDIPianoSheetCreator.this.progressBar.setString("Loaded File - " + JFrameMIDIPianoSheetCreator.this.openFileName);
                JFrameMIDIPianoSheetCreator.this.midiPlayer = new MIDIPlayer(new File(JFrameMIDIPianoSheetCreator.this.dirFileName));
                JFrameMIDIPianoSheetCreator.this.midiPlayer.setVolume(JFrameMIDIPianoSheetCreator.this.sliderVolume.getValue());
                JFrameMIDIPianoSheetCreator.this.midiPlayer.setInstrument(JFrameMIDIPianoSheetCreator.this.currentPatchNum);
                JFrameMIDIPianoSheetCreator.this.btnPlay.setEnabled(true);
                JFrameMIDIPianoSheetCreator.this.btnPlay.setText("Play");
                JFrameMIDIPianoSheetCreator.this.btnPlay.setIcon(new ImageIcon(getClass().getResource("/images/control_play_blue.png")));
                JFrameMIDIPianoSheetCreator.this.btnStop.setEnabled(true);
                JFrameMIDIPianoSheetCreator.this.mntmSave.setEnabled(true);
                JFrameMIDIPianoSheetCreator.this.sliderTime.setEnabled(true);
                JFrameMIDIPianoSheetCreator.this.sliderTime.setMaximum((int)JFrameMIDIPianoSheetCreator.this.midiPlayer.getTickLength());
                JFrameMIDIPianoSheetCreator.this.sliderTime.setMinimum(0);
                JFrameMIDIPianoSheetCreator.this.sliderTime.setValue(0);
                JFrameMIDIPianoSheetCreator.this.spinnerSpeed.setEnabled(true);
                JFrameMIDIPianoSheetCreator.this.spinnerSpeed_1.setEnabled(true);
            }
        };
        return midiParserWorker;
    }

    private void initMenuFontFamily() {
    }

    private void initMenuFontSize() {
        JMenu mnFamily = new JMenu("Family");
        this.mnFont.add(mnFamily);
        this.mntmLucidaConsole = new JCheckBoxMenuItem("Lucida Console");
        this.mntmLucidaConsole.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.deselectFontFamilyMenu();
                JFrameMIDIPianoSheetCreator.this.mntmLucidaConsole.setSelected(true);
                JFrameMIDIPianoSheetCreator.this.currentFont = "Lucida Console";
                Font font = new Font("Lucida Console", 0, JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getFont().getSize());
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
            }
        });
        mnFamily.add(this.mntmLucidaConsole);
        this.mntmTahoma = new JCheckBoxMenuItem("Tahoma");
        this.mntmTahoma.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.deselectFontFamilyMenu();
                JFrameMIDIPianoSheetCreator.this.mntmTahoma.setSelected(true);
                JFrameMIDIPianoSheetCreator.this.currentFont = "Tahoma";
                Font font = new Font("Tahoma", 0, JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getFont().getSize());
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
            }
        });
        mnFamily.add(this.mntmTahoma);
        this.mntmVerdana = new JCheckBoxMenuItem("Verdana");
        this.mntmVerdana.setSelected(true);
        this.mntmVerdana.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.deselectFontFamilyMenu();
                JFrameMIDIPianoSheetCreator.this.mntmVerdana.setSelected(true);
                JFrameMIDIPianoSheetCreator.this.currentFont = "Verdana";
                Font font = new Font("Verdana", 0, JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getFont().getSize());
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
            }
        });
        mnFamily.add(this.mntmVerdana);
        this.mntmSegoeUi = new JCheckBoxMenuItem("Segoe UI");
        this.mntmSegoeUi.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.deselectFontFamilyMenu();
                JFrameMIDIPianoSheetCreator.this.mntmSegoeUi.setSelected(true);
                JFrameMIDIPianoSheetCreator.this.currentFont = "Segoe UI";
                Font font = new Font("Segoe UI", 0, JFrameMIDIPianoSheetCreator.this.tpKeyEditor.getFont().getSize());
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
            }
        });
        mnFamily.add(this.mntmSegoeUi);
        JMenu mnSize = new JMenu("Size");
        this.mnFont.add(mnSize);
        this.mntmSize_8 = new JCheckBoxMenuItem("Size 8");
        this.mntmSize_8.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.deselectFontSizeMenu();
                JFrameMIDIPianoSheetCreator.this.mntmSize_8.setSelected(true);
                Font font = new Font(JFrameMIDIPianoSheetCreator.this.currentFont, 0, 8);
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
                JFrameMIDIPianoSheetCreator.this.currentFontSize = 8;
            }
        });
        mnSize.add(this.mntmSize_8);
        this.mntmSize_10 = new JCheckBoxMenuItem("Size 10");
        this.mntmSize_10.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.deselectFontSizeMenu();
                JFrameMIDIPianoSheetCreator.this.mntmSize_10.setSelected(true);
                Font font = new Font(JFrameMIDIPianoSheetCreator.this.currentFont, 0, 10);
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
                JFrameMIDIPianoSheetCreator.this.currentFontSize = 10;
            }
        });
        mnSize.add(this.mntmSize_10);
        this.mntmSize_12 = new JCheckBoxMenuItem("Size 12");
        this.mntmSize_12.setSelected(true);
        this.mntmSize_12.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.deselectFontSizeMenu();
                JFrameMIDIPianoSheetCreator.this.mntmSize_12.setSelected(true);
                Font font = new Font(JFrameMIDIPianoSheetCreator.this.currentFont, 0, 12);
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
                JFrameMIDIPianoSheetCreator.this.currentFontSize = 12;
            }
        });
        mnSize.add(this.mntmSize_12);
        this.mntmSize_14 = new JCheckBoxMenuItem("Size 14");
        this.mntmSize_14.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.deselectFontSizeMenu();
                JFrameMIDIPianoSheetCreator.this.mntmSize_14.setSelected(true);
                Font font = new Font(JFrameMIDIPianoSheetCreator.this.currentFont, 0, 14);
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
                JFrameMIDIPianoSheetCreator.this.currentFontSize = 14;
            }
        });
        mnSize.add(this.mntmSize_14);
        this.mntmSize_16 = new JCheckBoxMenuItem("Size 16");
        this.mntmSize_16.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.deselectFontSizeMenu();
                JFrameMIDIPianoSheetCreator.this.mntmSize_16.setSelected(true);
                Font font = new Font(JFrameMIDIPianoSheetCreator.this.currentFont, 0, 16);
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
                JFrameMIDIPianoSheetCreator.this.currentFontSize = 16;
            }
        });
        mnSize.add(this.mntmSize_16);
        this.mntmSize_18 = new JCheckBoxMenuItem("Size 18");
        this.mntmSize_18.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.deselectFontSizeMenu();
                JFrameMIDIPianoSheetCreator.this.mntmSize_18.setSelected(true);
                Font font = new Font(JFrameMIDIPianoSheetCreator.this.currentFont, 0, 18);
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
                JFrameMIDIPianoSheetCreator.this.currentFontSize = 18;
            }
        });
        mnSize.add(this.mntmSize_18);
        this.mntmSize_24 = new JCheckBoxMenuItem("Size 24");
        this.mntmSize_24.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.deselectFontSizeMenu();
                JFrameMIDIPianoSheetCreator.this.mntmSize_24.setSelected(true);
                Font font = new Font(JFrameMIDIPianoSheetCreator.this.currentFont, 0, 24);
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
                JFrameMIDIPianoSheetCreator.this.currentFontSize = 24;
            }
        });
        mnSize.add(this.mntmSize_24);
        this.mntmSize_36 = new JCheckBoxMenuItem("Size 36");
        this.mntmSize_36.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.deselectFontSizeMenu();
                JFrameMIDIPianoSheetCreator.this.mntmSize_36.setSelected(true);
                Font font = new Font(JFrameMIDIPianoSheetCreator.this.currentFont, 0, 36);
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
                JFrameMIDIPianoSheetCreator.this.currentFontSize = 36;
            }
        });
        mnSize.add(this.mntmSize_36);
        this.mntmSize_48 = new JCheckBoxMenuItem("Size 48");
        this.mntmSize_48.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.deselectFontSizeMenu();
                JFrameMIDIPianoSheetCreator.this.mntmSize_48.setSelected(true);
                Font font = new Font(JFrameMIDIPianoSheetCreator.this.currentFont, 0, 48);
                JFrameMIDIPianoSheetCreator.this.tpKeyEditor.setFont(font);
                JFrameMIDIPianoSheetCreator.this.currentFontSize = 48;
            }
        });
        mnSize.add(this.mntmSize_48);
    }

    private void initMenuInstruments() {
        JMenu mnInstrument = new JMenu("Instrument");
        this.menuBar.add(mnInstrument);
        this.chckbxmntmAcousticGrandPiano = new JCheckBoxMenuItem("Acoustic Grand Piano");
        this.chckbxmntmAcousticGrandPiano.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrameMIDIPianoSheetCreator.this.changeInstrument(0);
                JFrameMIDIPianoSheetCreator.this.chckbxmntmAcousticGrandPiano.setSelected(true);
            }
        });
        mnInstrument.add(this.chckbxmntmAcousticGrandPiano);
        this.chckbxmntmBrightAcousticPiano = new JCheckBoxMenuItem("Bright Acoustic Piano");
        this.chckbxmntmBrightAcousticPiano.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.changeInstrument(1);
                JFrameMIDIPianoSheetCreator.this.chckbxmntmBrightAcousticPiano.setSelected(true);
            }
        });
        mnInstrument.add(this.chckbxmntmBrightAcousticPiano);
        this.chckbxmntmElectricGrandPiano = new JCheckBoxMenuItem("Electric Grand Piano");
        this.chckbxmntmElectricGrandPiano.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.changeInstrument(2);
                JFrameMIDIPianoSheetCreator.this.chckbxmntmElectricGrandPiano.setSelected(true);
            }
        });
        mnInstrument.add(this.chckbxmntmElectricGrandPiano);
        this.chckbxmntmHonkyTonkPiano = new JCheckBoxMenuItem("Honky-tonk Piano");
        this.chckbxmntmHonkyTonkPiano.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.changeInstrument(3);
                JFrameMIDIPianoSheetCreator.this.chckbxmntmHonkyTonkPiano.setSelected(true);
            }
        });
        mnInstrument.add(this.chckbxmntmHonkyTonkPiano);
        this.chckbxmntmElectricPiano1 = new JCheckBoxMenuItem("Electric Piano 1");
        this.chckbxmntmElectricPiano1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.changeInstrument(4);
                JFrameMIDIPianoSheetCreator.this.chckbxmntmElectricPiano1.setSelected(true);
            }
        });
        mnInstrument.add(this.chckbxmntmElectricPiano1);
        this.chckbxmntmElectricPiano2 = new JCheckBoxMenuItem("Electric Piano 2");
        this.chckbxmntmElectricPiano2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.changeInstrument(5);
                JFrameMIDIPianoSheetCreator.this.chckbxmntmElectricPiano2.setSelected(true);
            }
        });
        mnInstrument.add(this.chckbxmntmElectricPiano2);
        this.chckbxmntmHarpsichord = new JCheckBoxMenuItem("Harpsichord");
        this.chckbxmntmHarpsichord.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.changeInstrument(6);
                JFrameMIDIPianoSheetCreator.this.chckbxmntmHarpsichord.setSelected(true);
            }
        });
        mnInstrument.add(this.chckbxmntmHarpsichord);
        this.chckbxmntmClavinet = new JCheckBoxMenuItem("Clavinet");
        this.chckbxmntmClavinet.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.changeInstrument(7);
                JFrameMIDIPianoSheetCreator.this.chckbxmntmClavinet.setSelected(true);
            }
        });
        mnInstrument.add(this.chckbxmntmClavinet);
        this.chckbxmntmUseMidiDefault = new JCheckBoxMenuItem("Use MIDI Default Instruments");
        this.chckbxmntmUseMidiDefault.setSelected(true);
        this.chckbxmntmUseMidiDefault.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMIDIPianoSheetCreator.this.changeInstrument(-1);
                JFrameMIDIPianoSheetCreator.this.chckbxmntmUseMidiDefault.setSelected(true);
            }
        });
        mnInstrument.add(this.chckbxmntmUseMidiDefault);
    }

    private void changeInstrument(int patchNum) {
        this.deselectInstrumentMenu();
        if (this.midiPlayer != null) {
            this.midiPlayer.setInstrument(patchNum);
            this.currentPatchNum = patchNum;
        }
    }

    private void deselectInstrumentMenu() {
        this.chckbxmntmAcousticGrandPiano.setSelected(false);
        this.chckbxmntmBrightAcousticPiano.setSelected(false);
        this.chckbxmntmElectricGrandPiano.setSelected(false);
        this.chckbxmntmUseMidiDefault.setSelected(false);
        this.chckbxmntmHonkyTonkPiano.setSelected(false);
        this.chckbxmntmElectricPiano1.setSelected(false);
        this.chckbxmntmElectricPiano2.setSelected(false);
        this.chckbxmntmHarpsichord.setSelected(false);
        this.chckbxmntmClavinet.setSelected(false);
    }

    private void deselectFontSizeMenu() {
        this.mntmSize_8.setSelected(false);
        this.mntmSize_10.setSelected(false);
        this.mntmSize_12.setSelected(false);
        this.mntmSize_14.setSelected(false);
        this.mntmSize_16.setSelected(false);
        this.mntmSize_18.setSelected(false);
        this.mntmSize_24.setSelected(false);
        this.mntmSize_36.setSelected(false);
        this.mntmSize_48.setSelected(false);
    }

    private void deselectFontFamilyMenu() {
        this.mntmLucidaConsole.setSelected(false);
        this.mntmTahoma.setSelected(false);
        this.mntmVerdana.setSelected(false);
        this.mntmSegoeUi.setSelected(false);
    }
}

