package client.logic;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by vvrud on 14.09.16.
 *
 * @author VVRud
 *         This class provides getting messages from server to client.
 */
public class GetterClient extends Thread {

    @Override
    public void run() {
        Socket socket = PrintingData.getSocket();

        DataInputStream input = null;
        try {
            input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("failed creating input stream");
        }


    }
}
