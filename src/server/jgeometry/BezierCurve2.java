package server.jgeometry;

import java.util.ArrayList;

/**
 * Created by vvrud on 29.11.16.
 *
 * @author VVRud
 */
public class BezierCurve2 extends Geometry {
    private Point pStart;
    private Point p1;
    private Point pFinish;

    public BezierCurve2(ArrayList<Point> pointList) {
        super("BezierCurve2");
        this.pStart = pointList.get(0);
        this.p1 = pointList.get(1);
        this.pFinish = pointList.get(2);
    }

    public Point getpStart() {
        return pStart;
    }

    public Point getP1() {
        return p1;
    }

    public Point getpFinish() {
        return pFinish;
    }

    public String toString() {
        return getType() + " [ " + pStart + "->" + p1 + "->" + pFinish + " ]";
    }
}
