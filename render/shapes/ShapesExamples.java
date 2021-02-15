package render.shapes;

import render.point.Point3D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class ShapesExamples {
    public static Color randomColor() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public static ComplexShape createTriangle(int s) {
        Polygon3D polygon3D = new Polygon3D(
                Color.WHITE,
                new Point3D(0, 0, 0),
                new Point3D(0, 0, s),
                new Point3D(0, s, 0)
        );
        return new ComplexShape(polygon3D);
    }

    public static ComplexShape createDisk(int s) {
        List<Polygon3D> polygon3DS = new ArrayList<>();
        List<List<Point3D>> points = new ArrayList<>();
        List<Point3D> zeroPoints = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            points.add(new ArrayList<>());
            double y = i;
            zeroPoints.add(new Point3D(0, y, 0));
            for (int j = 0; j <= s; j++) {
                double x = j;
                double z = circleEquation(s, x);
                points.get(i).add(new Point3D(x, y, -z));
                points.get(i).add(new Point3D(x, y, z));
                points.get(i).add(new Point3D(-x, y, -z));
                points.get(i).add(new Point3D(-x, y, z));
            }
        }
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.get(i).size() - 4; j++) {
                polygon3DS.add(
                        new Polygon3D(
                                randomColor(), zeroPoints.get(i), points.get(i).get(j), points.get(i).get(j + 4)));
            }
        }
        return new ComplexShape(polygon3DS);
    }

    public static ComplexShape createCylinder(int s) {
        List<Polygon3D> polygon3DS = new ArrayList<>();
        List<Point3D> coverPoints = new ArrayList<>();
        for (int i = 0; i <= s; i++) {
            double x = i;
            double z = circleEquation(s, x);
            coverPoints.add(new Point3D(x, s, z));
            coverPoints.add(new Point3D(x, s, -z));
            coverPoints.add(new Point3D(-x, s, z));
            coverPoints.add(new Point3D(-x, s, -z));
            coverPoints.add(new Point3D(x, -s, z));
            coverPoints.add(new Point3D(x, -s, -z));
            coverPoints.add(new Point3D(-x, -s, z));
            coverPoints.add(new Point3D(-x, -s, -z));
        }
        Point3D zeroTop = new Point3D(0, s, 0);
        Point3D zeroBottom = new Point3D(0, -s, 0);
        for (int i = 0; i < coverPoints.size() - 8; i++) {
            Point3D addPoint;
            if (i % 8 < 4) {
                addPoint = zeroTop;
            } else {
                addPoint = zeroBottom;
            }
            polygon3DS.add(new Polygon3D(randomColor(), addPoint, coverPoints.get(i), coverPoints.get(i + 8)));
        }
        for (int i = 0; i < coverPoints.size() - 12; i++) {
            polygon3DS.add(
                    new Polygon3D(Color.WHITE, coverPoints.get(i), coverPoints.get(i + 8), coverPoints.get(i + 12),
                                  coverPoints.get(i + 4)
                    ));
        }
        return new ComplexShape(polygon3DS);
    }

    public static ComplexShape createCones(int s) {
        List<Polygon3D> polygon3DS = new ArrayList<>();
        List<Point3D> points = new ArrayList<>();
        for (double i = 0; i <= s; i += 0.5) {
            double x = i;
            double y = circleEquation(s, x);
            points.add(new Point3D(x, y, s));
            points.add(new Point3D(-x, y, s));
            points.add(new Point3D(x, -y, s));
            points.add(new Point3D(-x, -y, s));
            points.add(new Point3D(x, y, -s));
            points.add(new Point3D(-x, y, -s));
            points.add(new Point3D(x, -y, -s));
            points.add(new Point3D(-x, -y, -s));
        }
        Point3D zeroPoint = new Point3D(0, 0, 2 * s);
        Point3D zeroPoint2 = new Point3D(0, 0, -2 * s);
        for (int i = 0; i < points.size() - 12; i++) {
            if (i % 8 < 4) {
                polygon3DS.add(new Polygon3D(randomColor(), zeroPoint, points.get(i), points.get(i + 8)));
            } else {
                polygon3DS.add(new Polygon3D(randomColor(), zeroPoint2, points.get(i), points.get(i + 8)));
            }
            polygon3DS.add(new Polygon3D(randomColor(), points.get(i), points.get(i + 8), points.get(i + 12),
                                         points.get(i + 4)
            ));
        }
        return new ComplexShape(polygon3DS);
    }

    public static double circleEquation(double r, double x) {
        return -Math.sqrt((r * r) - (x * x));
    }

    public static List<Point3D> getCirclePointsList(int r, Point3D centerPoint, int dimension) {
        return getCirclePointsList(r, centerPoint, dimension, 0);
    }

    public static List<Point3D> getCirclePointsList(int r, Point3D centerPoint, int dimension, double otherValue) {
        List<Point3D> points = new ArrayList<>();
        Stack<Point3D> mirrorPoints = new Stack<>();
        switch (dimension) {
            case (1):
                for (int i = -r; i < r; i++) {
                    double z = Math.sqrt((r * r) - (Math.pow((double) i - centerPoint.y, 2))) - centerPoint.z;
                    points.add(new Point3D(otherValue, i, z));
                    mirrorPoints.push(new Point3D(otherValue, i, -z));
                }
                while (!mirrorPoints.isEmpty()) {
                    points.add(mirrorPoints.pop());
                }
                break;
            case (2):
                for (int i = -r; i < r; i++) {
                    double x = i;
                    double z = Math.sqrt((r * r) - (Math.pow(x - centerPoint.x, 2))) - centerPoint.z;
                    points.add(new Point3D(x, otherValue, z));
                    mirrorPoints.push(new Point3D(x, otherValue, -z));
                }
                while (!mirrorPoints.isEmpty()) {
                    points.add(mirrorPoints.pop());
                }
                break;
            case (3):
                for (int i = -r; i < r; i++) {
                    double x = i;
                    double y = Math.sqrt((r * r) - (Math.pow(x - centerPoint.x, 2))) - centerPoint.y;
                    points.add(new Point3D(x, y, otherValue));
                    mirrorPoints.push(new Point3D(x, -y, otherValue));
                }
                while (!mirrorPoints.isEmpty()) {
                    points.add(mirrorPoints.pop());
                }
                break;
        }
        return points;
    }

    public static ComplexShape createCircle(int r) {
        return new ComplexShape(new Polygon3D(randomColor(), getCirclePointsList(r, new Point3D(0, 0, 0), 3)));
    }

    public static ComplexShape createCube(int s) {
        Point3D p1 = new Point3D(s / 2, -s / 2, -s / 2);
        Point3D p2 = new Point3D(s / 2, s / 2, -s / 2);
        Point3D p3 = new Point3D(s / 2, s / 2, s / 2);
        Point3D p4 = new Point3D(s / 2, -s / 2, s / 2);
        Point3D p5 = new Point3D(-s / 2, -s / 2, -s / 2);
        Point3D p6 = new Point3D(-s / 2, s / 2, -s / 2);
        Point3D p7 = new Point3D(-s / 2, s / 2, s / 2);
        Point3D p8 = new Point3D(-s / 2, -s / 2, s / 2);
        return new ComplexShape(
                new Polygon3D(Color.BLUE, p5, p6, p7, p8),
                new Polygon3D(Color.WHITE, p1, p2, p6, p5),
                new Polygon3D(Color.PINK, p1, p5, p8, p4),
                new Polygon3D(Color.YELLOW, p2, p6, p7, p3),
                new Polygon3D(Color.GREEN, p4, p3, p7, p8),
                new Polygon3D(Color.RED, p1, p2, p3, p4)
        );
    }

    public static ComplexShape createDiamond(int s) {
        Point3D p1 = new Point3D(s / 2, -s / 2, -s / 2);
        Point3D p2 = new Point3D(s / 2, s / 2, -s / 2);
        Point3D p3 = new Point3D(s / 2, s / 2, s / 2);
        Point3D p4 = new Point3D(s / 2, -s / 2, s / 2);
        Point3D p5 = new Point3D(-s / 2, -s / 2, -s / 2);
        Point3D p6 = new Point3D(-s / 2, s / 2, -s / 2);
        Point3D p7 = new Point3D(-s / 2, s / 2, s / 2);
        Point3D p8 = new Point3D(-s / 2, -s / 2, s / 2);
        Point3D p9 = new Point3D(s * (3 / 2), 0, 0);
        Point3D p10 = new Point3D(-s * (3 / 2), 0, 0);
        Point3D p11 = new Point3D(0, s * (3 / 2), 0);
        Point3D p12 = new Point3D(0, -s * (3 / 2), 0);
        Point3D p13 = new Point3D(0, 0, s * (3 / 2));
        Point3D p14 = new Point3D(0, 0, -s * (3 / 2));

        return new ComplexShape(
                //-------------------------------
                new Polygon3D(randomColor(), p9, p1, p2),
                new Polygon3D(randomColor(), p9, p2, p3),
                new Polygon3D(randomColor(), p9, p3, p4),
                new Polygon3D(randomColor(), p9, p1, p4),
                //----------------------------
                //  new Polygon3D(Color.BLUE, p5, p6, p7, p8),
                //-------------------------------
                new Polygon3D(randomColor(), p10, p5, p6),
                new Polygon3D(randomColor(), p10, p6, p7),
                new Polygon3D(randomColor(), p10, p7, p8),
                new Polygon3D(randomColor(), p10, p8, p5),
                //----------------------------
                //  new Polygon3D(Color.YELLOW, p2, p6, p7, p3),
                //-------------------------------
                new Polygon3D(randomColor(), p11, p2, p6),
                new Polygon3D(randomColor(), p11, p3, p2),
                new Polygon3D(randomColor(), p11, p6, p7),
                new Polygon3D(randomColor(), p11, p7, p3),

                //----------------------------
                //new Polygon3D(Color.PINK, p1, p5, p8, p4),
                //-------------------------------
                new Polygon3D(randomColor(), p12, p1, p5),
                new Polygon3D(randomColor(), p12, p8, p4),
                new Polygon3D(randomColor(), p12, p5, p8),
                new Polygon3D(randomColor(), p12, p1, p4),

                //----------------------------
                //new Polygon3D(Color.GREEN, p4, p3, p7, p8),
                //-------------------------------
                new Polygon3D(randomColor(), p13, p4, p3),
                new Polygon3D(randomColor(), p13, p3, p7),
                new Polygon3D(randomColor(), p13, p7, p8),
                new Polygon3D(randomColor(), p13, p4, p8),
                //----------------------------
                //new Polygon3D(Color.WHITE, p1, p2, p6, p5),
                //-------------------------------
                new Polygon3D(randomColor(), p14, p1, p2),
                new Polygon3D(randomColor(), p14, p2, p6),
                new Polygon3D(randomColor(), p14, p6, p5),
                new Polygon3D(randomColor(), p14, p1, p5)
                //---------------------------------
        );
    }

    public static ComplexShape createOctahedron(int s) {
        Point3D p1 = new Point3D(0, 0, s);
        Point3D p2 = new Point3D(-0.5 * s, 0.5 * s, 0);
        Point3D p3 = new Point3D(0.5 * s, 0.5 * s, 0);
        Point3D p4 = new Point3D(0.5 * s, -0.5 * s, 0);
        Point3D p5 = new Point3D(-0.5 * s, -0.5 * s, 0);
        Point3D p6 = new Point3D(0, 0, -s);
        return new ComplexShape(
                new Polygon3D(Color.RED, p1, p2, p3)
                , new Polygon3D(Color.BLUE, p1, p3, p4)
                , new Polygon3D(Color.YELLOW, p1, p4, p5)
                , new Polygon3D(Color.GREEN, p1, p2, p5)
                , new Polygon3D(Color.WHITE, p2, p3, p4, p5)
                , new Polygon3D(Color.PINK, p6, p2, p3)
                , new Polygon3D(Color.MAGENTA, p6, p3, p4)
                , new Polygon3D(Color.DARK_GRAY, p6, p4, p5)
                , new Polygon3D(Color.CYAN, p6, p2, p5)
        );
    }

    public static ComplexShape getRandomShape(int size) {
        Random random = new Random();
        ComplexShape shape = null;
        int choice =random.nextInt(8);
        switch (choice) {
            case (0):
                shape=createCube(size);
                break;
            case (1):
                shape=createDiamond(size);
                break;
            case (2):
                shape=createCones(size);
                break;
            case (3):
                shape=createCylinder(size);
                break;
            case (4):
                shape=createOctahedron(size);
                break;
            case (5):
                shape=createTriangle(size);
                break;
            case (6):
                shape=createDisk(size);
                break;
            case (7):
                shape=createCircle(size);
                break;
        }
        return shape;
    }
}
