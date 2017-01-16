package server.data;

import server.jgeometry.Geometry;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vvrud on 18.09.16.
 *
 * @author VVRud
 *         This class stores all the printing options.
 */
public class PrintingData {

    private static ArrayList<Geometry> geometryList;
    private static File file;
    private static HashMap<String, String> options;

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        PrintingData.file = file;
    }

    public static HashMap<String, String> getOptions() {
        return options;
    }

    public static void setOptions(HashMap<String, String> options) {
        PrintingData.options = options;
    }

    public static ArrayList<Geometry> getGeometryList() {
        return geometryList;
    }

    public static void setGeometryList(ArrayList<Geometry> geometryList) {
        PrintingData.geometryList = geometryList;
    }
}