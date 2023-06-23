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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class DialogOnScreenKeyboard
extends JDialog {
    private int fontSize = 14;
    private int fontStyle = 1;
    private String fontFamily = "Tahoma";
    private Color clrLowerCase = new Color(0, 153, 0);
    private Color clrUpperCase = new Color(255, 0, 0);
    private Color clrDefaultBackground = new Color(255, 255, 255);
    private Color clrDefaultForeground = new Color(255, 255, 255);
    private final JPanel contentPanel = new JPanel();
    private JLayeredPane layeredPane;
    private JButton btnTilda;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9;
    private JButton btn0;
    private JButton btnTab;
    private JButton btnQ;
    private JButton btnW;
    private JButton btnE;
    private JButton btnR;
    private JButton btnT;
    private JButton btnU;
    private JButton btnI;
    private JButton btnO;
    private JButton btnP;
    private JButton btnY;
    private JButton btnCapsLock;
    private JButton btnA;
    private JButton btnS;
    private JButton btnD;
    private JButton btnF;
    private JButton btnG;
    private JButton btnJ;
    private JButton btnK;
    private JButton btnL;
    private JButton btnH;
    private JButton btnShiftLeft;
    private JButton btnZ;
    private JButton btnX;
    private JButton btnC;
    private JButton btnV;
    private JButton btnB;
    private JButton btnM;
    private JButton btnN;
    private JCheckBox chckbxHighlightLeftShift;
    private JCheckBox chckbxEnableFade;
    private JButton btnMinus;
    private JButton btnEqual;
    private JButton btnBackspace;
    private JButton btnSquareBracketOpen;
    private JButton btnSquareBracketClose;
    private JButton btnBackslash;
    private JButton btnSemicolon;
    private JButton btnApostraphe;
    private JButton btnEnter;
    private JButton btnComma;
    private JButton btnFullStop;
    private JButton btnForwardSlash;
    private JButton btnShiftRight;
    private JButton btnCtrlLeft;
    private JButton btnWinLeft;
    private JButton btnAltLeft;
    private JButton btnSpace;
    private JButton btnAltRight;
    private JButton btnWinRight;
    private JButton btnContext;
    private JButton btnCtrlRight;
    private JCheckBox chckbxHighlightRightShift;
    private JCheckBox chckbxEnableTextColoring;
    private JCheckBox chckbxEnableBackgroundHighlighting;
    private JLabel lblCurrentNote;
    private JCheckBox chckbxAlwaysOnTop;

    public DialogOnScreenKeyboard(final JFrame parent) {
        this.setResizable(false);
        this.setBounds(100, 100, 543, 306);
        this.setTitle("On-Screen Keyboard");
        this.setIconImage(new ImageIcon(getClass().getResource("/images/icongrey.png")).getImage());
        WindowFocusListener focusListener = new WindowFocusListener(){

            @Override
            public void windowGainedFocus(WindowEvent arg0) {
                if (!DialogOnScreenKeyboard.this.chckbxAlwaysOnTop.isEnabled()) {
                    parent.toFront();
                }
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
        this.layeredPane.setBorder(new TitledBorder(null, "On-Screen Keyboard", 4, 2, null, null));
        this.layeredPane.setBounds(8, 48, 520, 219);
        this.contentPanel.add(this.layeredPane);
        this.btnTilda = new JButton("~");
        this.btnTilda.setBounds(10, 21, 35, 35);
        this.layeredPane.add(this.btnTilda);
        this.btnTilda.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.clrDefaultBackground = this.btnTilda.getBackground();
        this.clrDefaultForeground = this.btnTilda.getForeground();
        this.btn1 = new JButton("1");
        this.btn1.setBounds(43, 21, 35, 35);
        this.layeredPane.add(this.btn1);
        this.btn1.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btn2 = new JButton("2");
        this.btn2.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btn2.setBounds(76, 21, 35, 35);
        this.layeredPane.add(this.btn2);
        this.btn3 = new JButton("3");
        this.btn3.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btn3.setBounds(109, 21, 35, 35);
        this.layeredPane.add(this.btn3);
        this.btn4 = new JButton("4");
        this.btn4.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btn4.setBounds(142, 21, 35, 35);
        this.layeredPane.add(this.btn4);
        this.btn5 = new JButton("5");
        this.btn5.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btn5.setBounds(175, 21, 35, 35);
        this.layeredPane.add(this.btn5);
        this.btn6 = new JButton("6");
        this.btn6.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btn6.setBounds(208, 21, 35, 35);
        this.layeredPane.add(this.btn6);
        this.btn7 = new JButton("7");
        this.btn7.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btn7.setBounds(241, 21, 35, 35);
        this.layeredPane.add(this.btn7);
        this.btn8 = new JButton("8");
        this.btn8.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btn8.setBounds(274, 21, 35, 35);
        this.layeredPane.add(this.btn8);
        this.btn9 = new JButton("9");
        this.btn9.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btn9.setBounds(307, 21, 35, 35);
        this.layeredPane.add(this.btn9);
        this.btn0 = new JButton("0");
        this.btn0.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btn0.setBounds(340, 21, 35, 35);
        this.layeredPane.add(this.btn0);
        this.btnTab = new JButton("Tab");
        this.btnTab.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnTab.setBounds(10, 55, 53, 35);
        this.layeredPane.add(this.btnTab);
        this.btnQ = new JButton("Q");
        this.btnQ.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnQ.setBounds(62, 55, 35, 35);
        this.layeredPane.add(this.btnQ);
        this.btnW = new JButton("W");
        this.btnW.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnW.setBounds(95, 55, 37, 35);
        this.layeredPane.add(this.btnW);
        this.btnE = new JButton("E");
        this.btnE.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnE.setBounds(130, 55, 35, 35);
        this.layeredPane.add(this.btnE);
        this.btnR = new JButton("R");
        this.btnR.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnR.setBounds(163, 55, 35, 35);
        this.layeredPane.add(this.btnR);
        this.btnT = new JButton("T");
        this.btnT.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnT.setBounds(197, 55, 35, 35);
        this.layeredPane.add(this.btnT);
        this.btnU = new JButton("U");
        this.btnU.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnU.setBounds(263, 55, 35, 35);
        this.layeredPane.add(this.btnU);
        this.btnI = new JButton("I");
        this.btnI.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnI.setBounds(296, 55, 35, 35);
        this.layeredPane.add(this.btnI);
        this.btnO = new JButton("O");
        this.btnO.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnO.setBounds(329, 55, 35, 35);
        this.layeredPane.add(this.btnO);
        this.btnP = new JButton("P");
        this.btnP.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnP.setBounds(362, 55, 35, 35);
        this.layeredPane.add(this.btnP);
        this.btnY = new JButton("Y");
        this.btnY.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnY.setBounds(230, 55, 35, 35);
        this.layeredPane.add(this.btnY);
        this.btnCapsLock = new JButton("Caps");
        this.btnCapsLock.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnCapsLock.setBounds(10, 88, 70, 35);
        this.layeredPane.add(this.btnCapsLock);
        this.btnA = new JButton("A");
        this.btnA.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnA.setBounds(78, 88, 35, 35);
        this.layeredPane.add(this.btnA);
        this.btnS = new JButton("S");
        this.btnS.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnS.setBounds(111, 88, 37, 35);
        this.layeredPane.add(this.btnS);
        this.btnD = new JButton("D");
        this.btnD.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnD.setBounds(146, 88, 35, 35);
        this.layeredPane.add(this.btnD);
        this.btnF = new JButton("F");
        this.btnF.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnF.setBounds(179, 88, 35, 35);
        this.layeredPane.add(this.btnF);
        this.btnG = new JButton("G");
        this.btnG.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnG.setBounds(212, 88, 35, 35);
        this.layeredPane.add(this.btnG);
        this.btnJ = new JButton("J");
        this.btnJ.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnJ.setBounds(278, 88, 35, 35);
        this.layeredPane.add(this.btnJ);
        this.btnK = new JButton("K");
        this.btnK.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnK.setBounds(311, 88, 35, 35);
        this.layeredPane.add(this.btnK);
        this.btnL = new JButton("L");
        this.btnL.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnL.setBounds(344, 88, 35, 35);
        this.layeredPane.add(this.btnL);
        this.btnH = new JButton("H");
        this.btnH.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnH.setBounds(245, 88, 35, 35);
        this.layeredPane.add(this.btnH);
        this.btnShiftLeft = new JButton("Shift");
        this.btnShiftLeft.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnShiftLeft.setBounds(10, 122, 87, 35);
        this.layeredPane.add(this.btnShiftLeft);
        this.btnZ = new JButton("Z");
        this.btnZ.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnZ.setBounds(95, 122, 35, 35);
        this.layeredPane.add(this.btnZ);
        this.btnX = new JButton("X");
        this.btnX.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnX.setBounds(128, 122, 35, 35);
        this.layeredPane.add(this.btnX);
        this.btnC = new JButton("C");
        this.btnC.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnC.setBounds(161, 122, 35, 35);
        this.layeredPane.add(this.btnC);
        this.btnV = new JButton("V");
        this.btnV.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnV.setBounds(194, 122, 35, 35);
        this.layeredPane.add(this.btnV);
        this.btnB = new JButton("B");
        this.btnB.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnB.setBounds(227, 122, 35, 35);
        this.layeredPane.add(this.btnB);
        this.btnM = new JButton("M");
        this.btnM.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnM.setBounds(293, 122, 35, 35);
        this.layeredPane.add(this.btnM);
        this.btnN = new JButton("N");
        this.btnN.setFont(new Font(this.fontFamily, this.fontStyle, this.fontSize));
        this.btnN.setBounds(260, 122, 35, 35);
        this.layeredPane.add(this.btnN);
        this.contentPanel.setEnabled(false);
        this.clearHighlights();
        this.chckbxHighlightLeftShift = new JCheckBox("Highlight Left Shift");
        this.chckbxHighlightLeftShift.setSelected(true);
        this.chckbxHighlightLeftShift.setBounds(10, 164, 135, 23);
        this.layeredPane.add(this.chckbxHighlightLeftShift);
        this.chckbxEnableFade = new JCheckBox("Enable Fade History");
        this.chckbxEnableFade.setSelected(true);
        this.chckbxEnableFade.setBounds(374, 164, 135, 23);
        this.layeredPane.add(this.chckbxEnableFade);
        this.btnMinus = new JButton("-");
        this.btnMinus.setFont(new Font("Tahoma", 1, 14));
        this.btnMinus.setBounds(373, 21, 35, 35);
        this.layeredPane.add(this.btnMinus);
        this.btnEqual = new JButton("=");
        this.btnEqual.setFont(new Font("Tahoma", 1, 14));
        this.btnEqual.setBounds(406, 21, 35, 35);
        this.layeredPane.add(this.btnEqual);
        this.btnBackspace = new JButton("Back");
        this.btnBackspace.setFont(new Font("Tahoma", 1, 14));
        this.btnBackspace.setBounds(439, 21, 70, 35);
        this.layeredPane.add(this.btnBackspace);
        this.btnSquareBracketOpen = new JButton("[");
        this.btnSquareBracketOpen.setFont(new Font("Tahoma", 1, 14));
        this.btnSquareBracketOpen.setBounds(395, 55, 35, 35);
        this.layeredPane.add(this.btnSquareBracketOpen);
        this.btnSquareBracketClose = new JButton("]");
        this.btnSquareBracketClose.setFont(new Font("Tahoma", 1, 14));
        this.btnSquareBracketClose.setBounds(428, 55, 35, 35);
        this.layeredPane.add(this.btnSquareBracketClose);
        this.btnBackslash = new JButton("\\");
        this.btnBackslash.setFont(new Font("Tahoma", 1, 14));
        this.btnBackslash.setBounds(461, 55, 48, 35);
        this.layeredPane.add(this.btnBackslash);
        this.btnSemicolon = new JButton(";");
        this.btnSemicolon.setFont(new Font("Tahoma", 1, 14));
        this.btnSemicolon.setBounds(377, 88, 35, 35);
        this.layeredPane.add(this.btnSemicolon);
        this.btnApostraphe = new JButton("'");
        this.btnApostraphe.setFont(new Font("Tahoma", 1, 14));
        this.btnApostraphe.setBounds(410, 88, 35, 35);
        this.layeredPane.add(this.btnApostraphe);
        this.btnEnter = new JButton("Enter");
        this.btnEnter.setFont(new Font("Tahoma", 1, 14));
        this.btnEnter.setBounds(443, 88, 66, 35);
        this.layeredPane.add(this.btnEnter);
        this.btnComma = new JButton(",");
        this.btnComma.setFont(new Font("Tahoma", 1, 14));
        this.btnComma.setBounds(326, 122, 35, 35);
        this.layeredPane.add(this.btnComma);
        this.btnFullStop = new JButton(".");
        this.btnFullStop.setFont(new Font("Tahoma", 1, 14));
        this.btnFullStop.setBounds(359, 122, 35, 35);
        this.layeredPane.add(this.btnFullStop);
        this.btnForwardSlash = new JButton("/");
        this.btnForwardSlash.setFont(new Font("Tahoma", 1, 14));
        this.btnForwardSlash.setBounds(392, 122, 35, 35);
        this.layeredPane.add(this.btnForwardSlash);
        this.btnShiftRight = new JButton("Shift");
        this.btnShiftRight.setFont(new Font("Tahoma", 1, 14));
        this.btnShiftRight.setBounds(425, 122, 84, 35);
        this.layeredPane.add(this.btnShiftRight);
        this.chckbxHighlightRightShift = new JCheckBox("Highlight Right Shift");
        this.chckbxHighlightRightShift.setBounds(10, 189, 145, 23);
        this.layeredPane.add(this.chckbxHighlightRightShift);
        this.chckbxEnableTextColoring = new JCheckBox("Enable Text Coloring");
        this.chckbxEnableTextColoring.setSelected(true);
        this.chckbxEnableTextColoring.setBounds(164, 164, 167, 23);
        this.layeredPane.add(this.chckbxEnableTextColoring);
        this.chckbxEnableBackgroundHighlighting = new JCheckBox("Enable Background Highlighting");
        this.chckbxEnableBackgroundHighlighting.setSelected(true);
        this.chckbxEnableBackgroundHighlighting.setBounds(164, 189, 213, 23);
        this.layeredPane.add(this.chckbxEnableBackgroundHighlighting);
        this.chckbxAlwaysOnTop = new JCheckBox("Always on Top");
        this.chckbxAlwaysOnTop.setSelected(true);
        this.chckbxAlwaysOnTop.setBounds(374, 190, 135, 23);
        this.chckbxAlwaysOnTop.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (DialogOnScreenKeyboard.this.chckbxAlwaysOnTop.isSelected()) {
                    DialogOnScreenKeyboard.this.setAlwaysOnTop(true);
                } else {
                    DialogOnScreenKeyboard.this.setAlwaysOnTop(false);
                }
            }
        });
        this.chckbxAlwaysOnTop.setEnabled(true);
        this.layeredPane.add(this.chckbxAlwaysOnTop);
        this.lblCurrentNote = new JLabel("Current Note");
        this.lblCurrentNote.setFont(new Font("Tahoma", 1, 16));
        this.lblCurrentNote.setHorizontalAlignment(0);
        this.lblCurrentNote.setBounds(8, 11, 518, 26);
        this.contentPanel.add(this.lblCurrentNote);
    }

    public void highlightKeys(String keysToHighlight) {
        System.out.println(keysToHighlight);
        this.updateCurrentNoteLabel(keysToHighlight);
        this.clearHighlights();
        char[] charKeysToHighlight = keysToHighlight.toCharArray();
        Component[] components = this.layeredPane.getComponents();
        char[] arrc = charKeysToHighlight;
        int n = charKeysToHighlight.length;
        for (int i = 0; i < n; ++i) {
            char c = arrc[i];
            if (c == '[' || c == ']') continue;
            Component[] arrcomponent = components;
            int n2 = components.length;
            for (int j = 0; j < n2; ++j) {
                Component component = arrcomponent[j];
                if (!(component instanceof JButton)) continue;
                String stringCompare = ((JButton)component).getText();
                String tmp = Character.toString(c);
                String tmpLower = stringCompare.toLowerCase();
                String tmpUpper = null;
                if (!Character.isDigit(c)) {
                    tmpUpper = stringCompare.toUpperCase();
                }
                if (tmp.equals(tmpLower)) {
                    if (this.chckbxEnableTextColoring.isSelected()) {
                        ((JButton)component).setForeground(this.clrLowerCase);
                    }
                    if (this.chckbxEnableBackgroundHighlighting.isSelected()) {
                        ((JButton)component).setBackground(new Color(153, 255, 153));
                    }
                    if (this.chckbxEnableFade.isSelected()) {
                        ((JButton)component).setSelected(true);
                    }
                    ((JButton)component).setEnabled(true);
                    ((JButton)component).setText(tmpLower);
                }
                if (tmp.equals(tmpUpper)) {
                    if (this.chckbxEnableTextColoring.isSelected()) {
                        ((JButton)component).setForeground(this.clrUpperCase);
                    }
                    if (this.chckbxEnableBackgroundHighlighting.isSelected()) {
                        ((JButton)component).setBackground(new Color(255, 153, 153));
                    }
                    if (this.chckbxEnableFade.isSelected()) {
                        ((JButton)component).setSelected(true);
                    }
                    ((JButton)component).setEnabled(true);
                    ((JButton)component).setText(tmpUpper);
                    if (this.chckbxHighlightLeftShift.isSelected()) {
                        if (this.chckbxEnableTextColoring.isSelected()) {
                            this.btnShiftLeft.setForeground(this.clrUpperCase);
                        }
                        if (this.chckbxEnableBackgroundHighlighting.isSelected()) {
                            this.btnShiftLeft.setBackground(new Color(255, 153, 153));
                        }
                        if (this.chckbxEnableFade.isSelected()) {
                            this.btnShiftLeft.setEnabled(true);
                        }
                    }
                    if (this.chckbxHighlightRightShift.isSelected()) {
                        if (this.chckbxEnableTextColoring.isSelected()) {
                            this.btnShiftRight.setForeground(this.clrUpperCase);
                        }
                        if (this.chckbxEnableBackgroundHighlighting.isSelected()) {
                            this.btnShiftRight.setBackground(new Color(255, 153, 153));
                        }
                        if (this.chckbxEnableFade.isSelected()) {
                            this.btnShiftRight.setEnabled(true);
                        }
                    }
                }
                if (!Character.isDigit(c) || c != stringCompare.charAt(0)) continue;
                if (this.chckbxEnableTextColoring.isSelected()) {
                    ((JButton)component).setForeground(this.clrLowerCase);
                }
                if (this.chckbxEnableBackgroundHighlighting.isSelected()) {
                    ((JButton)component).setBackground(new Color(153, 255, 153));
                }
                if (this.chckbxEnableFade.isSelected()) {
                    ((JButton)component).setSelected(true);
                }
                ((JButton)component).setEnabled(true);
                ((JButton)component).setText(stringCompare);
            }
        }
    }

    private void updateCurrentNoteLabel(String currentNote) {
        this.lblCurrentNote.setText(currentNote);
    }

    private void clearHighlights() {
        Component[] components;
        Component[] arrcomponent = components = this.layeredPane.getComponents();
        int n = components.length;
        for (int i = 0; i < n; ++i) {
            Component component = arrcomponent[i];
            if (!(component instanceof JButton)) continue;
            ((JButton)component).setForeground(this.clrDefaultForeground);
            ((JButton)component).setBackground(this.clrDefaultBackground);
            ((JButton)component).setSelected(false);
            ((JButton)component).setEnabled(true);
            ((JButton)component).setFocusable(false);
            if (((JButton)component).getText().length() != 1) continue;
            ((JButton)component).setText(((JButton)component).getText().toLowerCase());
        }
    }

    private void toggleComponents(JLayeredPane container, boolean enable) {
        Component[] components;
        Component[] arrcomponent = components = container.getComponents();
        int n = components.length;
        for (int i = 0; i < n; ++i) {
            Component component = arrcomponent[i];
            component.setEnabled(enable);
        }
    }

    public void setText(String parsedData) {
    }
}

