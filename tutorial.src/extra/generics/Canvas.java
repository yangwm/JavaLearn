package extra.generics;

import java.util.List;

public class Canvas {
    public void draw(Shape s) {
        s.draw(this);
    }
    
//    public void drawAll(List<Shape> shapes) {
//        for (Shape s: shapes) {
//            s.draw(this);
//       }
//    }

    public void drawAll(List<? extends Shape> shapes) {
        //...
    }

    public void addShape(List<Shape> shapes) {
        shapes.add(0, new Rectangle()); // OK!
    }
    
    public void addRectangle(List<? extends Shape> shapes) {
        shapes.add(0, new Rectangle()); // Compile-time error!
    }

}
