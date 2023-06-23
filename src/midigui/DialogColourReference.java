/*
 * Decompiled with CFR 0.150.
 */
package midigui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DialogColourReference
extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JCheckBox chckbxAlwaysOnTop;

    public DialogColourReference(final JFrame parent) {
        this.setResizable(false);
        this.setBounds(100, 100, 264, 435);
        WindowFocusListener focusListener = new WindowFocusListener(){

            @Override
            public void windowGainedFocus(WindowEvent arg0) {
                parent.setVisible(true);
                if (!DialogColourReference.this.chckbxAlwaysOnTop.isEnabled()) {
                    parent.toFront();
                }
            }

            @Override
            public void windowLostFocus(WindowEvent arg0) {
            }
        };
        this.addWindowFocusListener(focusListener);
        this.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        this.getContentPane().setLayout(new BorderLayout());
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setAlwaysOnTop(true);
        this.setTitle("Colour Reference");
        this.setIconImage(new ImageIcon(getClass().getResource("/images/icongrey.png")).getImage());
        this.getContentPane().add((Component)this.contentPanel, "Center");
        this.contentPanel.setLayout(new BoxLayout(this.contentPanel, 3));
        JLabel lblColourNoteValueReference = new JLabel("Colour Note Value Reference");
        lblColourNoteValueReference.setFont(new Font("Tahoma", 1, 11));
        lblColourNoteValueReference.setAlignmentX(0.5f);
        lblColourNoteValueReference.setAlignmentY(0.0f);
        this.contentPanel.add(lblColourNoteValueReference);
        ImageIcon icon = new ImageIcon("images/icongrey_small.png");
        JLabel lblRecogniseHowLong = new JLabel("Recognise how long a note lasts by Colour!");
        lblRecogniseHowLong.setFont(new Font("Tahoma", 0, 11));
        lblRecogniseHowLong.setAlignmentX(0.5f);
        lblRecogniseHowLong.setAlignmentY(0.0f);
        this.contentPanel.add(lblRecogniseHowLong);
        JLabel lblImage = new JLabel(icon);
        lblImage.setAlignmentX(0.5f);
        lblImage.setAlignmentY(0.0f);
        this.contentPanel.add(lblImage);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(1, null, null), "", 4, 2, null, null), "Colour Keys", 4, 2, null, null));
        this.contentPanel.add(layeredPane);
        JLabel label = new JLabel("octupleNote");
        label.setForeground(new Color(0, 100, 0));
        label.setBounds(10, 23, 95, 14);
        layeredPane.add(label);
        label = new JLabel("quadrupleNote");
        label.setForeground(new Color(0, 204, 0));
        label.setBounds(10, 48, 95, 14);
        layeredPane.add(label);
        label = new JLabel("wholeNote");
        label.setForeground(new Color(51, 255, 51));
        label.setBounds(10, 73, 95, 14);
        layeredPane.add(label);
        label = new JLabel("halfNote");
        label.setForeground(new Color(128, 255, 0));
        label.setBounds(10, 98, 100, 14);
        layeredPane.add(label);
        label = new JLabel("quarterNote");
        label.setForeground(new Color(204, 204, 0));
        label.setBounds(10, 123, 95, 14);
        layeredPane.add(label);
        label = new JLabel("eighthNote");
        label.setForeground(new Color(255, 128, 0));
        label.setBounds(10, 148, 95, 14);
        layeredPane.add(label);
        label = new JLabel("sixteenthNote");
        label.setForeground(new Color(255, 151, 151));
        label.setBounds(10, 173, 95, 14);
        layeredPane.add(label);
        label = new JLabel("thirtySecondNote");
        label.setForeground(Color.RED);
        label.setBounds(10, 198, 95, 14);
        layeredPane.add(label);
        label = new JLabel("sixtyFourthNote");
        label.setForeground(new Color(153, 0, 0));
        label.setBounds(10, 223, 158, 14);
        layeredPane.add(label);
        label = new JLabel("hundredTwentyEighthNote");
        label.setForeground(new Color(153, 0, 0));
        label.setBounds(10, 248, 158, 14);
        layeredPane.add(label);
        label = new JLabel("twoHundredFiftySixthNote");
        label.setForeground(new Color(153, 0, 0));
        label.setBounds(10, 273, 158, 14);
        layeredPane.add(label);
        this.chckbxAlwaysOnTop = new JCheckBox("Always on Top");
        this.chckbxAlwaysOnTop.setAlignmentX(0.5f);
        this.chckbxAlwaysOnTop.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (DialogColourReference.this.chckbxAlwaysOnTop.isSelected()) {
                    DialogColourReference.this.setAlwaysOnTop(true);
                } else {
                    DialogColourReference.this.setAlwaysOnTop(false);
                }
            }
        });
        this.chckbxAlwaysOnTop.setSelected(true);
        this.chckbxAlwaysOnTop.setFocusPainted(false);
        this.contentPanel.add(this.chckbxAlwaysOnTop);
    }
}

