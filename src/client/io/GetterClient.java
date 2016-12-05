package client.io;

import client.Client;
import client.data.PrintingData;

import java.io.ObjectInputStream;

/**
 * Created by vvrud on 14.09.16.
 *
 * @author VVRud
 *         This class provides getting messages from server to client.
 */
public class GetterClient extends Thread {

    @Override
    public void run() {
        ObjectInputStream objectInput = Client.getObjectInput();

        while (!PrintingData.isPrintingInterrupted()) {
            
        }
    }
}
