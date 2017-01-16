package server.jgeometry;

import java.util.ArrayList;

/**
 * Created by vvrud on 11.12.16.
 *
 * @author VVRud
 */
public class Line extends Geometry {

    private Point pointStart;
    private Point pointEnd;

    public Line(ArrayList<Point> pointList) {
        super("Line");
        this.pointStart = pointList.get(0);
        this.pointEnd = pointList.get(1);
    }

    public Point getPointStart() {
        return pointStart;
    }

    public Point getPointEnd() {
        return pointEnd;
    }
}
