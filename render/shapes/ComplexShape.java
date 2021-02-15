package render.shapes;

import render.point.Point3D;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComplexShape {
    private List<Polygon3D> polygons;
    private Color color;

    public ComplexShape(Color color, Polygon3D... polygons) {
        this.color = color;
        this.polygons = new ArrayList<>();
        for (int i = 0; i < polygons.length; i++) {
            if (polygons[i] == null) {
                continue;
            }
            Polygon3D p = polygons[i];
            this.polygons.add(p);
        }
    }

    public ComplexShape(Polygon3D... polygons) {
        this.color = Color.ORANGE;
        this.polygons = new ArrayList<>();
        for (int i = 0; i < polygons.length; i++) {
            if (polygons[i] == null) {
                continue;
            }
            Polygon3D p = polygons[i];
            this.polygons.add(p);
        }
    }

    public ComplexShape(List<Polygon3D> polygons) {
        this.color = Color.ORANGE;
        this.polygons = polygons;
    }

    public void rotate(boolean cw, double xDegrees, double yDegrees, double zDegrees) {
        for (Polygon3D poly : this.polygons) {
            poly.rotate(cw, xDegrees, yDegrees, zDegrees);
        }
        this.sortPolygons();
    }

    public void render(Graphics g) {
        for (Polygon3D poly : this.polygons) {
            poly.render(g);
        }
    }

    private double getAverageX() {
        double sum = 0, numPoints = 0;
        for (Polygon3D poly : this.polygons) {
            for (Point3D point : poly.getPoints()) {
                sum += point.x;
                numPoints++;
            }
        }
        return sum / numPoints;
    }

    private double getAverageY() {
        double sum = 0, numPoints = 0;
        for (Polygon3D poly : this.polygons) {
            for (Point3D point : poly.getPoints()) {
                sum += point.y;
                numPoints++;
            }
        }
        return sum / numPoints;
    }

    private double getAverageZ() {
        double sum = 0, numPoints = 0;
        for (Polygon3D poly : this.polygons) {
            for (Point3D point : poly.getPoints()) {
                sum += point.z;
                numPoints++;
            }
        }
        return sum / numPoints;
    }

    private void sortPolygons() {
        this.polygons.sort(new Comparator<Polygon3D>() {
            @Override
            public int compare(Polygon3D o1, Polygon3D o2) {
                double dip = o1.getAverageX() - o2.getAverageX();
                if (dip > 0) {
                    return 1;
                } else if (dip < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    public void contract(double different, int dimension) {
        double average = 0;
        switch (dimension) {
            case (1):
                average = this.getAverageX();
                break;
            case (2):
                average = this.getAverageY();
                break;
            case (3):
                average = this.getAverageZ();
                break;
            default:
                average = 0;
        }
        for (Polygon3D polygon : this.polygons) {
            polygon.contract(different, dimension,average );
        }
    }

    private void setPolygonsColor(Color color) {
        for (Polygon3D polygon : this.polygons) {
            polygon.setColor(color);
        }
    }
}
