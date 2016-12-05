package client;

import client.ui.LoginWindow;
import client.ui.WorkspaceWindow;

import javax.swing.*;
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

    public static void main(String[] args) {
        JFrame loginWindow = new LoginWindow();
        loginWindow.setVisible(true);

        while (!LoginWindow.isConnectionSuccess()) {
            Thread.yield();
        }

        loginWindow.setVisible(false);
        loginWindow = null;

        JFrame mainWindow = new WorkspaceWindow();
        mainWindow.setVisible(true);
    }

    public static Socket getSocket() {
        return socket;
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
}
