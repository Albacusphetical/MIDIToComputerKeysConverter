/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel
 */
package midigui;

import midigui.JFrameMIDIPianoSheetCreator;
import org.pushingpixels.radiance.theming.api.skin.RadianceGraphiteLookAndFeel;

import java.awt.EventQueue;
import javax.swing.*;

public class InitMain {
    public static void main(String[] args) {
        InitMain.tryLookAndFeel();
        InitMain.initEventDispatchThread();
    }

    private static void initEventDispatchThread() {
        EventQueue.invokeLater(() -> {
            try {
                JFrameMIDIPianoSheetCreator frame = new JFrameMIDIPianoSheetCreator();
                System.out.println("Program launched. Keep this window open for debug out");
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println();
                System.out.println("Program launched but something went wrong. See stack trace. Exiting.");
            }
        });
    }

    private static void tryLookAndFeel() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if (!"Nimbus".equals(info.getName())) continue;
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e1) {
                e1.printStackTrace();
            }
            try {
                UIManager.setLookAndFeel(new RadianceGraphiteLookAndFeel());
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        });

    }
}

