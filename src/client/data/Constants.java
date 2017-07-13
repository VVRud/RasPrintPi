package client.data;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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

    public static final int AREA_WIDTH = 620;
    public static final int AREA_HEIGHT = 620;
    public static final Dimension DRAWING_AREA_SIZE = new Dimension(AREA_WIDTH, AREA_HEIGHT);
    public static final FileFilter FILTER = new FileNameExtensionFilter("JPEG or XML files",
            "xml", "jpg", "jpeg");

    public static final String DEFAULT_IP = "10.10.100.29";
    public static final String DEFAULT_PORT = "6565";

    //Modes
    public static final int BEZ_MODE = 1;
    public static final int JPG_MODE = 2;

    //Errors
    public static final String ERR_TITLE = "ERROR!";
    public static final String IP_ERR = "ERROR! Can't connect to server. Check your IP, please.";
    public static final String PORT_ERR = "ERROR! Can't connect to server. Check your PORT, please.";
    public static final String PORT_NUM_ERR = "ERROR! Can't connect to server. Check your PORT, please.";
    public static final String PORT_ILLEGAL_ARG_ERR = "ERROR! PORT number is out of range. Check the PORT, please.";
    public static final String CHOOSING_FILE_ERR = "You might have chosen file without extension." +
            "Choose the right file, please.";

    //Warnings
    public static final String WARN_TITLE = "WARNING!";
    public static final String EMPTY_DRAWING_LIST = "Printing is impossible now, you did'nt draw anything.";
    public static final String UNKNOWN_MODE = "Unknown mode! Please, try again.";

    //Messages
    public static final String MESSAGE_TITLE = "PROGRAM MESSAGE";
    public static final String FINISHED_PRINTING_MSG = "Printer finished printing.";

    //Instructions
    public static final int INTERRUPT = 0;
    public static final int TXT = 1;
    public static final int XML = 2;
    public static final int SHUTDOWN = 3;
    public static final int PRINTING_FINISHED = 9;

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
    public static final String[] MODE_ANALYZE_DATA = {
            "Analyze Drawing",
            "Analyze Chosen File"
    };
}
