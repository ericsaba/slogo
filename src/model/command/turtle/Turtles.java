package model.command.turtle;

import main.Constants;
import model.turtle.TurtleList;


public class Turtles extends GeneralTurtleCommand {

    @Override
    public double run(TurtleList turtle, double ... param) {
        return turtle.getAllTurtles().length;
    }

    @Override
    public int paramNumber() {
        return Constants.ZERO;
    }
}
