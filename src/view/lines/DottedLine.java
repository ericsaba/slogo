package view.lines;

import javafx.scene.paint.Color;
import main.Constants;


public class DottedLine extends AbstractLine {

    public DottedLine(double startX,
                      double startY,
                      double endX,
                      double endY,
                      double thickness,
                      Color color) {
        super(startX, startY, endX, endY, thickness, color);
    }

    private DottedLine(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }

    @Override
    protected void setStyle() {
        getStrokeDashArray().addAll(Constants.DOTTED_DOUBLE);
    }

    @Override
    protected AbstractLine init(double startX, double startY, double endX, double endY) {
        return new DottedLine(startX, startY, endX, endY);
    }

}
