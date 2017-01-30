package server;

import server.io.ReceiverServer;
import server.io.SenderServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
            dataOutput = new DataOutputStream(serverSocket.getOutputStream());
            objectInput = new ObjectInputStream(dataInput);

            receiver = new ReceiverServer(dataInput, objectInput);
            receiver.start();

            sender = new SenderServer(dataOutput);
            sender.start();

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
    }

    public static DataInputStream getDataInput() {
        return dataInput;
    }

    public static DataOutputStream getDataOutput() {
        return dataOutput;
    }

    public static ObjectInputStream getObjectInput() {
        return objectInput;
    }

    public static void setPrinter(Printer printer) {
        Server.printer = printer;
    }

    public static SenderServer getSender() {
        return sender;
    }
}