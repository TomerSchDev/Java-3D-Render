package render.point;

import render.Display;
import java.awt.Point;

public class PointConvector {
    private static double scale = 1;
    private static double myScale = 1000;


    public static Point convertPoint(Point3D point3D) {
        double x3d = point3D.y * scale;
        double y3d = point3D.z * scale;
        double depth = point3D.x * scale;
        double[] newVal = scale(x3d, y3d, depth);
        int x2d = (int) ((Display.WIDTH / 2) + newVal[0]);
        int y2d = (int) ((Display.HEIGHT / 2) - newVal[1]);
        return new Point(x2d, y2d);
    }

    public static double[] scale(double x3d, double y3d, double depth) {
        double dis = Math.sqrt(x3d * x3d + y3d * y3d);
        double theta = Math.atan2(y3d, x3d);
        double depth2 = 15 - depth;
        double localScale = Math.abs(myScale / (depth2 + myScale));
        dis *= localScale;
        double[] newVal = new double[2];
        newVal[0] = dis * Math.cos(theta);
        newVal[1] = dis * Math.sin(theta);
        return newVal;
    }

    public static void rotateAxisX(Point3D p, boolean cw, double xDegrees) {
        double r = Math.sqrt(p.y * p.y + p.z * p.z);
        double theta = Math.atan2(p.z, p.y);
        theta += 2 * Math.toRadians(xDegrees) * (cw ? -1 : 1);
        p.y = r * Math.cos(theta);
        p.z = r * Math.sin(theta);
    }

    public static void rotateAxisY(Point3D p, boolean cw, double yDegrees) {
        double r = Math.sqrt(p.x * p.x + p.z * p.z);
        double theta = Math.atan2(p.x, p.z);
        theta += 2 * Math.toRadians(yDegrees) * (cw ? -1 : 1);
        p.x = r * Math.sin(theta);
        p.z = r * Math.cos(theta);
    }

    public static void rotateAxisZ(Point3D p, boolean cw, double zDegrees) {
        double r = Math.sqrt(p.y * p.y + p.x * p.x);
        double theta = Math.atan2(p.y, p.x);
        theta += 2 * Math.toRadians(zDegrees) * (cw ? -1 : 1);
        p.y = r * Math.sin(theta);
        p.x = r * Math.cos(theta);
    }
}
