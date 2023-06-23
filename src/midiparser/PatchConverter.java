/*
 * Decompiled with CFR 0.150.
 */
package midiparser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class PatchConverter {
    private static ArrayList<String> patchName = new ArrayList();

    public PatchConverter() {
        InputStream input = this.getClass().getResourceAsStream("/data/patchNames.csv");
        InputStream in = this.getClass().getResourceAsStream("/data/patchNames.csv");
        System.out.println("resource: " + this.getClass().getResourceAsStream("/data/patchNames.csv").toString());
        Scanner sc = new Scanner(in).useDelimiter(",");
        while (sc.hasNextLine()) {
            int patchNum = Integer.parseInt(sc.next());
            String instrumentName = sc.nextLine();
            instrumentName = instrumentName.replace(",", "");
            patchName.add(patchNum, instrumentName);
        }
        sc.close();
    }

    public String getPatchName(int patchNum) {
        String returnValue = null;
        returnValue = patchNum < 0 || patchNum > patchName.size() - 1 ? "Unknown Instrument" : patchName.get(patchNum);
        return returnValue;
    }
}

