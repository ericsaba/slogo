package model.turtle;

import java.awt.geom.Point2D;
import main.Constants;


public class Turtle {
    private int name;
    private double coordX, coordY;
    private double oldX, oldY;
    private double myAngle, oldAng;
    private boolean visible;
    private boolean clear;
    private Pen myPen;

    public Turtle(int thisName) {
        name = thisName;
        oldX = coordX = Constants.ZERO;
        oldY = coordY = Constants.ZERO;
        myAngle = Constants.ZERO;
        visible = true;
        clear = false;
        myPen =
                new Pen(true, LineType.SOLID, Constants.DEFAULT_THICKNESS,
                        Constants.DEFAULT_START_COLOR_INDEX);
    }

    public void refresh() {
        oldX = coordX;
        oldY = coordY;
        oldAng = myAngle;
    }

    public void move(double distance) {
        refresh();
        coordX += distance * Math.sin(myAngle);
        coordY += distance * Math.cos(myAngle);
    }

    public void rotate(double ang) {
        refresh();
        myAngle = (myAngle + Math.toRadians(ang)) % (Constants.TWO * Math.PI);
    }

    public void towards(double targetX, double targetY) {
        refresh();
        myAngle = Math.atan2(targetX - coordX, targetY - coordY);
    }

    public void setPosition(double newX, double newY) {
        refresh();
        coordX = newX;
        coordY = newY;
    }

    public void setHeading(double newAng) {
        refresh();
        myAngle = Math.toRadians(newAng);
    }

    public void setPenDown(boolean down) {
        myPen.setDown(down);
    }

    public void setPenSize(double index) {
        myPen.setThickness(index);
    }

    public void setPenShape(double index) {
        myPen.setStyle((int)index);
    }

    public void setPenColor(double index) {
        myPen.setColorIndex((int)index);
    }

    public void setVisible(boolean vis) {
        visible = vis;
    }

    public void setClear(boolean clr) {
        clear = clr;
    }

    public double getTranslationDistance() {
        return Point2D.distance(coordX, coordY, oldX, oldY);
    }

    public double getRotationAngle() {
        return Math.toDegrees(myAngle - oldAng);
    }

    public double getX() {
        return coordX;
    }

    public double getOldX() {
        return oldX;
    }

    public double getY() {
        return coordY;
    }

    public double getOldY() {
        return oldY;
    }

    public double getHeading() {
        return Math.toDegrees(myAngle);
    }

    public double getOldHeading() {
        return Math.toDegrees(oldAng);
    }

    public Integer getName() {
        return name;
    }

    public double isShowing() {
        return visible ? Constants.ONE : Constants.ZERO;
    }

    public double isPenDown() {
        return myPen.getDown();
    }

    public boolean isClear() {
        return clear;
    }

    public Pen getPen() {
        return myPen;
    }
}
