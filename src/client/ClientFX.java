package client;

import client.io.ReceiverClient;
import client.io.SenderClient;
import client.ui.LoginWindowFX;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientFX {

    private static Socket socket;
    private static DataOutputStream dataOutput;
    private static DataInputStream dataInput;

    private static SenderClient sender;
    private static ReceiverClient receiver;

    public static void main(String[] args) {
        new LoginWindowFX().openWindow(args);
    }
}
