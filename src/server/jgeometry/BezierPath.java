package server.jgeometry;

import java.util.ArrayList;

/**
 * Created by vvrud on 29.11.16.
 *
 * @author VVRud
 */
public class BezierPath extends Geometry {
    private ArrayList<Point> points;

    public BezierPath(ArrayList<Point> list) throws RuntimeException {
        super("BezierPath");
        if (list.size() > 3) this.points = list;
        else throw new RuntimeException("Less than 3 points are not available here!");
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public Point getPoint(int i) {
        return points.get(i);
    }

    public int getPathLength() {
        return points.size();
    }

    public String toString() {
        return getType() + "; PL=" + points.size() + "; " + points.get(0).toString() + " ->...-> " +
                points.get((int) (points.size() / 2)).toString() + " ->...-> " +
                points.get(points.size() - 1).toString();
    }
}
