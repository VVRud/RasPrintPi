package server.io;

import server.Printer;
import server.Server;
import server.data.PrintingData;

import java.io.*;
import java.net.SocketException;
import java.util.HashMap;

/**
 * Created by vvrud on 14.09.16.
 *
 * @author VVRud
 *         This class provides getting messages from client to server.
 *         It gets file and options to print.
 *         It also check if program interrupted.
 */
public class ReceiverServer extends Thread {

    public static final int INTERRUPT = 0;
    public static final int TXT = 1;
    public static final int XML = 2;
    public static final int SHUTDOWN = 3;

    private static Printer printer;
    private int currentState = -1;
    private DataInputStream dataInput = Server.getDataInput();
    private ObjectInputStream objectInput = Server.getObjectInput();

    public static Printer getPrinter() {
        return printer;
    }

    @Override
    public void run() {
        while (currentState != SHUTDOWN) {
            try {
                currentState = dataInput.readInt();
                if (currentState == TXT) {
                    inputOptions(objectInput);
                    inputFile(dataInput, "txt");
                    printer = new Printer(currentState);
                    printer.start();
                    Server.setPrinter(printer);
                } else if (currentState == XML) {
                    inputFile(dataInput, "xml");
                    printer = new Printer(currentState);
                    printer.start();
                    Server.setPrinter(printer);
                } else if (currentState == INTERRUPT) {
                    interruptPrinting();
                }
            } catch (SocketException e) {
                currentState = SHUTDOWN;
                System.out.println("Client is not connected now!");
                e.printStackTrace();
            } catch (IOException e) {
                currentState = SHUTDOWN;
                System.out.println("Reading failed");
                e.printStackTrace();
            }
        }
        if (currentState == SHUTDOWN) {
            Server.shutdown();
            Server.start();
        }
    }

    private void interruptPrinting() {
        if (printer != null && printer.isAlive()) {
            printer.stopPrinting();
        }
    }

    private void inputOptions(ObjectInputStream objectInput) throws IOException {
        HashMap<String, String> options = null;
        try {
            options = (HashMap<String, String>) objectInput.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Class was not found!");
            e.printStackTrace();
        }
        PrintingData.setOptions(options);
    }

    private void inputFile(DataInputStream input, String type) throws IOException {
        long size = input.readLong();

        File file = File.createTempFile("rppi_tmp_", "." + type);

        FileOutputStream outF = new FileOutputStream(file);

        byte[] buffer = new byte[(int) size];

        long left = size;
        while (true) {
            int nextPackSize = (int) Math.min(left, buffer.length);
            int count = input.read(buffer, 0, nextPackSize);
            if (count <= 0) {
                throw new RuntimeException("Что-то пошло не так!");
            }

            outF.write(buffer, 0, count);

            left -= count;
            if (left == 0) {
                break;
            }
        }

        outF.flush();
        outF.close();
        PrintingData.setFile(file);
        System.out.println(PrintingData.getFile());
    }

    public int getCurrentState() {
        return currentState;
    }
}
