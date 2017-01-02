package server.jgeometry;

/**
 * Created by vvrud on 11.12.16.
 *
 * @author VVRud
 */
public class Line {
    private Point pointStart;
    private Point pointEnd;

    public Line(Point p1, Point p2) {
        this.pointStart = p1;
        this.pointEnd = p2;
    }

    public Line(int x1, int y1, int x2, int y2) {
        this.pointStart = new Point(x1, y1);
        this.pointEnd = new Point(x2, y2);
    }

    public Point getPointStart() {
        return pointStart;
    }

    public Point getPointEnd() {
        return pointEnd;
    }
}
