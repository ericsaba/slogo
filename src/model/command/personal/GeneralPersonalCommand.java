package model.command.personal;

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


public abstract class GeneralPersonalCommand extends CommandsUtility implements GeneralCommand {

    @Override
    public double run(Object ... param) throws UnrecognizedNameException,
                                       InvalidArgumentsClosingBracketException,
                                       InvalidArgumentsTypeException,
                                       InvalidArgumentsNumberException,
                                       InvalidArgumentsZeroDivisorException {
        return run((Runner)param[Constants.ZERO],
                   (String[])param[Constants.ONE]);
    }

    @Override
    public CommandType type() {
        return CommandType.PERSONAL;
    }

    public abstract double run(Runner runner, String ... param)
                                                               throws UnrecognizedNameException,
                                                               InvalidArgumentsClosingBracketException,
                                                               InvalidArgumentsTypeException,
                                                               InvalidArgumentsNumberException,
                                                               InvalidArgumentsZeroDivisorException;

    public abstract String getVars();

    public abstract String getText();
}
