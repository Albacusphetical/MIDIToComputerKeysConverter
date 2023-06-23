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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DialogAbout
extends JDialog {
    private final JPanel contentPanel = new JPanel();

    public DialogAbout(JFrame parent) {
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setResizable(false);
        this.setDefaultCloseOperation(2);
        this.setLocationRelativeTo(parent);
        this.setTitle("About");
        this.setBounds(100, 100, 394, 274);
        this.getContentPane().setLayout(new BorderLayout());
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add((Component)this.contentPanel, "Center");
        this.contentPanel.setLayout(new BoxLayout(this.contentPanel, 3));
        JLabel lblMidiToVirtual = new JLabel("Midi to Computer Keys Converter");
        lblMidiToVirtual.setAlignmentX(0.5f);
        lblMidiToVirtual.setAlignmentY(0.0f);
        this.contentPanel.add(lblMidiToVirtual);
        JLabel lblNewLabel = new JLabel("alpha version 0.8");
        lblNewLabel.setAlignmentX(0.5f);
        lblNewLabel.setAlignmentY(0.0f);
        this.contentPanel.add(lblNewLabel);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/icongrey_small.png"));
        JLabel lblImage = new JLabel(icon);
        lblImage.setAlignmentX(0.5f);
        lblImage.setAlignmentY(0.0f);
        this.contentPanel.add(lblImage);
        Component verticalStrut = Box.createVerticalStrut(20);
        this.contentPanel.add(verticalStrut);
        JLabel lblWrittenByLittle = new JLabel("A simple MIDI to Computer Keyboard Sheet converter");
        lblWrittenByLittle.setAlignmentX(0.5f);
        lblWrittenByLittle.setAlignmentY(0.0f);
        this.contentPanel.add(lblWrittenByLittle);
        JLabel lblForUseIn = new JLabel("For use in Virtual Piano and \"Playable Piano\" Addon for Garry's Mod");
        lblForUseIn.setAlignmentX(0.5f);
        lblForUseIn.setAlignmentY(0.0f);
        this.contentPanel.add(lblForUseIn);
        verticalStrut = Box.createVerticalStrut(20);
        this.contentPanel.add(verticalStrut);
        JLabel lblCodedByLittle = new JLabel("This software is in alpha stage");
        lblCodedByLittle.setAlignmentX(0.5f);
        lblCodedByLittle.setAlignmentY(0.0f);
        this.contentPanel.add(lblCodedByLittle);
        JLabel lblThereMayBe = new JLabel("There may be bugs and/or missing functionality");
        lblThereMayBe.setAlignmentX(0.5f);
        lblThereMayBe.setAlignmentY(0.0f);
        this.contentPanel.add(lblThereMayBe);
        JLabel lblCreatedByLittle = new JLabel("Created by Little Cute Lion, 2014");
        lblCreatedByLittle.setAlignmentX(0.5f);
        lblCreatedByLittle.setAlignmentY(0.0f);
        this.contentPanel.add(lblCreatedByLittle);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(2));
        this.getContentPane().add((Component)buttonPane, "South");
        JButton cancelButton = new JButton("Close");
        cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DialogAbout.this.setVisible(false);
                DialogAbout.this.dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }
}

