package client;

import client.io.ReceiverClient;
import client.io.SenderClient;
import client.ui.LoginWindow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by vvrud on 10.09.16.
 *
 * @author vvrud
 *         This class runs this software and saves Socket of server here.
 */

public class Client {

    private static Socket socket;
    private static DataOutputStream dataOutput;
    private static DataInputStream dataInput;
    private static ObjectOutputStream objectOutput;
    private static ObjectInputStream objectInput;

    private static SenderClient sender;
    private static ReceiverClient receiver;


    public static void main(String[] args) {
        new LoginWindow();
        Thread.yield();
    }

    public static void setSocket(Socket socket) {
        Client.socket = socket;
    }

    public static DataOutputStream getDataOutput() {
        return dataOutput;
    }

    public static void setDataOutput(DataOutputStream dataOutput) {
        Client.dataOutput = dataOutput;
    }

    public static DataInputStream getDataInput() {
        return dataInput;
    }

    public static void setDataInput(DataInputStream dataInput) {
        Client.dataInput = dataInput;
    }

    public static ObjectOutputStream getObjectOutput() {
        return objectOutput;
    }

    public static void setObjectOutput(ObjectOutputStream objectOutput) {
        Client.objectOutput = objectOutput;
    }

    public static ObjectInputStream getObjectInput() {
        return objectInput;
    }

    public static void setObjectInput(ObjectInputStream objectInput) {
        Client.objectInput = objectInput;
    }

    public static SenderClient getSender() {
        return sender;
    }

    public static void setSender(SenderClient sender) {
        Client.sender = sender;
    }

    public static ReceiverClient getReceiver() {
        return receiver;
    }

    public static void setReceiver(ReceiverClient receiver) {
        Client.receiver = receiver;
    }
}
