package client.logic;

import java.net.Socket;

/**
 * Created by vvrud on 12.09.16.
 * This class provides connection between server and client.
 */
public class SenderClient {
    private static Socket socket;


    public static void setSocket(Socket socket) {
        SenderClient.socket = socket;
    }
}
