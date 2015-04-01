package view;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import controller.Controller;
import view.lines.AbstractLine;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import main.Constants;
import model.turtle.Pen;
import model.turtle.Turtle;


/***
 * @author Eric Saba
 */
public class DisplayTurtle extends ImageView {

    private List<AbstractLine> myLines = new ArrayList<AbstractLine>();
    private Group myPane;
    private Controller myController;
    private int myXOff;
    private int myYOff;
    private static final int TURTLE_HEIGHT = 40;
    private static final int TURTLE_WIDTH = 30;

    public DisplayTurtle(Controller controller,
                         Image turtleImage,
                         Group pane,
                         Turtle turtle,
                         int xOff,
                         int yOff) {
        super(turtleImage);
        myController = controller;
        setFitHeight(TURTLE_HEIGHT);
        setFitWidth(TURTLE_WIDTH);
        myXOff = xOff;
        myYOff = yOff;
        myPane = pane;
        relocate((myXOff + turtle.getX() - TURTLE_WIDTH / Constants.TWO),
                 (myYOff - turtle.getY() - TURTLE_HEIGHT / Constants.TWO));

        update(turtle, Color.BLACK);
    }

    /**
     * Public API called by the display to update the turtle in the view.
     *
     * @param turtle specific turtle being updated
     * @param lineColor lineColor for drawing lines
     */
    public void update(Turtle turtle, Color lineColor) {
        double halfTurtWidth = TURTLE_WIDTH / Constants.TWO;
        double halfTurtHeight = TURTLE_HEIGHT / Constants.TWO;
        double oldX = getLayoutX();
        double oldY = getLayoutY();
        double newX = (myXOff + turtle.getX() - halfTurtWidth);
        double newY = (myYOff - turtle.getY() - halfTurtHeight);
        updateDisplayTurtle(turtle, newX, newY, myXOff * Constants.TWO, myYOff * Constants.TWO);
        setTurtleBack(turtle, newX, newY);
        if (turtle.isPenDown() == 1)
            drawLines(oldX + halfTurtWidth, oldY + halfTurtHeight, newX, newY, turtle.getPen());
        if (turtle.isClear()) {
            clearLines();
            turtle.setClear(false);
        }
    }

    /**
     * Moves the displayTurtle on the screen. Checks to see whether it has moved out
     * of bounds and sets its coordinates accordingly.
     *
     * @param turtle turtle corresponding to this display turtle
     * @param newX new x-coordinate for the display turtle to be set
     * @param newY new y-coordinate for the display turtle to be set
     * @param width width of the screen
     * @param height height of the screen
     */
    private void updateDisplayTurtle(Turtle turtle,
                                     double newX,
                                     double newY,
                                     double width,
                                     double height) {
        relocate(findNewLocation(newX, width, TURTLE_WIDTH / Constants.TWO),
                 findNewLocation(newY, height, TURTLE_HEIGHT / Constants.TWO));
        setVisible(turtle.isShowing() == 1);
        setRotate(turtle.getHeading());
    }

    private double findNewLocation(double newCoor, double sceneDimension, int halfTurtleDimension) {
        double retCoor = ((newCoor + halfTurtleDimension) % sceneDimension) - halfTurtleDimension;
        if (retCoor < halfTurtleDimension * -1) {
            retCoor += sceneDimension;
        }
        return retCoor;
    }

    private void setTurtleBack(Turtle turtle, double displayTurtleOldX, double displayTurtleOldY) {
        double newX = turtle.getX() - Math.abs(getLayoutX() - displayTurtleOldX);
        double newY = turtle.getY() - Math.abs(getLayoutY() - displayTurtleOldY);
        turtle.setPosition(newX, newY);
    }

    /**
     * Handles the line drawing on the view.
     *
     * @param oldX start x-coordinate of the line
     * @param oldY start y-coordinate of the line
     * @param newX new x-coordinate of the turtle
     * @param newY new y-coordinate of the turtle
     * @param lineColor line color used to draw the line
     */

    private void drawLines(double oldX, double oldY, double newX, double newY, Pen pen) {
        try {
            AbstractLine testLine = createLineWithType(oldX, oldY, newX, newY, pen);
            List<AbstractLine> drawLines = checkLine(new ArrayList<AbstractLine>(), testLine);
            myLines.addAll(drawLines);
            myPane.getChildren().addAll(drawLines);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private AbstractLine createLineWithType(double oldX,
                                            double oldY,
                                            double newX,
                                            double newY,
                                            Pen pen) throws Exception {
        Class myClass = Class.forName(Constants.LINE_EXTENSION + pen.getStyle());
        Class[] types =
        { Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Color.class };
        Constructor constructor = myClass.getConstructor(types);

        Object[] parameters = { oldX, oldY,
                               newX + TURTLE_WIDTH / Constants.TWO,
                               newY + TURTLE_HEIGHT / Constants.TWO,
                               pen.getThickness(),
                               myController.getColorFromIndex(pen.getColorIndex()) };
        return (AbstractLine)constructor.newInstance(parameters);
    }

    /**
     * Checks the line about to be drawn to see if it crosses the edges of the view. If so, this
     * method recursively
     * cuts up this line into multiple lines to be drawn by the Display.
     *
     * @param curLines array of current lines going to be drawn
     * @param line current line being examined
     * @return array of lines to be drawn
     */
    private List<AbstractLine> checkLine(List<AbstractLine> curLines, AbstractLine line) {
        double width = myXOff * Constants.TWO;
        double height = myYOff * Constants.TWO;
        double slope = (line.getEndY() - line.getStartY()) / (line.getEndX() - line.getStartX());
        if (line.getEndY() < 0) {
            line = checkLineYHelper(curLines, line, slope, 0, height);
        }
        else if (line.getEndY() > height) {
            line = checkLineYHelper(curLines, line, slope, height, 0);
        }
        if (line.getEndX() > width) {
            line = checkLineXHelper(curLines, line, slope, width, 0);
        }
        else if (line.getEndX() < 0) {
            line = checkLineXHelper(curLines, line, slope, 0, width);
        }
        curLines.add(line);
        return curLines;
    }

    /**
     * Used in the recursive method checkLine() to check if x coordinate of the line is past the
     * bounds.
     *
     * @param curLines array of lines being drawn
     * @param l current line being examined
     * @param slope slope of the line
     * @param mySide boundary on the side being checked
     * @param opSide other boundary
     * @return l, after it's been reduced to just stop at an x-boundary
     */
    private AbstractLine checkLineXHelper(List<AbstractLine> curLines,
                                          AbstractLine l,
                                          double slope,
                                          double mySide,
                                          double opSide) {
        double cutOffY = getCutOff(l.getStartY(), l.getStartX(), slope, mySide);
        AbstractLine secondL =
                l.getNewLine(opSide, cutOffY, l.getEndX() + (opSide - mySide), l.getEndY());
        l = l.getNewLine(l.getStartX(), l.getStartY(), mySide, cutOffY);
        checkLine(curLines, secondL);
        return l;
    }

    /**
     * Used in the recursive method checkLine() to check if y coordinate of the line is past the
     * bounds.
     *
     * @param curLines array of lines being drawn
     * @param l current line being examined
     * @param slope slope of the line
     * @param mySide boundary on the side being checked
     * @param opSide other boundary
     * @return l, after it's been reduced to just stop at an y-boundary
     */
    private AbstractLine checkLineYHelper(List<AbstractLine> curLines,
                                          AbstractLine l,
                                          double slope,
                                          double mySide,
                                          double opSide) {
        double cutOffX = getCutOff(l.getStartX(), l.getStartY(), (1 / slope), mySide);
        AbstractLine secondL =
                l.getNewLine(cutOffX, opSide, l.getEndX(), l.getEndY() + (opSide - mySide));
        l = l.getNewLine(l.getStartX(), l.getStartY(), cutOffX, mySide);
        checkLine(curLines, secondL);
        return l;
    }

    /**
     * Finds the value of the coordinate where the line should be cut.
     *
     * @param otherStart start coordinate of the axis not being checked
     * @param myStart start coordinate of the axis being checked
     * @param slope slope of the line
     * @param newEnd end coordinate of the axis being checked (one of the boundaries of this
     *        coordinate)
     * @return the coordinate-value of the cutoff of the line
     */
    private double getCutOff(double otherStart, double myStart, double slope, double newEnd) {
        return otherStart + (slope * (newEnd - myStart));
    }

    private void clearLines() {
        myPane.getChildren().removeAll(myLines);
    }
}
