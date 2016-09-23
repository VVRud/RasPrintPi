package server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by vvrud on 14.09.16.
 *
 * @author VVRud
 *         This class provides getting messages from client to server.
 *         It gets file and options to print.
 *         It also check if program interrupted.
 */
class GetterServer extends Thread {

    private boolean printingInterrupted = false;

    @Override
    public void run() {
        DataInputStream dataInput = Server.getDataInput();

        try {
            printingInterrupted = dataInput.readBoolean();
        } catch (IOException e) {
            System.out.println("interruption reading failed");
            e.printStackTrace();
        }

        if (!printingInterrupted) {
            try {
                inputOptions(dataInput);
                inputFile(dataInput);
            } catch (IOException e) {
                System.out.println("Failed input data");
            }

            Interrupter interrupter = new Interrupter(printingInterrupted);
            interrupter.start();
        } else {
            Interrupter interrupter = new Interrupter(printingInterrupted);
            interrupter.start();
        }
    }

    private void inputOptions(DataInputStream input) throws IOException {
        PrintingData.setPrintingSpeed(Float.parseFloat(input.readUTF()));
        PrintingData.setPrintingMode(input.readUTF());
        PrintingData.setPrintingIntensity(Float.parseFloat(input.readUTF()));
        System.out.println(PrintingData.getPrintingSpeed());
        System.out.println(PrintingData.getPrintingMode());
        System.out.println(PrintingData.getPrintingIntensity());
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
