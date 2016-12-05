package server.data;

import java.io.Serializable;

/**
 * Created by vvrud on 05.11.16.
 *
 * @author VVRud
 */
public class PrintingOptions implements Serializable {
    private static float speed;
    private static float intensity;
    private static String mode;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        PrintingOptions.speed = speed;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        PrintingOptions.intensity = intensity;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        PrintingOptions.mode = mode;
    }
}
