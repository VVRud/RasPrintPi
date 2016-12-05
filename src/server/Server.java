package server;

import server.io.GetterServer;

import java.io.*;
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
    private static ObjectInputStream objectInput;
    private static ObjectOutputStream objectOutput;

    public static void main(String[] args) {
        int port = 6565;

        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Waiting for a client...");
            serverSocket = ss.accept();
            System.out.println("Have got a client!");
            System.out.println();

            dataInput = new DataInputStream(serverSocket.getInputStream());
            objectInput = new ObjectInputStream(dataInput);
            objectOutput = new ObjectOutputStream(new DataOutputStream(serverSocket.getOutputStream()));

            GetterServer getter = new GetterServer();
            System.out.println("Getter started!");
            getter.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static DataInputStream getDataInput() {
        return dataInput;
    }

    static DataOutputStream getDataOutput() {
        return dataOutput;
    }

    public static ObjectInputStream getObjectInput() {
        return objectInput;
    }

    public static ObjectOutputStream getObjectOutput() {
        return objectOutput;
    }

}