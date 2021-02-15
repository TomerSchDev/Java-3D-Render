package render.shapes;

import render.point.Point3D;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomShape {
    public static ComplexShape createCustomShape(File file) {
        List<String> lines = getLinesFromFile(file);
        List<Polygon3D> polygonList = new ArrayList<>();
        int startingLine = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("Polygon")) {
                List<String> polygonLines = new ArrayList<>();
                for (int j = startingLine; j < i; j++) {
                    polygonLines.add(lines.get(j));
                }
                startingLine = i + 1;
                polygonList.add(getPolygonFromLines(polygonLines));
            }
        }
        return new ComplexShape(polygonList);
    }

    private static Polygon3D getPolygonFromLines(List<String> lines) {
        Color color = getColorFromLines(lines);
        List<Point3D> points = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("point:")) {
                points.add(getPointFromLine(lines.get(i).replace("point:", "")));
            }
        }
        return new Polygon3D(color, points);
    }

    private static Point3D getPointFromLine(String line) {
        ///x,y,z
        double x, y, z;
        x = 0;
        y = 0;
        z = 0;
        int startingChar = 0;
        int times = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ',') {
                String value = line.substring(startingChar, i);
                switch (times) {
                    case (0):
                        x = Double.parseDouble(value);
                        break;
                    case (1):
                        y = Double.parseDouble(value);
                        break;
                    case (2):
                        z = Double.parseDouble(value);
                        break;

                }
                startingChar = i + 1;
                times++;
            }
        }
        return new Point3D(x, y, z);
    }

    private static Color getColorFromLines(List<String> lines) {
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("black", Color.black);
        colorMap.put("blue", Color.blue);
        colorMap.put("cyan", Color.cyan);
        colorMap.put("gray", Color.gray);
        colorMap.put("lightGray", Color.lightGray);
        colorMap.put("green", Color.green);
        colorMap.put("orange", Color.orange);
        colorMap.put("pink", Color.pink);
        colorMap.put("red", Color.red);
        colorMap.put("white", Color.white);
        colorMap.put("yellow", Color.yellow);
        Color color = Color.WHITE;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("Color:")) {
                String colorString=lines.get(i).replace("Color:", "").toLowerCase();
                color = colorMap.get(colorString);
            }
        }
        return color;
    }

    public static List<String> getLinesFromFile(File file) {
        List<String> lines = new ArrayList<>();
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream);
            String line = "";

            LineNumberReader lineNumberReader = new LineNumberReader(reader);
            while ((line = lineNumberReader.readLine()) != null) {
                if (line.isEmpty() || line.charAt(0) == '#') {
                    continue;
                }
                lines.add(line.trim());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
