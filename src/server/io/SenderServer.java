package server.io;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by vvrud on 14.09.16.
 *
 * @author VVRud
 *         This class provides sending messages to client from server.
 */
public class SenderServer {

    private static final int FINISH_PRINTING = 9;

    private DataOutputStream dataOut;

    public SenderServer(DataOutputStream dataOut) {
        this.dataOut = dataOut;
    }

    public void sendFinishing() {
        try {
            dataOut.writeInt(FINISH_PRINTING);
        } catch (IOException e) {
            System.out.println("Problems with sending printing finished.");
            e.printStackTrace();
        }
    }
}
