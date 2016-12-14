package client.logic;

import client.Client;
import client.io.ReceiverClient;
import client.io.SenderClient;
import client.ui.WorkspaceWindow;

import java.util.ArrayList;

import static client.data.Constants.EMPTY_DRAWING_LIST;

/**
 * Created by vvrud on 05.12.16.
 *
 * @author VVRud
 */

public class Analyzer extends Thread {

    private static ArrayList<ArrayList<Integer>> listX = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> listY = new ArrayList<>();

    public static void addToAnalyze(ArrayList<Integer> listXDraw, ArrayList<Integer> listYDraw) {
        listX.add(listXDraw);
        listY.add(listYDraw);
    }

    public static void clearLists() {
        listX.clear();
        listY.clear();
    }

    @Override
    public void run() {


        if (listX.isEmpty() && listY.isEmpty()) {
            WorkspaceWindow.showWarningMessage(EMPTY_DRAWING_LIST);
        } else {
            ArrayList<Integer> lx;
            ArrayList<Integer> ly;
            for (int i = 0; i < listX.size() || i < listY.size(); i++) {
                lx = listX.get(i);
                ly = listY.get(i);

                int lxs = lx.size();
                int lys = ly.size();

                if (lxs == 1 && lys == 1) {
                    //make point
                } else if (lxs == 2 && lys == 2) {
                    //make line
                } else if (lxs == 3 && lys == 3) {
                    //make bezier curve2
                } else if (lxs == 4 && lys == 4) {
                    //make bezier curve 3
                } else if (lxs > 4 && lys > 4) {
                    //make bezier path
                }
            }
        }



        //Run after Analyzing
        SenderClient sender = new SenderClient();
        sender.start();
        Client.setSender(sender);

        ReceiverClient receiver = new ReceiverClient();
        receiver.start();
        Client.setReceiver(receiver);
    }

    private int findFirstPoint(int p1, int p2, int p3, int p4) {
        return (-5 * p1 + 18 * p2 - 9 * p3 + 2 * p4) / 6;
    }

    private int findSecondPoint(int p1, int p2, int p3, int p4) {
        return (-5 * p4 + 18 * p3 - 9 * p2 + 2 * p1) / 6;
    }
}
