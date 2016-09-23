package server;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by vvrud on 21.09.16.
 *
 * @author VVRud
 */
class Interrupter extends Thread {

    private boolean printingInterrupted;

    Interrupter(boolean printingInterrupted) {
        this.printingInterrupted = printingInterrupted;
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
        //TODO Interrupt printing
        System.out.println("I'm INTERRUPTED");
    }
}
