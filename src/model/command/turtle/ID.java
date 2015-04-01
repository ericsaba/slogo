package model.command.turtle;

import main.Constants;
import model.turtle.TurtleList;


public class ID extends GeneralTurtleCommand {

    @Override
    public double run(TurtleList turtle, double ... param) {
        return turtle.getName();
    }

    @Override
    public int paramNumber() {
        return Constants.ZERO;
    }
}
