package model.command.turtle;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import model.turtle.TurtleList;


public class PenUp extends GeneralTurtleCommand {

    @Override
    public double run(TurtleList turtle, double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        turtle.setPenDown(false);
        return Constants.ZERO;
    }

    @Override
    public int paramNumber() {
        return Constants.ZERO;
    }
}
