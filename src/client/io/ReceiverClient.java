package client.io;

import client.Client;
import client.ui.WorkspaceWindow;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import static client.data.Constants.PRINTING_FINISHED;

/**
 * Created by vvrud on 14.09.16.
 *
 * @author VVRud
 *         This class provides getting messages from server to client.
 */
public class ReceiverClient extends Thread {

    @Override
    public void run() {
        DataInputStream dataInput = Client.getDataInput();
        boolean disconnected = false;

        while (!disconnected) {
            int state = 0;
            try {
                state = dataInput.readInt();
            } catch (EOFException eex) {
                System.out.println("Server disconnected");
                eex.printStackTrace();
                disconnected = true;
            } catch (IOException e) {
                System.out.println("Failed reading state!");
                e.printStackTrace();
            }
            if (state == PRINTING_FINISHED) {
                WorkspaceWindow.setInactiveFalse();
                WorkspaceWindow.showFinishingMessage();
            }
        }
    }
}
