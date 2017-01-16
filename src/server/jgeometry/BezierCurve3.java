package server.jgeometry;

import java.util.ArrayList;

/**
 * Created by vvrud on 29.11.16.
 *
 * @author VVRud
 */
public class BezierCurve3 extends Geometry {

    private Point pStart;
    private Point p1;
    private Point p2;
    private Point pFinish;

    public BezierCurve3(ArrayList<Point> pointList) {
        super("BezierCurve3");
        this.pStart = pointList.get(0);
        this.p1 = pointList.get(1);
        this.p2 = pointList.get(2);
        this.pFinish = pointList.get(3);
    }

    public Point getpStart() {
        return pStart;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Point getpFinish() {
        return pFinish;
    }
}
