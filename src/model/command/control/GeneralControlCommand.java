package model.command.control;

import main.Constants;
import model.command.CommandsUtility;
import model.command.GeneralCommand;
import model.command.Runner;
import model.command.exception.InvalidArgumentsClosingBracketException;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.InvalidArgumentsZeroDivisorException;
import model.command.exception.UnrecognizedNameException;
import model.parser.CommandType;


public abstract class GeneralControlCommand extends CommandsUtility implements GeneralCommand {

    @Override
    public double run(Object ... param) throws UnrecognizedNameException,
                                       InvalidArgumentsTypeException,
                                       InvalidArgumentsClosingBracketException,
                                       InvalidArgumentsNumberException, NumberFormatException,
                                       InvalidArgumentsZeroDivisorException {
        return run((Runner)param[Constants.ZERO],
                   (String[])param[Constants.ONE]);
    }

    @Override
    public CommandType type() {
        return CommandType.CONTROL;
    }

    public abstract double run(Runner runner, String ... param)
                                                               throws UnrecognizedNameException,
                                                               InvalidArgumentsTypeException,
                                                               InvalidArgumentsClosingBracketException,
                                                               InvalidArgumentsNumberException,
                                                               NumberFormatException,
                                                               InvalidArgumentsZeroDivisorException;
}
