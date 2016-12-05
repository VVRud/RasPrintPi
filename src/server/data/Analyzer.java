package server.data;

import java.io.*;

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

    private static int[][] analyzeTxtFile(File file) {
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

    public static int[][] analyzeFile() throws RuntimeException {
        File file = PrintingData.getFile();
        String fileName = file.getName();

        if (fileName.contains(".txt")) {
            return analyzeTxtFile(file);
        } else {
            throw new RuntimeException("Unknown file type.");
        }
    }
}
