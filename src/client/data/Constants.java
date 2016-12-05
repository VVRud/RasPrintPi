package client.data;

import java.awt.*;

/**
 * Created by vvrud on 10.09.16.
 * @author VVRud
 * This class created to store all constant of this program.
 * It is easier to change them from one place, but changes
 * will be made for all program.
 */
public class Constants {

    public static final String TITLE = "RasPrintPi";
    public static final Dimension CANVAS_SIZE = new Dimension(420, 420);

    //TODO Set default ip and port
    public static final String DEFAULT_IP = "localhost";
    public static final String DEFAULT_PORT = "6565";

    //Errors
    public static final String ERR_TITLE = "ERROR!";
    public static final String IP_ERR = "ERROR! Can't connect to server. Check your IP, please.";
    public static final String PORT_ERR = "ERROR! Can't connect to server. Check your PORT, please.";
    public static final String PORT_NUM_ERR = "ERROR! Can't connect to server. Check your PORT, please.";
    public static final String PORT_ILLEGAL_ARG_ERR = "ERROR! PORT number is out of range. Check the PORT, please.";

    //Data
    public static final String[] SPEED_DATA = {
            "0.4",
            "0.5",
            "0.6",
            "0.7"
    };
    public static final String[] MODE_DATA = {
            "Drawing mode",
            "Dots mode"
    };
    public static final String[] INTENSITY_DATA = {
            "0.4",
            "0.6",
            "0.8"
    };
}
