package server;

import server.io.GetterServer;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by vvrud on 21.09.16.
 *
 * @author VVRud
 */
public class Interrupter extends Thread {

    private static boolean printingInterrupted;

    public Interrupter(boolean printingInterrupted) {
        Interrupter.printingInterrupted = printingInterrupted;
    }

    static boolean isPrintingInterrupted() {
        return printingInterrupted;
    }

    @Override
    public void run() {
        if (printingInterrupted) {
            interruptPrinting();
        } else {
            DataInputStream dataInput = Server.getDataInput();
            try {
                printingInterrupted = dataInput.readBoolean();
            } catch (IOException e) {
                System.out.println("failed reading interruption");
                e.printStackTrace();
            }
            if (printingInterrupted) {
                interruptPrinting();
            }

        }
    }

    private void interruptPrinting() {
        Printer printer = GetterServer.getPrinter();
        printer.interrupt();
        GetterServer getter = new GetterServer();
        getter.start();
        System.out.println("Printing was interrupted!");
    }
}
