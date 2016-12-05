package jgeometry;

/**
 * Created by vvrud on 29.11.16.
 *
 * @author VVRud
 */
public class BezierCurve3 {

    private Point pStart;
    private Point p1;
    private Point p2;
    private Point pFinish;

    public BezierCurve3(Point pStart, Point p1, Point p2, Point pFinish) {
        this.pStart = pStart;
        this.p1 = p1;
        this.p2 = p2;
        this.pFinish = pFinish;
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
