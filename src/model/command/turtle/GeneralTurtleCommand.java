package model.command.turtle;

import main.Constants;
import model.command.CommandsUtility;
import model.command.GeneralCommand;
import model.command.exception.InvalidArgumentsNumberException;
import model.parser.CommandType;
import model.turtle.TurtleList;


public abstract class GeneralTurtleCommand extends CommandsUtility implements GeneralCommand {

    @Override
    public double run(Object ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, Constants.TWO);
        return run((TurtleList)param[Constants.ZERO],
                   convertToDouble((Object[])param[Constants.ONE]));
    }

    @Override
    public CommandType type() {
        return CommandType.TURTLE;
    }

    public abstract double run(TurtleList turtle, double ... param)
                                                                   throws InvalidArgumentsNumberException;

}
