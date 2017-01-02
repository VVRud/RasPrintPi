package server.jgeometry;

import java.util.LinkedList;

/**
 * Created by vvrud on 29.11.16.
 *
 * @author VVRud
 */
public class BezierPath {
    private LinkedList<Point> points;

    public BezierPath(LinkedList<Point> list) throws RuntimeException {
        if (list.size() > 3) this.points = list;
        else throw new RuntimeException("Less than 3 points are not available here!");
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public Point getPoint(int i) {
        return points.get(i);
    }

}
