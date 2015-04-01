package view.lines;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public abstract class AbstractLine extends Line {
    Color myColor;
    double myThickness;

    protected AbstractLine(double startX,
                           double startY,
                           double endX,
                           double endY,
                           double thickness,
                           Color color) {
        super(startX, startY, endX, endY);
        myColor = color;
        myThickness = thickness;
        setStroke(color);
        setStrokeWidth(thickness);
        setStyle();
    }

    protected AbstractLine(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }

    protected abstract void setStyle();

    public AbstractLine getNewLine(double startX, double startY, double endX, double endY) {
        AbstractLine ret = init(startX, startY, endX, endY);
        setStroke(myColor);
        setStrokeWidth(myThickness);
        setStyle();
        return ret;
    }

    protected abstract AbstractLine init(double startX, double startY, double endX, double endY);
}
