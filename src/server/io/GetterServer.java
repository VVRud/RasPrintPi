package server.io;

import server.Interrupter;
import server.Printer;
import server.Server;
import server.data.PrintingData;

import java.io.*;
import java.util.HashMap;

/**
 * Created by vvrud on 14.09.16.
 *
 * @author VVRud
 *         This class provides getting messages from client to server.
 *         It gets file and options to print.
 *         It also check if program interrupted.
 */
public class GetterServer extends Thread {

    private static Printer printer;

    public static Printer getPrinter() {
        return printer;
    }

    @Override
    public void run() {
        boolean printingInterrupted = false;
        DataInputStream dataInput = Server.getDataInput();
        ObjectInputStream objectInput = Server.getObjectInput();

        try {
            printingInterrupted = dataInput.readBoolean();
        } catch (IOException e) {
            System.out.println("interruption reading failed");
            e.printStackTrace();
        }

        if (!printingInterrupted) {
            try {
                inputOptions(objectInput);
                inputFile(dataInput);
            } catch (IOException e) {
                System.out.println("Failed input data");
                e.printStackTrace();
            }

            Interrupter interrupter = new Interrupter(printingInterrupted);
            printer = new Printer();
            printer.start();
            interrupter.start();
        } else {
            Interrupter interrupter = new Interrupter(printingInterrupted);
            interrupter.start();
        }
    }

    private void inputOptions(ObjectInputStream objectInput) throws IOException {
        //TODO прием объекта
        HashMap<String, String> options = null;
        try {
            options = (HashMap<String, String>) objectInput.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Class was not found!");
            e.printStackTrace();
        }
        System.out.println(options.get("Speed"));
        System.out.println(options.get("Mode"));
        System.out.println(options.get("Intensity"));
        PrintingData.setOptions(options);
    }

    private void inputFile(DataInputStream input) throws IOException {
        long size = input.readLong();
        String fileName = input.readUTF();
        String fileFolder = "/home/vvrud/RasPrintPiPictures/";

        new File(fileFolder).mkdir();
        FileOutputStream outF = new FileOutputStream(fileFolder + fileName);

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
        PrintingData.setFile(new File(fileFolder + fileName));
        System.out.println(PrintingData.getFile());
    }
}
