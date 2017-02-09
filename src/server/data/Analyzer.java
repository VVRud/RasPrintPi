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
        ArrayList<Geometry> geometryList = new ArrayList<>();
        File file = PrintingData.getFile();

        if (file != null) {
            try {
                Document xmlDocument = DocumentBuilderFactory.newInstance().
                        newDocumentBuilder().
                        parse(file);

                NodeList list = xmlDocument.getElementsByTagName("Element");
                System.out.println("NORMAL CHECK\n________________________________________________");
                for (int i = 0; i < list.getLength(); i++) {
                    Node nNode = list.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String elementType = eElement.getChildNodes().item(1).getTextContent();
                        if (elementType.equals("Options")) {
                            String mode = ((Element) nNode).getElementsByTagName("mode")
                                    .item(0)
                                    .getTextContent();
                            String intensity = ((Element) nNode).getElementsByTagName("intensity")
                                    .item(0)
                                    .getTextContent();
                            String speed = ((Element) nNode).getElementsByTagName("speed")
                                    .item(0)
                                    .getTextContent();
                            //TODO Save settings

                        } else {
                            ArrayList<Point> pointList = createCurve(eElement);
                            switch (elementType) {
                                case "Point":
                                    Point p = createPoint(eElement);
                                    geometryList.add(p);
                                    System.out.println(p.toString());
                                    break;
                                case "Line":
                                    Line l = new Line(pointList);
                                    System.out.println(l.toString());
                                    geometryList.add(l);
                                    break;
                                case "BezierCurve2":
                                    BezierCurve2 bc2 = new BezierCurve2(pointList);
                                    geometryList.add(bc2);
                                    System.out.println(bc2.toString());
                                    break;
                                case "BezierCurve3":
                                    createCurve(eElement);
                                    BezierCurve3 bc3 = new BezierCurve3(pointList);
                                    geometryList.add(bc3);
                                    System.out.println(bc3.toString());
                                    break;
                                case "BezierPath":
                                    createCurve(eElement);
                                    BezierPath bp = new BezierPath(pointList);
                                    geometryList.add(bp);
                                    System.out.println(bp.toString());
                                    break;
                            }
                        }
                    }
                }
                PrintingData.setGeometryList(new ArrayList<>(geometryList));
                PrintingData.setFile(null);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else System.out.println("File is null!");
    }

    private static ArrayList<Point> createCurve(Element eElement) {
        ArrayList<Point> pointList = new ArrayList<>();
        NodeList nl = eElement.getElementsByTagName("point");
        for (int j = 0; j < nl.getLength(); j++) {
            Element e = (Element) nl.item(j);
            pointList.add(createPoint(e));
        }
        return pointList;
    }

    private static Point createPoint(Element eElement) {
        float x = Float.parseFloat(eElement.getElementsByTagName("xCoord")
                .item(0)
                .getTextContent());
        float y = Float.parseFloat(eElement.getElementsByTagName("yCoord")
                .item(0)
                .getTextContent());
        return new Point(x, y);
    }

}
