package model.turtle;

import main.Constants;


public class Pen {
    private boolean myDown;
    private LineType myStyle;
    private double myThickness;
    private int myColorIndex;

    public Pen(boolean down, LineType style, double thickness, int colorIndex) {
        myDown = down;
        myStyle = style;
        myThickness = thickness;
        myColorIndex = colorIndex;
    }

    public double getDown() {
        return myDown ? Constants.ONE : Constants.ZERO;
    }

    public LineType getStyle() {
        return myStyle;
    }

    public double getThickness() {
        return myThickness;
    }

    public int getColorIndex() {
        return myColorIndex;
    }

    public void setThickness(double thickness) {
        myThickness = thickness;
    }

    public void setDown(boolean down) {
        myDown = down;
    }

    public void setStyle(int index) {
        myStyle = LineType.getType(index);
    }

    public void setColorIndex(int index) {
        myColorIndex = index;
    }
}
