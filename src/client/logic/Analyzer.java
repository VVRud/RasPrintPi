package client.logic;

import client.Client;
import client.io.ReceiverClient;
import client.io.SenderClient;

/**
 * Created by vvrud on 05.12.16.
 *
 * @author VVRud
 */

public class Analyzer extends Thread {

    @Override
    public void run() {


        //Run after Analyzing
        SenderClient sender = new SenderClient();
        sender.start();
        Client.setSender(sender);

        ReceiverClient receiver = new ReceiverClient();
        receiver.start();
        Client.setReceiver(receiver);
    }
}
