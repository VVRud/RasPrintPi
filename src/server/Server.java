package server;

import server.io.ReceiverServer;

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

    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;
    private static ObjectInputStream objectInput;
    private static ObjectOutputStream objectOutput;

    public static void main(String[] args) {
        int port = 6565;

        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Waiting for a client...");
            Socket serverSocket = ss.accept();
            System.out.println("Have got a client!\n");

            dataInput = new DataInputStream(serverSocket.getInputStream());
            objectInput = new ObjectInputStream(dataInput);
            objectOutput = new ObjectOutputStream(new DataOutputStream(serverSocket.getOutputStream()));

            new ReceiverServer().start();
            System.out.println("Getter started!");
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