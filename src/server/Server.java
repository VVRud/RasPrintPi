package server;

import server.io.ReceiverServer;
import server.io.SenderServer;

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

    private static final int PORT = 6565;
    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;
    private static ObjectInputStream objectInput;
    private static ObjectOutputStream objectOutput;
    private static Printer printer;
    private static SenderServer sender;
    private static ReceiverServer receiver;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        try {
            ServerSocket ss = new ServerSocket(PORT);
            System.out.println("Waiting for a client...");
            Socket serverSocket = ss.accept();
            System.out.println("Have got a client!\n");

            dataInput = new DataInputStream(serverSocket.getInputStream());
            objectInput = new ObjectInputStream(dataInput);
            objectOutput = new ObjectOutputStream(new DataOutputStream(serverSocket.getOutputStream()));

            receiver = new ReceiverServer();
            receiver.start();
            System.out.println("Getter started!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        if (receiver != null) {
            receiver.interrupt();
            receiver = null;
        }
        if (sender != null) {
            sender.interrupt();
            sender = null;
        }
        if (printer != null) {
            printer.interrupt();
            printer = null;
        }

        dataInput = null;
        dataOutput = null;
        objectInput = null;
        objectOutput = null;
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

    public static void setPrinter(Printer printer) {
        Server.printer = printer;
    }

    public void setSender(SenderServer sender) {
        Server.sender = sender;
    }
}