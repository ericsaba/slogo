package model.command.turtle;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import model.turtle.TurtleList;


public class XCoordinate extends GeneralTurtleCommand {

    @Override
    public double run(TurtleList turtle, double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        return turtle.getX();
    }

    @Override
    public int paramNumber() {
        return Constants.ZERO;
    }
}
