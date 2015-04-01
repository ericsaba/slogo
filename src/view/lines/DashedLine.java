package view.lines;

import main.Constants;
import javafx.scene.paint.Color;


public class DashedLine extends AbstractLine {

    public DashedLine(double startX, double startY, double endX,
                      double endY, double thickness, Color color) {
        super(startX, startY, endX, endY, thickness, color);
    }

    private DashedLine(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }

    @Override
    protected void setStyle() {
        getStrokeDashArray().addAll(Constants.DASHED_DOUBLES);
    }

    @Override
    protected AbstractLine init(double startX, double startY, double endX, double endY) {
        return new DashedLine(startX, startY, endX, endY);
    }
}
