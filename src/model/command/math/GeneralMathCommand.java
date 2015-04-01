package model.command.math;

import model.command.CommandsUtility;
import model.command.GeneralCommand;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsZeroDivisorException;
import model.parser.CommandType;


public abstract class GeneralMathCommand extends CommandsUtility implements GeneralCommand {

    @Override
    public double run(Object ... param) throws InvalidArgumentsNumberException,
                                       InvalidArgumentsZeroDivisorException {
        return run(convertToDouble(param));
    }

    @Override
    public CommandType type() {
        return CommandType.MATH;
    }

    public abstract double run(double ... param) throws InvalidArgumentsNumberException,
                                                InvalidArgumentsZeroDivisorException;

}
