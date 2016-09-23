package client.logic;

import client.ui.LoginWindow;
import client.ui.WorkspaceWindow;

import javax.swing.*;

/**
 * Created by vvrud on 10.09.16.
 *
 * @author vvrud
 *         This class runs this software and saves Socket of server here.
 */

public class Client {

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
}
