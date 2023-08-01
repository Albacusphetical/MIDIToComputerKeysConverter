/*
 * Decompiled with CFR 0.150.
 */
package midigui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DialogPreferences
extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JRadioButton rdbtnAutoTimeSig;
    private JRadioButton rdbtnCustomTimeSig;
    private JTextField tfTop;
    private JTextField tfBottom;
    private JLabel lblTimeSignatureIs;
    private JLayeredPane lpNewLinePlacement;
    private JRadioButton rdbtnMeasureBar;
    private JRadioButton rdbtnNoMeasure;
    private JTextField tfMeasure;
    private JLabel lblANewLine;
    private JLayeredPane lpAdvancedImportOptions;
    private JRadioButton rdbtnUseAdvancedImport;
    private JRadioButton rdbtnUseDefaultSetting;
    private JLabel lblMeasuresBars;
    private JLabel lblDivider;
    private JSeparator separator;

    public DialogPreferences(JFrame parent) {
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setResizable(false);
        this.setDefaultCloseOperation(2);
        this.setLocationRelativeTo(parent);
        this.setTitle("Import Preferences");
        this.setBounds(100, 100, 463, 348);
        this.getContentPane().setLayout(new BorderLayout());
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add((Component)this.contentPanel, "Center");
        this.contentPanel.setLayout(null);
        ButtonGroup btngrpTimeSig = new ButtonGroup();
        this.lpNewLinePlacement = new JLayeredPane();
        this.lpNewLinePlacement.setBounds(10, 100, 438, 182);
        this.lpNewLinePlacement.setBorder(new TitledBorder(new EtchedBorder(1, null, null), "New Line Placement", 4, 2, null, null));
        this.lpNewLinePlacement.setEnabled(false);
        this.contentPanel.add(this.lpNewLinePlacement);
        this.rdbtnNoMeasure = new JRadioButton("Do not add new lines");
        this.rdbtnNoMeasure.setEnabled(false);
        this.rdbtnNoMeasure.setBounds(6, 67, 143, 23);
        this.lpNewLinePlacement.add(this.rdbtnNoMeasure);
        this.rdbtnMeasureBar = new JRadioButton("Place a new line every: ");
        this.rdbtnMeasureBar.setEnabled(false);
        this.rdbtnMeasureBar.setSelected(true);
        this.rdbtnMeasureBar.setBounds(6, 41, 143, 23);
        this.lpNewLinePlacement.add(this.rdbtnMeasureBar);
        ButtonGroup btngrpMeasure = new ButtonGroup();
        btngrpMeasure.add(this.rdbtnMeasureBar);
        btngrpMeasure.add(this.rdbtnNoMeasure);
        this.tfMeasure = new JTextField();
        this.tfMeasure.setEnabled(false);
        this.tfMeasure.setText("1");
        this.tfMeasure.setBounds(149, 42, 21, 20);
        this.lpNewLinePlacement.add(this.tfMeasure);
        this.tfMeasure.setColumns(10);
        this.tfMeasure.getText();
        this.rdbtnMeasureBar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogPreferences.this.tfMeasure.setEnabled(true);
            }
        });
        this.rdbtnNoMeasure.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogPreferences.this.tfMeasure.setEnabled(false);
            }
        });
        this.lblANewLine = new JLabel("<html><i>A new line is calculated with relation to the time signature's measure / bar</i><html>");
        this.lblANewLine.setEnabled(false);
        this.lblANewLine.setBounds(10, 11, 463, 34);
        this.lpNewLinePlacement.add(this.lblANewLine);
        this.lblMeasuresBars = new JLabel("measures / bars");
        this.lblMeasuresBars.setEnabled(false);
        this.lblMeasuresBars.setBounds(180, 45, 89, 14);
        this.lpNewLinePlacement.add(this.lblMeasuresBars);
        this.separator = new JSeparator();
        this.separator.setBounds(10, 97, 422, 7);
        this.lpNewLinePlacement.add(this.separator);
        this.lblTimeSignatureIs = new JLabel("<html><i>Time Signature is used to provide easier reading by placing new lines per measure/bar</i><html>");
        this.lblTimeSignatureIs.setBounds(10, 97, 463, 28);
        this.lpNewLinePlacement.add(this.lblTimeSignatureIs);
        this.lblTimeSignatureIs.setEnabled(false);
        this.rdbtnAutoTimeSig = new JRadioButton("Auto detect Time Signature");
        this.rdbtnAutoTimeSig.setBounds(6, 121, 178, 23);
        this.lpNewLinePlacement.add(this.rdbtnAutoTimeSig);
        this.rdbtnAutoTimeSig.setSelected(true);
        btngrpTimeSig.add(this.rdbtnAutoTimeSig);
        this.rdbtnAutoTimeSig.setEnabled(false);
        this.rdbtnCustomTimeSig = new JRadioButton("Set up a custom Time Signature :");
        this.rdbtnCustomTimeSig.setBounds(6, 147, 190, 23);
        this.lpNewLinePlacement.add(this.rdbtnCustomTimeSig);
        btngrpTimeSig.add(this.rdbtnCustomTimeSig);
        this.rdbtnCustomTimeSig.setEnabled(false);
        this.tfTop = new JTextField();
        this.tfTop.setBounds(202, 148, 28, 20);
        this.lpNewLinePlacement.add(this.tfTop);
        this.tfTop.setText("4");
        this.tfTop.setColumns(10);
        this.tfTop.setEnabled(false);
        this.tfBottom = new JTextField();
        this.tfBottom.setBounds(250, 148, 28, 20);
        this.lpNewLinePlacement.add(this.tfBottom);
        this.tfBottom.setText("4");
        this.tfBottom.setColumns(10);
        this.tfBottom.setEnabled(false);
        this.lblDivider = new JLabel("/");
        this.lblDivider.setBounds(240, 151, 11, 14);
        this.lpNewLinePlacement.add(this.lblDivider);
        this.lblDivider.setEnabled(false);
        this.rdbtnCustomTimeSig.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogPreferences.this.tfTop.setEnabled(true);
                DialogPreferences.this.tfBottom.setEnabled(true);
            }
        });
        this.rdbtnAutoTimeSig.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogPreferences.this.tfTop.setEnabled(false);
                DialogPreferences.this.tfBottom.setEnabled(false);
            }
        });
        this.lpAdvancedImportOptions = new JLayeredPane();
        this.lpAdvancedImportOptions.setBorder(new TitledBorder(new EtchedBorder(1, null, null), "MIDI Import Options", 4, 2, null, null));
        this.lpAdvancedImportOptions.setBounds(10, 11, 438, 78);
        this.contentPanel.add(this.lpAdvancedImportOptions);
        this.rdbtnUseAdvancedImport = new JRadioButton("Always Display Advanced Import Options when Opening a MIDI File");
        this.rdbtnUseAdvancedImport.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DialogPreferences.this.toggleEnableTimeSig(false);
                DialogPreferences.this.toggleEnableNewLinePlacement(false);
                DialogPreferences.this.tfTop.setEnabled(false);
                DialogPreferences.this.tfBottom.setEnabled(false);
                DialogPreferences.this.rdbtnAutoTimeSig.setSelected(true);
                DialogPreferences.this.rdbtnMeasureBar.setSelected(true);
                DialogPreferences.this.tfMeasure.setEnabled(false);
            }
        });
        this.rdbtnUseAdvancedImport.setBounds(6, 19, 426, 23);
        this.lpAdvancedImportOptions.add(this.rdbtnUseAdvancedImport);
        this.rdbtnUseDefaultSetting = new JRadioButton("Use Default Settings Below when Opening a MIDI File");
        this.rdbtnUseDefaultSetting.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DialogPreferences.this.toggleEnableNewLinePlacement(true);
            }
        });
        this.rdbtnUseDefaultSetting.setBounds(6, 45, 426, 23);
        this.lpAdvancedImportOptions.add(this.rdbtnUseDefaultSetting);
        ButtonGroup btngrpSettingUse = new ButtonGroup();
        btngrpSettingUse.add(this.rdbtnUseAdvancedImport);
        btngrpSettingUse.add(this.rdbtnUseDefaultSetting);
        this.rdbtnUseAdvancedImport.setSelected(true);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(2));
        this.getContentPane().add((Component)buttonPane, "South");
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogPreferences.this.setVisible(false);
                DialogPreferences.this.dispose();
                JFrameMIDIPianoSheetCreator.spinnerMeasuresPerLine.setValue(getMeasures());
                JFrameMIDIPianoSheetCreator.midiParser.setMeasurePerLine(getMeasures());
                JFrameMIDIPianoSheetCreator.midiParser.reparse(JFrameMIDIPianoSheetCreator.midiParser.getTmMidiParsedData(), (Integer) JFrameMIDIPianoSheetCreator.spinnerTranspose.getValue());
                JFrameMIDIPianoSheetCreator.tpKeyEditor.setText(JFrameMIDIPianoSheetCreator.midiParser.getParsedData());
                JFrameMIDIPianoSheetCreator.tpKeyEditor.setStyledDocument(JFrameMIDIPianoSheetCreator.noteColourConverter.ColouriseNotes());
                JFrameMIDIPianoSheetCreator.tpKeyEditor.setCaretPosition(0);
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        this.getRootPane().setDefaultButton(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DialogPreferences.this.setVisible(false);
                DialogPreferences.this.dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setResizable(false);
        this.setDefaultCloseOperation(2);
        this.setLocationRelativeTo(parent);
    }

    private void toggleEnableTimeSig(boolean value) {
        this.rdbtnAutoTimeSig.setEnabled(value);
        this.rdbtnCustomTimeSig.setEnabled(value);
        this.lblTimeSignatureIs.setEnabled(value);
        this.lblDivider.setEnabled(value);
    }

    private void toggleEnableNewLinePlacement(boolean value) {
        this.lpNewLinePlacement.setEnabled(value);
        this.rdbtnMeasureBar.setEnabled(value);
        this.rdbtnNoMeasure.setEnabled(value);
        this.tfMeasure.setEnabled(value);
        this.lblANewLine.setEnabled(value);
        this.lblMeasuresBars.setEnabled(value);
    }

    public boolean isCustomMeasure() {
        return this.rdbtnMeasureBar.isSelected();
    }

    public int getMeasures() {
        int returnValue = 0;
        returnValue = !this.rdbtnMeasureBar.isSelected() ? 0 : Integer.valueOf(this.tfMeasure.getText());
        return returnValue;
    }

    public boolean isAdvancedTrackImport() {
        return this.rdbtnUseAdvancedImport.isSelected();
    }
}

