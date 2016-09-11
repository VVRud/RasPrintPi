package client.ui;

import javax.swing.*;

import static client.Constants.PROG_TITLE;

/**
 * Created by vvrud on 11.09.16.
 * Workspace window of my soft, that let you to download file, choose all available features of printing
 * and start printing.
 */
public class WorkspaceWindow extends JFrame {

    WorkspaceWindow() {
        super(PROG_TITLE + " on IP: " + LoginWindow.getIp() + LoginWindow.getPort());


    }
}
