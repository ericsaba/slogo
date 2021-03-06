package model.command.turtle;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import model.turtle.TurtleList;


public class IsShowing extends GeneralTurtleCommand {

    @Override
    public double run(TurtleList turtle, double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        return turtle.isShowing();
    }

    @Override
    public int paramNumber() {
        return Constants.ZERO;
    }
}
