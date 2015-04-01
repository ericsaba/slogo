package model.command.turtle;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import model.turtle.TurtleList;


public class HideTurtle extends GeneralTurtleCommand {

    @Override
    public double run(TurtleList turtle, double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        turtle.setVisible(false);
        return 0;
    }

    @Override
    public int paramNumber() {
        return Constants.ZERO;
    }
}
