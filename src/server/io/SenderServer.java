package server.io;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by vvrud on 14.09.16.
 *
 * @author VVRud
 *         This class provides sending messages to client from server.
 */
public class SenderServer extends Thread {

    private static final int FINISHED_PRINTING = 9;

    private DataOutputStream dataOut;

    public SenderServer(DataOutputStream dataOut) {
        this.dataOut = dataOut;
    }

    public void sendFinishing() {
        try {
            dataOut.writeInt(FINISHED_PRINTING);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problems with sending printing finished.");
        }
    }

    @Override
    public void run() {
        System.out.println("Sender was started!");
    }

}
