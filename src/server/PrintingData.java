package server;

import java.io.File;

/**
 * Created by vvrud on 18.09.16.
 *
 * @author VVRud
 *         This class stores all the printing options.
 */
class PrintingData {

    private static File file;

    private static float printingSpeed;
    private static String printingMode;
    private static float printingIntensity;

    static float getPrintingSpeed() {
        return printingSpeed;
    }

    static void setPrintingSpeed(float printingSpeed) {
        PrintingData.printingSpeed = printingSpeed;
    }

    static String getPrintingMode() {
        return printingMode;
    }

    static void setPrintingMode(String printingMode) {
        PrintingData.printingMode = printingMode;
    }

    static float getPrintingIntensity() {
        return printingIntensity;
    }

    static void setPrintingIntensity(float printingIntensity) {
        PrintingData.printingIntensity = printingIntensity;
    }

    static File getFile() {
        return file;
    }

    static void setFile(File file) {
        PrintingData.file = file;
    }


}