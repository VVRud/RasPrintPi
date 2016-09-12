package client.logic;

import client.ui.LoginWindow;

import javax.swing.*;

public class ClientProg {

    public static void main(String[] args) {
        JFrame loginWindow = new LoginWindow();
        loginWindow.setVisible(true);

//        while(!LoginWindow.isConnectionSuccess()){
//            Thread.yield();
//        }
//
//        loginWindow.setVisible(false);
//        loginWindow = null;

//        JFrame mainWindow = new WorkspaceWindow();
//        mainWindow.setVisible(true);

    }
}
