package client;

/**
 * Created by vvrud on 10.09.16.
 * @author VVRud
 * This class created to store all constant of this program.
 * It is easier to change them from one place, but changes
 * will be made for all program.
 */
public class Constants {
    public static final String TITLE = "RasPrintPi";
    public static final int CELL_SIZE = 5;
    //TODO Set default ip and port
    public static final String DEFAULT_IP = "localhost";
    public static final String DEFAULT_PORT = "6565";

    //TODO Set default speed, mode and intensity
    public static final float DEFAULT_SPEED = 0.4f;
    public static final String DEFAULT_MODE = "defaultMode";
    public static final float DEFAULT_INTENSITY = 0.4f;

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
