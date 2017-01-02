package server.data;

import com.pi4j.io.gpio.*;

import java.io.File;
import java.util.HashMap;

/**
 * Created by vvrud on 18.09.16.
 *
 * @author VVRud
 *         This class stores all the printing options.
 */
public class PrintingData {

    private static final GpioController gpio = GpioFactory.getInstance();
    //TODO set right GPIO pins
    public static final GpioPinDigitalOutput[] PINS_X = {
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW)};
    public static final GpioPinDigitalOutput[] PINS_Y = {
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW)};
    public static final GpioPinDigitalOutput[] PINS_Z = {
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW)};
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
}