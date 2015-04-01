package view.lines;

import javafx.scene.paint.Color;


public class SolidLine extends AbstractLine {
    public SolidLine(double startX,
                     double startY,
                     double endX,
                     double endY,
                     double thickness,
                     Color color) {
        super(startX, startY, endX, endY, thickness, color);
    }

    private SolidLine(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }

    @Override
    protected void setStyle() {
        return;
    }

    @Override
    protected AbstractLine init(double startX, double startY, double endX, double endY) {
        return new SolidLine(startX, startY, endX, endY);
    }
}
