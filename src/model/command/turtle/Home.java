package model.command.turtle;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import model.turtle.TurtleList;


public class Home extends GeneralTurtleCommand {

    @Override
    public double run(TurtleList turtle, double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        turtle.setPosition(Constants.ZERO, Constants.ZERO);
        return turtle.getTranslationDistance();
    }

    @Override
    public int paramNumber() {
        return Constants.ZERO;
    }
}
