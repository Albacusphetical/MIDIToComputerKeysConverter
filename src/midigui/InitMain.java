/* THIS CLASS USES RADIANCE THEMING, RADIANCE COPYRIGHT INFORMATION:
 * Copyright (c) 2005-2023 Radiance Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of the copyright holder nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *

 /
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

