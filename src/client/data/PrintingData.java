package client.data;

import java.io.File;
import java.util.HashMap;

/**
 * Created by vvrud on 18.09.16.
 *
 * @author VVRud
 *         This class stores all the printing options.
 */
public class PrintingData {

    private static File xmlFile;
    private static File file;
    private static HashMap<String, String> options;
    private static boolean printingInterrupted;

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        PrintingData.file = file;
    }

    public static boolean isPrintingInterrupted() {
        return printingInterrupted;
    }

    public static void setPrintingInterrupted(boolean interruptedPrinting) {
        PrintingData.printingInterrupted = interruptedPrinting;
    }

    public static HashMap<String, String> getOptions() {
        return options;
    }

    public static void setOptions(HashMap<String, String> options) {
        PrintingData.options = options;
    }

    public static File getXmlFile() {
        return xmlFile;
    }

    public static void setXmlFile(File xmlFile) {
        PrintingData.xmlFile = xmlFile;
    }
}
