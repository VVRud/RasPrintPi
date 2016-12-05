package client.data;

/**
 * Created by vvrud on 02.11.16.
 *
 * @author VVRud
 */
public class CoordsData {
    private static int xSize;
    private static int ySize;
    private static int xPos;
    private static int yPos;

    public static int getxSize() {
        return xSize;
    }

    public static void setxSize(int xSize) {
        CoordsData.xSize = xSize;
    }

    public static int getySize() {
        return ySize;
    }

    public static void setySize(int ySize) {
        CoordsData.ySize = ySize;
    }

    public static int getxPos() {
        return xPos;
    }

    public static void setxPos(int xPos) {
        CoordsData.xPos = xPos;
    }

    public static int getyPos() {
        return yPos;
    }

    public static void setyPos(int yPos) {
        CoordsData.yPos = yPos;
    }
}
