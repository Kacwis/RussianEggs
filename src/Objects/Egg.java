package Objects;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;

import java.awt.*;

public class Egg extends Ellipse {


    public Egg(){
        Color color = Color.color(0.624,0.659,0.584);
        setFill(color);
        setRadiusX(7);
        setRadiusY(10);
        setStroke(Color.BLACK);
        setStrokeType(StrokeType.CENTERED);
        setStrokeWidth(2);
    }

}
