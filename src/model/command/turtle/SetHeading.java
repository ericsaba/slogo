package model.command.turtle;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import model.turtle.TurtleList;


public class SetHeading extends GeneralTurtleCommand {

    @Override
    public double run(TurtleList turtle, double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        turtle.setHeading(param[Constants.ZERO]);
        return turtle.getRotationAngle();
    }

    @Override
    public int paramNumber() {
        return Constants.ONE;
    }
}
