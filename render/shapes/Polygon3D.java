package render.shapes;

import render.point.Point3D;
import render.point.PointConvector;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.List;

public class Polygon3D {
    private Point3D[] points;
    private Point3D[] referencePoints;
    private Color color;
    private boolean[] contract;

    public Polygon3D(Color color, Point3D... points) {

        this.color = color;
        this.points = new Point3D[points.length];
        this.referencePoints = new Point3D[points.length];
        this.contract = new boolean[points.length];
        for (int i = 0; i < points.length; i++) {
            this.contract[i] = true;
            Point3D point3D = points[i];
            this.points[i] = new Point3D(point3D.x, point3D.y, point3D.z);
            this.referencePoints[i] = new Point3D(point3D.x, point3D.y, point3D.z);
        }
    }

    public Polygon3D(Color color, List<Point3D> points) {

        this.color = color;
        this.points = new Point3D[points.size()];
        this.referencePoints = new Point3D[points.size()];
        this.contract = new boolean[points.size()];
        for (int i = 0; i < points.size(); i++) {
            this.contract[i] = true;
            Point3D point3D = points.get(i);
            this.points[i] = new Point3D(point3D.x, point3D.y, point3D.z);
            this.referencePoints[i] = new Point3D(point3D.x, point3D.y, point3D.z);
        }
    }

    public Polygon3D(Point3D... points) {
        this.referencePoints = new Point3D[points.length];
        this.color = Color.BLACK;
        this.points = new Point3D[points.length];
        this.contract = new boolean[points.length];
        for (int i = 0; i < points.length; i++) {
            this.contract[i] = true;
            Point3D point3D = points[i];
            this.points[i] = new Point3D(point3D.x, point3D.y, point3D.z);
            this.referencePoints[i] = new Point3D(point3D.x, point3D.y, point3D.z);
        }
    }

    public void render(Graphics g) {
        Polygon polygon = new Polygon();
        for (int i = 0; i < this.points.length; i++) {
            Point p = PointConvector.convertPoint(this.points[i]);
            polygon.addPoint(p.x, p.y);
        }
        g.setColor(this.color);
        g.fillPolygon(polygon);
    }

    public void rotate(boolean cw, double xDegrees, double yDegrees, double zDegrees) {
        for (Point3D p : this.points) {
            if (xDegrees != 0) {
                PointConvector.rotateAxisX(p, cw, xDegrees);
            }
            if (yDegrees != 0) {
                PointConvector.rotateAxisY(p, cw, yDegrees);
            }
            if (zDegrees != 0) {
                PointConvector.rotateAxisZ(p, cw, zDegrees);
            }

        }
    }

    public double getAverageX() {
        double sum = 0;
        for (Point3D p : this.points) {
            sum += p.x;
        }
        return sum / this.points.length;
    }

    public Point3D[] getPoints() {
        return this.points;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void contract(double different, int dimension, double average) {
        for (int i = 0; i < this.points.length; i++) {
            Point3D point = this.points[i];
            Point3D referencePoint = this.referencePoints[i];
            double multi = 0;
            switch (dimension) {
                case (1):
                    if (point.x < average) {
                        multi = 1;
                    } else if (point.x > average) {
                        multi = -1;
                    }
                    if (Math.abs(referencePoint.x - point.x) > different) {
                        this.contract[i] = !this.contract[i];
                    }
                    if (!this.contract[i]) {
                        multi *= -1;
                    }
                    this.points[i] = new Point3D(point.x - (1 * multi), point.y, point.z);
                    break;
                case (2):
                    if (point.y < average) {
                        multi = 1;
                    } else if (point.y > average) {
                        multi = -1;
                    }
                    if (Math.abs(referencePoint.y - point.y) > different) {
                        this.contract[i] = !this.contract[i];
                    }
                    if (!this.contract[i]) {
                        multi *= -1;
                    }
                    this.points[i] = new Point3D(point.x, point.y - (1 * multi), point.z);
                    break;
                case (3):
                    if (point.z < average) {
                        multi = 1;
                    } else if (point.z > average) {
                        multi = -1;
                    }
                    if (Math.abs(referencePoint.z - point.z) > different) {
                        this.contract[i] = !this.contract[i];
                    }
                    if (!this.contract[i]) {
                        multi *= -1;
                    }
                    this.points[i] = new Point3D(point.x, point.y, point.z - (1 * multi));
                    break;
                default:
                    return;
            }
        }
    }
}
