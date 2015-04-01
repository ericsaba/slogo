package view;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.Constants;
import model.turtle.Turtle;
import model.turtle.TurtleList;
import controller.Controller;


public class Display extends Stage implements AbstractView {
    private Map<Integer, DisplayTurtle> turtleMap = new HashMap<Integer, DisplayTurtle>();
    private List<String> colors = new ArrayList<String>();
    private List<String> languages = new ArrayList<String>();
    private Image turtleImage;
    private int xOffset;
    private int yOffset;
    private Color myLineColor;
    private Scene myScene;
    private VBox myVBox = new VBox();
    private Group myPane = new Group();
    private Rectangle myBackground;
    private List<Line> myLineList = new ArrayList<Line>();
    private Controller myController;
    private TurtleList myTurtle;

    public Display(Controller controller, TurtleList turtle, int width, int height) {
        myTurtle = turtle;
        turtleImage = new Image(Constants.TURTLE_ADDRESS);
        myController = controller;
        xOffset = width / Constants.TWO;
        yOffset = height / Constants.TWO;
        myLineColor = Color.BLACK;
        myBackground = new Rectangle(width, height, Color.WHITE);

        myPane.getChildren().add(myBackground);
        makeTurtle(myTurtle.getAllTurtles()[0]);

        PreferencesPanel menuBar = new PreferencesPanel(this, controller, colors, languages);
        myVBox.getChildren().add(menuBar);
        myVBox.getChildren().add(myPane);

        setTitle(Constants.DISPLAY_NAME);
        myScene = new Scene(myVBox, width, height);

        myScene.setFill(Color.WHITE);
        setScene(myScene);
        show();
        setY(0);
        localInitialization();
    }

    private void localInitialization() {
        // TODO Auto-generated method stub

    }

    protected DisplayTurtle makeTurtle(Turtle t) {
        DisplayTurtle displayTurtle =
                new DisplayTurtle(myController, turtleImage, myPane, t, xOffset, yOffset);
        myPane.getChildren().add(displayTurtle);
        turtleMap.put(t.getName(), displayTurtle);

        return displayTurtle;
    }

    public void updateBackgroundColor(int index) {
        updateBackgroundColor(Color.valueOf(colors.get(index)));
    }

    public void updateBackgroundColor(Color c) {
        myBackground.setFill(c);
    }

    public void updateLineColor(int index) {
        updateLineColor(Color.valueOf(colors.get(index)));
    }

    public void updateLineColor(Color c) {
        myLineColor = c;
    }

    public void clearScreen() {
        myPane.getChildren().removeAll(myLineList);
    }

    public void updateTurtleList(TurtleList t) {
        for (Turtle individualTurtle : t) {
            if (turtleMap.get(individualTurtle.getName()) == null) {
                DisplayTurtle newTurt = makeTurtle(individualTurtle);
                newTurt.update(individualTurtle, myLineColor);
            }
            else {
                DisplayTurtle displayTurtle = turtleMap.get(individualTurtle
                        .getName());
                displayTurtle.update(individualTurtle, myLineColor);
            }
        }
    }

    public void setImage(String file) {
        turtleImage = new Image(file);
        for (Integer s : turtleMap.keySet()) {
            turtleMap.get(s).setImage(turtleImage);
        }
    }

    public TurtleList getTurtleList() {
        return myTurtle;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub

    }
}
