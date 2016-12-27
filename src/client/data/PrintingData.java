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

    private static File xmlFileCreated;
    private static File jpgFileCreated;
    private static File txtFileCreated;

    private static File xmlFileChosen;
    private static File jpgFileChosen;
    private static File txtFileChosen;

    private static HashMap<String, String> options;
    private static boolean printingInterrupted;

    public static File getJpgFileCreated() {
        return jpgFileCreated;
    }

    public static void setJpgFileCreated(File jpgFileCreated) {
        PrintingData.jpgFileCreated = jpgFileCreated;
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

    public static File getXmlFileCreated() {
        return xmlFileCreated;
    }

    public static void setXmlFileCreated(File xmlFileCreated) {
        PrintingData.xmlFileCreated = xmlFileCreated;
    }

    public static File getTxtFileCreated() {
        return txtFileCreated;
    }

    public static void setTxtFileCreated(File txtFileCreated) {
        PrintingData.txtFileCreated = txtFileCreated;
    }

    public static File getXmlFileChosen() {
        return xmlFileChosen;
    }

    public static void setXmlFileChosen(File xmlFileChosen) {
        PrintingData.xmlFileChosen = xmlFileChosen;
    }

    public static File getJpgFileChosen() {
        return jpgFileChosen;
    }

    public static void setJpgFileChosen(File jpgFileChosen) {
        PrintingData.jpgFileChosen = jpgFileChosen;
    }

    public static File getTxtFileChosen() {
        return txtFileChosen;
    }

    public static void setTxtFileChosen(File txtFileChosen) {
        PrintingData.txtFileChosen = txtFileChosen;
    }
}
