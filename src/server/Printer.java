package server;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;
import server.data.Analyzer;
import server.data.PrintingData;
import server.io.ReceiverServer;
import server.jgeometry.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vvrud on 18.09.16.
 *
 * @author VVRud
 *         This class provides printing the picture.
 */
public class Printer extends Thread {

    private static final GpioController gpio = GpioFactory.getInstance();

    private static final int MOTOR_X = 0;
    private static final int MOTOR_Y = 1;

    private static final GpioPinDigitalOutput[] PINS_X = {
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, PinState.LOW)};

    private static final GpioPinDigitalOutput[] PINS_Y = {
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, PinState.LOW)};

    private static final GpioPinDigitalOutput[] PINS_Z = {
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_14, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_30, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, PinState.LOW)};

    private static GpioStepperMotorComponent motorX = new GpioStepperMotorComponent(PINS_X);
    private static GpioStepperMotorComponent motorY = new GpioStepperMotorComponent(PINS_Y);
    private static GpioStepperMotorComponent motorZ = new GpioStepperMotorComponent(PINS_Z);
    private static GpioPinPwmOutput pwm = gpio.provisionPwmOutputPin(RaspiPin.GPIO_01);
    boolean zUp = true;
    private MotorController controllerX;
    private MotorController controllerY;
    private int state;
    private Point currentPoint = new Point(0, 0);

    public Printer(int currentState) {
        this.state = currentState;
    }

    @Override
    public void run() {
        System.out.println("Printer started!");
        setMotorsDefaults();
        setPwmDefaults();

        if (state == ReceiverServer.TXT) {
            printTxt();
        } else if (state == ReceiverServer.XML) {
            printXml();
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

    private void printPictureDrawing(int[][] arrayToPrint) {
        //TODO DO PICTURE DRAWING METHOD
    }

    public void stopPrinting() {
        System.out.println("PRINTING INTERRUPTED!");
        if (!zUp) {
            motorZUp();
        }
        motorsToHomeSpot();
        interrupt();
    }

    private void printXml() {
        Analyzer.analyzeXmlFile();

        ArrayList<Geometry> list = PrintingData.getGeometryList();

        for (Geometry draw : list) {
            String type = draw.getType();

            if (type.equals("Point")) {
                Point p = (Point) draw;
                motorsMove(p);
                currentPoint = p;
                motorZDown();
                motorZUp();
            } else if (type.equals("Line")) {
                Line l = (Line) draw;
                motorsMove(l.getPointStart());
                motorZDown();
                motorsMove(l.getPointEnd());
                motorZUp();
            } else if (type.equals("BezierCurve2")) {
                BezierCurve2 bc2 = (BezierCurve2) draw;
                motorsMove(bc2.getpStart());
                motorZDown();
                for (float i = 0; i <= 1; i += 0.01f) {
                    Point p = calculatePoint2(i, bc2.getpStart(), bc2.getP1(), bc2.getpFinish());
                    motorsMove(p);
                }
                motorZUp();
            } else if (type.equals("BezierCurve3")) {
                BezierCurve3 bc3 = (BezierCurve3) draw;
                motorsMove(bc3.getpStart());
                motorZDown();
                for (float i = 0; i <= 1; i += 0.01f) {
                    Point p = calculatePoint3(i, bc3.getpStart(), bc3.getP1(), bc3.getP2(), bc3.getpFinish());
                    motorsMove(p);
                }
                motorZUp();
            } else if (type.equals("BezierPath")) {
                BezierPath path = (BezierPath) draw;
                Point p0 = path.getPoint(0);
                Point p1;
                Point p2;
                Point p3;

                motorsMove(p0);
                motorZDown();
                for (int j = 0; j < path.getPathLength() - 1; j += 3) {
                    p1 = path.getPoint(j + 1);
                    p2 = path.getPoint(j + 2);
                    p3 = path.getPoint(j + 3);

                    for (float i = 0; i <= 1; i += 0.01f) {
                        Point p = calculatePoint3(i, p0, p1, p2, p3);
                        motorsMove(p);
                    }
                    p0 = p3;
                }
                motorZUp();
            }
        }
        motorsToHomeSpot();
        pwm.setPwm(0);
        Server.getSender().sendFinishing();
    }

    private void printTxt() {
        HashMap<String, String> options = PrintingData.getOptions();

        int[][] arrayToPrint = Analyzer.analyzeTxtFile();
        String mode = options.get("Mode");
        switch (mode) {
            case "Dots mode":
                printPictureDots(arrayToPrint);
                break;
            case "Drawing mode":
                printPictureDrawing(arrayToPrint);
                break;
            default:
                System.out.println("UNKNOWN MODE!");
                break;
        }
    }

    private void setMotorsDefaults() {
        byte[] double_step_sequence = new byte[4];
        double_step_sequence[0] = (byte) 0b0011;
        double_step_sequence[1] = (byte) 0b0110;
        double_step_sequence[2] = (byte) 0b1100;
        double_step_sequence[3] = (byte) 0b1001;

        motorX.setStepInterval(2);
        motorY.setStepInterval(2);
        motorZ.setStepInterval(2);

        motorX.setStepSequence(double_step_sequence);
        motorY.setStepSequence(double_step_sequence);
        motorZ.setStepSequence(double_step_sequence);

        motorX.setStepsPerRevolution(2038);
        motorY.setStepsPerRevolution(2038);
        motorZ.setStepsPerRevolution(2038);
    }

    private void setPwmDefaults() {
        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(1000);
        com.pi4j.wiringpi.Gpio.pwmSetClock(500);
        pwm.setPwm(1000);
    }

    private void motorZUp() {
        motorZ.step(510);
        zUp = true;
    }

    private void motorZDown() {
        motorZ.step(-510);
        zUp = false;
    }

    private void motorsMove(Point point) {
        controllerX = new MotorController(MOTOR_X);
        controllerY = new MotorController(MOTOR_Y);

        float deltaX = point.getX() - currentPoint.getX();
        float deltaY = point.getY() - currentPoint.getY();

        setDelay(deltaX, deltaY);

        controllerX.setStep((int) (deltaX * 18));
        controllerY.setStep((int) (deltaY * 18));

        controllerX.start();
        controllerY.start();

        try {
            controllerX.join();
            controllerY.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        currentPoint = point;
    }

    private void motorsToHomeSpot() {
        controllerX = new MotorController(MOTOR_X);
        controllerY = new MotorController(MOTOR_Y);

        float deltaX = 0 - currentPoint.getX();
        float deltaY = 0 - currentPoint.getY();

        setDelay(deltaX, deltaY);

        controllerX.setStep((int) (deltaX * 18));
        controllerY.setStep((int) (deltaY * 18));

        controllerX.start();
        controllerY.start();

        try {
            controllerX.join();
            controllerY.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        currentPoint = new Point(0, 0);
    }

    private Point calculatePoint2(float t, Point p0, Point p1, Point p2) {
        float x0 = p0.getX();
        float x1 = p1.getX();
        float x2 = p2.getX();

        float y0 = p0.getY();
        float y1 = p1.getY();
        float y2 = p2.getY();

        float u = 1 - t;
        float tt = t * t;
        float uu = u * u;

        float x = uu * x0;
        x += 2 * t * u * x1;
        x += tt * x2;

        float y = uu * y0;
        y += 2 * t * u * y1;
        y += tt * y2;

        return new Point(x, y);
    }

    private Point calculatePoint3(float t, Point p0, Point p1, Point p2, Point p3) {
        float x0 = p0.getX();
        float x1 = p1.getX();
        float x2 = p2.getX();
        float x3 = p3.getX();

        float y0 = p0.getY();
        float y1 = p1.getY();
        float y2 = p2.getY();
        float y3 = p3.getY();

        float u = 1 - t;
        float tt = t * t;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * t;

        float x = uuu * x0; //first term
        x += 3 * uu * t * x1; //second term
        x += 3 * u * tt * x2; //third term
        x += ttt * x3; //fourth term

        float y = uuu * y0; //first term
        y += 3 * uu * t * y1; //second term
        y += 3 * u * tt * y2; //third term
        y += ttt * y3; //fourth term

        return new Point(x, y);
    }

    private void setDelay(float deltaX, float deltaY) {
        float modDeltaX = Math.abs(deltaX);
        float modDeltaY = Math.abs(deltaY);
        if (modDeltaX < modDeltaY) {
            motorY.setStepInterval(2);
            float delay = (2 * modDeltaY) / modDeltaX;
            int mod = (int) ((delay % (long) delay) * 1000000);
            System.out.println("DELAY: " + delay + " " + mod);
            motorX.setStepInterval((long) delay, mod);
        } else {
            motorX.setStepInterval(2);
            float delay = (2 * modDeltaX) / modDeltaY;
            int mod = (int) ((delay % (long) delay) * 1000000);
            System.out.println("DELAY: " + delay + " " + mod);
            motorY.setStepInterval((long) delay, mod);
        }
    }

    private class MotorController extends Thread {
        private int type;
        private int step;

        private MotorController(int type) {
            this.type = type;
        }

        @Override
        public void run() {
            if (type == MOTOR_X) {
                motorX.step(step);
            } else if (type == MOTOR_Y) {
                motorY.step(step);
            }
            interrupt();
        }

        void setStep(int step) {
            this.step = step;
        }
    }
}
