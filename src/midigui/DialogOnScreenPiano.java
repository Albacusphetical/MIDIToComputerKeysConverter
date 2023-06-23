/*
 * Decompiled with CFR 0.150.
 */
package midigui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class DialogOnScreenPiano
extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JLayeredPane layeredPane;
    private JLabel label;
    private JButton btnOne;
    private JButton btnTwo;
    private JButton btnExclamation;

    public DialogOnScreenPiano(final JFrame parent) {
        this.setBounds(100, 100, 570, 300);
        this.setTitle("On-Screen Piano");
        this.setIconImage(new ImageIcon(getClass().getResource("/images/icongrey.png")).getImage());
        WindowFocusListener focusListener = new WindowFocusListener(){

            @Override
            public void windowGainedFocus(WindowEvent arg0) {
                parent.toFront();
            }

            @Override
            public void windowLostFocus(WindowEvent arg0) {
            }
        };
        this.addWindowFocusListener(focusListener);
        this.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        this.setAlwaysOnTop(true);
        this.getContentPane().setLayout(new BorderLayout());
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add((Component)this.contentPanel, "Center");
        this.contentPanel.setLayout(null);
        this.layeredPane = new JLayeredPane();
        this.layeredPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "On-Screen Piano", 4, 2, null, null));
        this.layeredPane.setBounds(10, 48, 534, 174);
        this.contentPanel.add(this.layeredPane);
        this.btnOne = new JButton("1");
        this.btnOne.setBounds(10, 21, 23, 142);
        this.btnOne.setSelected(true);
        this.btnOne.setEnabled(true);
        this.btnOne.setBackground(Color.WHITE);
        this.btnOne.setForeground(Color.WHITE);
        this.btnOne.setFocusable(true);
        this.layeredPane.add(this.btnOne);
        this.btnTwo = new JButton("2");
        this.btnTwo.setBounds(32, 21, 23, 142);
        this.layeredPane.add(this.btnTwo, 0, -1);
        this.btnExclamation = new JButton("!");
        this.btnExclamation.setBounds(21, 22, 23, 97);
        this.layeredPane.add(this.btnExclamation, 1, -1);
        this.label = new JLabel("Current Note");
        this.label.setHorizontalAlignment(0);
        this.label.setFont(new Font("Tahoma", 1, 16));
        this.label.setBounds(10, 11, 534, 26);
        this.contentPanel.add(this.label);
    }
}

