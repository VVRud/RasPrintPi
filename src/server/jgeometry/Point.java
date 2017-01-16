package server.jgeometry;

/**
 * Created by vvrud on 29.11.16.
 *
 * @author VVRud
 */
public class Point extends Geometry {
    private float x;
    private float y;

    public Point(float x, float y) {
        super("Point");
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
