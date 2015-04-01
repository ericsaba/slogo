package model.command.turtle;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import model.turtle.TurtleList;


public class Right extends GeneralTurtleCommand {
    private final static int DEFAULT = 0;

    @Override
    public double run(TurtleList turtle, double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        turtle.rotate(param[DEFAULT]);
        return param[DEFAULT];
    }

    @Override
    public int paramNumber() {
        return Constants.ONE;
    }
}
