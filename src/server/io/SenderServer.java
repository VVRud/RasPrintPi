package server.io;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by vvrud on 14.09.16.
 *
 * @author VVRud
 *         This class provides sending messages to client from server.
 */
class SenderServer {

    static void sendSize(int rowCount, int columnCount, DataOutputStream dataOutput) {
        try {
            dataOutput.writeUTF("Size");
            dataOutput.write(rowCount);
            dataOutput.write(columnCount);
        } catch (IOException e) {
            System.out.println("Failed sending size");
            e.printStackTrace();
        }
    }

    static void sendCords(int x, int y, DataOutputStream dataOutput) {
        try {
            dataOutput.writeUTF("Cords");
            dataOutput.write(x);
            dataOutput.write(y);
        } catch (IOException e) {
            System.out.println("Failed sending cords");
            e.printStackTrace();
        }
    }
}
