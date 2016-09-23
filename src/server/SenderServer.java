package server;

/**
 * Created by vvrud on 14.09.16.
 *
 * @author VVRud
 *         This class provides sending messages to client from server.
 */
class SenderServer extends Thread {

    @Override
    public void run() {
        System.out.println("Sender runned!");
        //DataOutputStream dataOutput = new DataOutputStream();

        System.out.println("Sender finished...");
    }
}
