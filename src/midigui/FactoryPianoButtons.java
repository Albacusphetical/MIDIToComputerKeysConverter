/*
 * Decompiled with CFR 0.150.
 */
package midigui;

import java.awt.Color;
import javax.swing.JButton;

public final class FactoryPianoButtons {
    public static JButton createPianoWhiteKey(String arg0) {
        JButton button = new JButton(arg0);
        button.setSelected(true);
        button.setEnabled(true);
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        return button;
    }
}

