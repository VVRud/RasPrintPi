package client.logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by vvrud on 18.09.16.
 *
 * @author VVRud
 *         This class stores all the printing options.
 */
public class PrintingData {

    private static Socket socket;
    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;

    private static File file;
    private static HashMap<String, String> options;
    private static boolean printingInterrupted;

    static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        PrintingData.file = file;
    }

    static HashMap<String, String> getOptions() {
        return options;
    }

    public static void setOptions(HashMap<String, String> options) {
        PrintingData.options = options;
    }

    static boolean isPrintingInterrupted() {
        return printingInterrupted;
    }

    public static void setPrintingInterrupted(boolean interruptedPrinting) {
        PrintingData.printingInterrupted = interruptedPrinting;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        PrintingData.socket = socket;
    }

    public static DataInputStream getDataInput() {
        return dataInput;
    }

    public static void setDataInput(DataInputStream dataInput) {
        PrintingData.dataInput = dataInput;
    }

    static DataOutputStream getDataOutput() {
        return dataOutput;
    }

    public static void setDataOutput(DataOutputStream dataOutput) {
        PrintingData.dataOutput = dataOutput;
    }
}
