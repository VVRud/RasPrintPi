package server;

import java.io.*;
import java.net.*;

public class ServerProg {

    public static void main(String[] args) {
        int port = 6666;

        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Waiting for a client...");
            Socket serverSocket = ss.accept();
            System.out.println("Have got a client!");
            System.out.println();

            DataInputStream input = new DataInputStream(serverSocket.getInputStream());
            DataOutputStream outout = new DataOutputStream(serverSocket.getOutputStream());

            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}