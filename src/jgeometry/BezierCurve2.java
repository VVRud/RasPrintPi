package jgeometry;

/**
 * Created by vvrud on 29.11.16.
 *
 * @author VVRud
 */
public class BezierCurve2 {

    private Point pStart;
    private Point p1;
    private Point pFinish;

    public BezierCurve2(Point pStart, Point p1, Point pFinish) {
        this.pStart = pStart;
        this.p1 = p1;
        this.pFinish = pFinish;
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

}
