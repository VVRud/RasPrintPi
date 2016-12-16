package client.logic;

import client.Client;
import client.io.ReceiverClient;
import client.io.SenderClient;
import client.ui.WorkspaceWindow;

import java.util.ArrayList;

import static client.data.Constants.*;

/**
 * Created by vvrud on 05.12.16.
 *
 * @author VVRud
 */

public class Analyzer extends Thread {

    private static ArrayList<ArrayList<Integer>> listX = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> listY = new ArrayList<>();
    private int mode = 1;

    public Analyzer(int mode) {
        this.mode = mode;
    }

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

        if (mode == BEZ_MODE) {
            analyzeCurve();
        } else if (mode == JPG_MODE) {
            analyzePicture();
        } else WorkspaceWindow.showWarningMessage(UNKNOWN_MODE);

        //Run after Analyzing
        SenderClient sender = new SenderClient();
        sender.start();
        Client.setSender(sender);

        ReceiverClient receiver = new ReceiverClient();
        receiver.start();
        Client.setReceiver(receiver);
    }

    private void analyzeCurve() {
        if (listX.isEmpty() || listY.isEmpty()) {
            WorkspaceWindow.showWarningMessage(EMPTY_DRAWING_LIST);
        } else {
            ArrayList<Integer> lx;
            ArrayList<Integer> ly;
            //TODO write first line of xml
            for (int i = 0; i < listX.size() || i < listY.size(); i++) {
                lx = listX.get(i);
                ly = listY.get(i);

                int lxs = lx.size();
                int lys = ly.size();

                if (lxs == 1 && lys == 1) {
                    //add point

                } else if (lxs == 2 && lys == 2) {
                    //add line
                } else if (lxs == 3 && lys == 3) {
                    //add bezier curve2
                } else if (lxs == 4 && lys == 4) {
                    //add bezier curve3
                } else if (lxs > 4 && lys > 4) {
                    //add bezier path
                }
            }
        }
    }

    private void analyzePicture() {

    }

    private int findFirstPoint(int p1, int p2, int p3, int p4) {
        return (-5 * p1 + 18 * p2 - 9 * p3 + 2 * p4) / 6;
    }

    private int findSecondPoint(int p1, int p2, int p3, int p4) {
        return (-5 * p4 + 18 * p3 - 9 * p2 + 2 * p1) / 6;
    }
}
