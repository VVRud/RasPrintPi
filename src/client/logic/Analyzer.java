package client.logic;

import client.Client;
import client.data.PrintingData;
import client.io.ReceiverClient;
import client.io.SenderClient;
import client.ui.WorkspaceWindow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public void addToAnalyze(ArrayList<Integer> listXDraw, ArrayList<Integer> listYDraw) {
        listX.add(listXDraw);
        listY.add(listYDraw);
    }

    public void clearLists() {
        listX.clear();
        listY.clear();
    }

    @Override
    public void run() {

        if (mode == BEZ_MODE) {
            try {
                analyzeCurve();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed writing to file!");
            }
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

    private void analyzeCurve() throws IOException {
        if (listX.isEmpty() || listY.isEmpty()) {
            WorkspaceWindow.showWarningMessage(EMPTY_DRAWING_LIST);
        } else {
            ArrayList<Integer> lx;
            ArrayList<Integer> ly;
            File xmlFile = File.createTempFile("rpi_xml_tmp", ".xml");
            FileWriter fr = new FileWriter(xmlFile);
            BufferedWriter writer = new BufferedWriter(fr);

            writer.write(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<Elements>" +
                            "\t<Element>\n" +
                            "\t\t<type>Options</type>\n" +
                            "\t\t<mode>" + PrintingData.getOptions().get("Mode") + "</mode>\n" +
                            "\t\t<intensity>" + PrintingData.getOptions().get("Intensity") + "</intensity>\n" +
                            "\t\t<speed>" + PrintingData.getOptions().get("Speed") + "</speed>\n" +
                            "\t</Element>");

            for (int i = 0; i < listX.size() || i < listY.size(); i++) {
                lx = listX.get(i);
                ly = listY.get(i);

                int lxs = lx.size();
                int lys = ly.size();

                writer.write("\t<Element>");

                if (lxs == 0 && lys == 0) {
                    System.out.println("Something went wrong! List is empty!");
                } else if (lxs == 1 && lys == 1) {
                    writer.write(
                            "\t\t<type>Point</type>\n" +
                            "\t\t<xCoord>" + lx.get(0) + "</xCoord>\n" +
                            "\t\t<yCoord>" + ly.get(0) + "</yCoord>");
                    System.out.printf("Point(%d; %d)\n", lx.get(0), ly.get(0));
                } else if (lxs == 2 && lys == 2) {
                    writer.write(
                            "\t\t<type>Line</type>\n" +
                            "\t\t<point0>\n" +
                            "\t\t\t<xCoord>" + lx.get(0) + "</xCoord>\n" +
                            "\t\t\t<yCoord" + ly.get(0) + "</yCoord>\n" +
                            "\t\t</point0>\n" +
                            "\t\t<point1>\n" +
                            "\t\t\t<xCoord>" + lx.get(1) + "</xCoord>\n" +
                            "\t\t\t<yCoord>" + ly.get(1) + "</yCoord>\n" +
                            "\t\t</point1>");
                    System.out.printf("Line(%d; %d)->(%d; %d)\n", lx.get(0), ly.get(0), lx.get(1), ly.get(1));
                } else if (lxs == 3 && lys == 3) {
                    int p0x = lx.get(0);
                    int p0y = ly.get(0);
                    int p1x = lx.get(1);
                    int p1y = ly.get(1);
                    int p2x = lx.get(2);
                    int p2y = ly.get(2);

                    writer.write(
                            "\t\t<type>BezierCurve2</type>\n" +
                            "\t\t<point0>\n" +
                            "\t\t\t<xCoord>" + p0x + "</xCoord>\n" +
                            "\t\t\t<yCoord>" + p0y + "</yCoord>\n" +
                            "\t\t</point0>\n" +
                            "\t\t<point1>\n" +
                            "\t\t\t<xCoord>" + findPointBez2(p0x, p1x, p2x) + "</xCoord>\n" +
                            "\t\t\t<yCoord>" + findPointBez2(p0y, p1y, p2y) + "</yCoord>\n" +
                            "\t\t</point1>\n" +
                            "\t\t<point2>\n" +
                            "\t\t\t<xCoord>" + p2x + "</xCoord>\n" +
                            "\t\t\t<yCoord>" + p2y + "</yCoord>\n" +
                            "\t\t</point2>");
                } else if ((lxs >= 4 && lxs <= 28) && (lys >= 4 && lys <= 28)) {
                    int p0x = lx.get(0);
                    int p0y = ly.get(0);
                    int p1x = lx.get((lxs / 3) - 1);
                    int p1y = ly.get((lys / 3) - 1);
                    int p2x = lx.get((lxs * 2 / 3) - 1);
                    int p2y = ly.get((lys * 2 / 3) - 1);
                    int p3x = lx.get(lxs - 1);
                    int p3y = ly.get(lys - 1);

                    writer.write(
                            "\t\t<type>BezierCurve3</type>\n" +
                            "\t\t<point0>\n" +
                            "\t\t\t<xCoord>" + p0x + "</xCoord>\n" +
                            "\t\t\t<yCoord>" + p0y + "</yCoord>\n" +
                            "\t\t</point0>\n" +
                            "\t\t<point1>\n" +
                            "\t\t\t<xCoord>" + findFirstPointBez3(p0x, p1x, p2x, p3x) + "</xCoord>\n" +
                            "\t\t\t<yCoord>" + findFirstPointBez3(p0y, p1y, p2y, p3y) + "</yCoord>\n" +
                            "\t\t</point1>\n" +
                            "\t\t<point2>\n" +
                            "\t\t\t<xCoord>" + findSecondPointBez3(p0x, p1x, p2x, p3x) + "</xCoord>\n" +
                            "\t\t\t<yCoord>" + findSecondPointBez3(p0y, p1y, p2y, p3y) + "</yCoord>\n" +
                            "\t\t</point2>\n" +
                            "\t\t<point3>\n" +
                            "\t\t\t<xCoord>" + p3x + "</xCoord>\n" +
                            "\t\t\t<yCoord>" + p3y + "</yCoord>\n" +
                            "\t\t</point3>");
                } else if (lxs > 28 && lys > 28) {
                    double num = (lxs - 1) / 3;
                    int curves = 3;

                    while (!((num >= 10 + 5 * (curves - 3)) && (num < 10 + 5 * (curves - 2)))) {
                        curves++;
                    }

                    int pointsCount = 3 * curves + 1;
                    System.out.printf("BezierPath. Points in list: %d. Curves: %d. Points to XML: %d\n", lxs, curves, pointsCount);

                    double coefficient = pointsCount / lxs;
                    int coef0, coef1, coef2, coef3;
                    int p0x = 0, p1x, p2x, p3x,
                            p0y = 0, p1y, p2y, p3y;

                    for (int j = 0; j < pointsCount; j = j + 3) {
                        coef0 = (int) (j * coefficient);
                        coef1 = (int) ((j + 1) * coefficient);
                        coef2 = (int) ((j + 2) * coefficient);
                        coef3 = (int) ((j + 3) * coefficient);

                        if (j == 0) {
                            p0x = lx.get(coef0);
                            p0y = ly.get(coef0);
                        }

                        p1x = lx.get(coef1);
                        p1y = ly.get(coef1);
                        p2x = lx.get(coef2);
                        p2y = ly.get(coef2);
                        p3x = lx.get(coef3);
                        p3y = ly.get(coef3);

                        if (j == 0) {
                            writer.write("\t\t<point" + j + ">\n" +
                                    "\t\t\t<xCoord>" + p0x + "</xCoord>\n" +
                                    "\t\t\t<yCoord>" + p0y + "</yCoord>\n" +
                                    "\t\t</point" + j + ">\n");
                        }

                        writer.write(
                                "\t\t<point" + (j + 1) + ">\n" +
                                        "\t\t\t<xCoord>" + findFirstPointBez3(p0x, p1x, p2x, p3x) + "</xCoord>\n" +
                                        "\t\t\t<yCoord>" + findFirstPointBez3(p0y, p1y, p2y, p3y) + "</yCoord>\n" +
                                        "\t\t</point" + (j + 1) + ">\n" +
                                        "\t\t<point" + (j + 2) + ">\n" +
                                        "\t\t\t<xCoord>" + findSecondPointBez3(p0x, p1x, p2x, p3x) + "</xCoord>\n" +
                                        "\t\t\t<yCoord>" + findSecondPointBez3(p0y, p1y, p2y, p3y) + "</yCoord>\n" +
                                        "\t\t</point" + (j + 2) + ">\n" +
                                        "\t\t<point" + (j + 3) + ">\n" +
                                        "\t\t\t<xCoord>" + p3x + "</xCoord>\n" +
                                        "\t\t\t<yCoord>" + p3y + "</yCoord>\n" +
                                        "\t\t</point" + (j + 3) + ">");

                        p0x = p3x;
                        p0y = p3y;
                    }
                }

                writer.write("\t</Element>");
            }
            writer.write("</Elements>");
            writer.flush();
            writer.close();

            PrintingData.setXmlFile(xmlFile);
        }
    }

    private void analyzePicture() {

    }

    private double findFirstPointBez3(int p0, int p1, int p2, int p3) {
        return (-5 * p0 + 18 * p1 - 9 * p2 + 2 * p3) / 6;
    }

    private double findSecondPointBez3(int p0, int p1, int p2, int p3) {
        return (-5 * p3 + 18 * p2 - 9 * p1 + 2 * p0) / 6;
    }

    private double findPointBez2(int p0, int p1, int p2) {
        return 0.25 * p1 - p0 - p2;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
