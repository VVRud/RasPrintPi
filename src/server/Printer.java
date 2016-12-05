package server;

import server.data.Analyzer;
import server.data.PrintingData;

import java.util.HashMap;

//import static server.data.PrintingData.PINS_X;
//import static server.data.PrintingData.PINS_Y;
//import static server.data.PrintingData.PINS_Z;

/**
 * Created by vvrud on 18.09.16.
 *
 * @author VVRud
 *         This class provides printing the picture.
 */
public class Printer extends Thread {
//TODO OPEN GPIO
//    private static GpioStepperMotorComponent motorX = new GpioStepperMotorComponent(PINS_X);
//    private static GpioStepperMotorComponent motorY = new GpioStepperMotorComponent(PINS_Y);
//    private static GpioStepperMotorComponent motorZ = new GpioStepperMotorComponent(PINS_Z);
    
    @Override
    public void run() {
        System.out.println("Printer started!");
        HashMap<String, String> options = PrintingData.getOptions();

        int[][] arrayToPrint = Analyzer.analyzeFile();
        String mode = options.get("Mode");
        if (mode.contains("Dots mode")) {
            printPictureDots(arrayToPrint);
        } else if (mode.contains("Drawing mode")) {
            System.out.println("DRAWING MODE IS UNABLE IN THIS VERSION!");
            //printPictureDrawing(arrayToPrint);
        } else {
            System.out.println("UNKNOWN MODE!");
        }
    }

    private void printPictureDots(int[][] arrayToPrint) {
        int direction = 1;
        int point;
        for (int i = 0; i < Analyzer.getRowCount(); i++) {
            for (int j = 0; j < Analyzer.getColumnCount(); j++) {
                point = arrayToPrint[i][j];

                if (point == 1) {
                    /*  motor Z goes down
                        thread.sleep some time
                        motor Z goes up
                     */
                } else if (point == 2) {
                    /*  motor Z goes down
                        thread.sleep some time
                        motor Z goes up
                     */
                } else if (point == 3) {
                    /*  motor Z goes down
                        thread.sleep some time
                        motor Z goes up
                     */
                } else if (point == 4) {
                    /*  motor Z goes down
                        thread.sleep some time
                        motor Z goes up
                     */
                }
                if (direction == 1) {
                    //move right
                } else {
                    //move left
                }
            }
            //motor Y++
            direction = -direction;
        }
        // printing point goes to start point
    }

    private void printPictureDrawing() {
        //TODO DO DRAWING METHOD
    }
}
