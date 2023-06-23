/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel
 */
package midigui;

import midigui.JFrameMIDIPianoSheetCreator;
import java.awt.EventQueue;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.SystemColor;
import java.awt.Cursor;


public class GUIUpdated {
	/**
	 * @wbp.nonvisual location=-67,109
	 */
//	private static final SubstanceGraphiteLookAndFeel substanceGraphiteLookAndFeel = new SubstanceGraphiteLookAndFeel();
    public static void main(String[] args) {
        GUIUpdated.tryLookAndFeel();
        GUIUpdated.initEventDispatchThread();
    }

    private static void initEventDispatchThread() {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    JFrameMIDIPianoSheetCreator frame = new JFrameMIDIPianoSheetCreator();
                    frame.getContentPane().setEnabled(false);
                    frame.getContentPane().setIgnoreRepaint(true);
                    frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    frame.getContentPane().setForeground(SystemColor.desktop);
                    System.out.println("Program launched. Keep this window open for debug out");
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println();
                    System.out.println("Program launched but something went wrong. See stack trace. Exiting.");
                }
            }
        });
    }

    private static void tryLookAndFeel() {
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
//        	UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.GraphiteSkin");
//        	UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.GraphiteSkin");
//            UIManager.setLookAndFeel((LookAndFeel)new SubstanceGraphiteLookAndFeel());
//            UIManager.setLookAndFeel((LookAndFeel)new SubstanceGraphiteLookAndFeel());
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

