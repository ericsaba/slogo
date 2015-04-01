package model.command.turtle;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import model.turtle.TurtleList;

public class SetShape extends GeneralTurtleCommand {

    @Override
    public double run(TurtleList turtle, double ... param) throws InvalidArgumentsNumberException {
        checkLength(paramNumber(), param.length);
        turtle.setPenShape(param[Constants.ZERO]);
        return param[Constants.ZERO];
    }

    @Override
    public int paramNumber() {
        return Constants.ONE;
    }
}
