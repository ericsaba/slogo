package model.data;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import model.turtle.Turtle;


public class TurtleData extends AbstractDataModel {

    private List<Turtle> myTurtles;
    private BooleanProperty myBool;

    public TurtleData() {
        myTurtles = new ArrayList<Turtle>();
        myBool = new SimpleBooleanProperty();
        myBool.set(false);
    }

    public List<Turtle> getTurtles() {
        return myTurtles;
    }

    public void addTurtles(Turtle t) {
        myTurtles.add(t);
        myBool.set(!myBool.get());
    }

    public BooleanProperty getBoolean() {
        return myBool;
    }
}
