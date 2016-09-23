package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by vvrud on 14.09.16.
 *
 * @author VVRud
 *         This class provides connection between server and client
 *         and also runs server on the printer.
 */

public class Server {

    private static Socket serverSocket;
    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;

    public static void main(String[] args) {
        int port = 6565;

        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Waiting for a client...");
            serverSocket = ss.accept();
            System.out.println("Have got a client!");
            System.out.println();

            dataInput = new DataInputStream(serverSocket.getInputStream());
            dataOutput = new DataOutputStream(serverSocket.getOutputStream());

            GetterServer getter = new GetterServer();
            System.out.println("Getter started!");
            getter.start();

            SenderServer sender = new SenderServer();
            System.out.println("Sender started!");
            sender.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static Socket getServerSocket() {
        return serverSocket;
    }

    static DataInputStream getDataInput() {
        return dataInput;
    }

    static DataOutputStream getDataOutput() {
        return dataOutput;
    }
}