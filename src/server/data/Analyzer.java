package server.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import server.jgeometry.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by vvrud on 10.11.16.
 *
 * @author VVRud
 */
public class Analyzer {

    private static int rowCount = 0;
    private static int columnCount = 0;

    private static ArrayList<Geometry> geometryList = new ArrayList<>();
    private static ArrayList<server.jgeometry.Point> pointList = new ArrayList<>();

    public static int getColumnCount() {
        return columnCount;
    }

    public static int getRowCount() {
        return rowCount;
    }

    public static int[][] analyzeTxtFile() {
        File file = PrintingData.getFile();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }
        String fileString = "";
        try {
            fileString = reader.readLine();
        } catch (IOException e) {
            System.out.println("Failed reading line from file!");
            e.printStackTrace();
        }

        char[] stringArray = fileString.toCharArray();

        for (char numChar : stringArray) {
            int numInt = Integer.parseInt(String.valueOf(numChar));
            if (numInt != 8 && columnCount < 1) {
                rowCount++;
            } else if (numInt == 8 && columnCount >= 0) {
                columnCount++;
            }
        }

        int[][] arrayToPrint = new int[rowCount][columnCount];
        int x = 0;
        int y = 0;
        for (char numChar : stringArray) {
            int numInt = Integer.parseInt(String.valueOf(numChar));
            if (numInt != 8) {
                arrayToPrint[x][y] = numInt;
                y++;
            } else {
                x++;
                y = 0;
            }
        }
        return arrayToPrint;
    }

    public static void analyzeXmlFile() {
        File file = PrintingData.getFile();

        if (file != null) {
            try {
                Document xmlDocument = DocumentBuilderFactory.newInstance().
                        newDocumentBuilder().
                        parse(file);

                NodeList list = xmlDocument.getElementsByTagName("Element");
                for (int i = 0; i < list.getLength(); i++) {
                    Node nNode = list.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        pointList.clear();
                        Element eElement = (Element) nNode;
                        String elementType = eElement.getChildNodes().item(1).getTextContent();
                        switch (elementType) {
                            case "Point":
                                geometryList.add(createPoint(eElement));
                                break;
                            case "Line":
                                createCurve(eElement);
                                geometryList.add(new Line(pointList));
                                break;
                            case "BezierCurve2":
                                createCurve(eElement);
                                geometryList.add(new BezierCurve2(pointList));
                                break;
                            case "BezierCurve3":
                                createCurve(eElement);
                                geometryList.add(new BezierCurve3(pointList));
                                break;
                            case "BezierPath":
                                createCurve(eElement);
                                geometryList.add(new BezierPath(pointList));
                                break;
                            case "Options":
                                String mode = ((Element) nNode).getElementsByTagName("mode")
                                        .item(0)
                                        .getTextContent();
                                String intensity = ((Element) nNode).getElementsByTagName("intensity")
                                        .item(0)
                                        .getTextContent();
                                String speed = ((Element) nNode).getElementsByTagName("speed")
                                        .item(0)
                                        .getTextContent();
                                System.out.println("Options: " + mode + " " + speed + " " + intensity);
                                break;
                        }
                        System.out.println("-------------------------------------------");
                        System.out.println(geometryList);
                        System.out.println(elementType + pointList);
                        System.out.println("-------------------------------------------");
                    }
                }
                PrintingData.setGeometryList(new ArrayList<>(geometryList));
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else System.out.println("Something went wrong!");
    }

    private static void createCurve(Element eElement) {
        NodeList nl = eElement.getElementsByTagName("point");
        for (int j = 0; j < nl.getLength(); j++) {
            Element e = (Element) nl.item(j);
            pointList.add(createPoint(e));
        }
    }

    private static Point createPoint(Element eElement) {
        float x = Float.parseFloat(eElement.getElementsByTagName("xCoord")
                .item(0)
                .getTextContent());
        float y = Float.parseFloat(eElement.getElementsByTagName("yCoord")
                .item(0)
                .getTextContent());
        return new server.jgeometry.Point(x, y);
    }

}
