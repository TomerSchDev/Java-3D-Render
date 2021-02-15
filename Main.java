import render.Display;
import render.shapes.ComplexShape;
import render.shapes.CustomShape;
import render.shapes.ShapesExamples;
import java.io.File;

public class Main {
    private static final int SIZE = 100;

    public static void main(String[] args) {
        Display display = new Display();
        File file=null;
        if(args.length!=0) {
            file = new File(args[0]);
        }
        ComplexShape shape;
        if (file!=null&&file.exists()) {
            shape = CustomShape.createCustomShape(file);
        } else {
            shape=ShapesExamples.getRandomShape(SIZE);
        }
        display.init(shape);
        display.start();
    }
}
