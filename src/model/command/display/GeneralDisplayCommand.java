package model.command.display;

import main.Constants;
import model.command.CommandsUtility;
import model.command.GeneralCommand;
import model.command.exception.InvalidArgumentsNumberException;
import model.parser.CommandType;
import view.Display;


public abstract class GeneralDisplayCommand extends CommandsUtility implements GeneralCommand {

    @Override
    public double run(Object ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, Constants.TWO);
        return run((Display)param[Constants.ZERO],
                   convertToString((Object[])param[Constants.ONE]));
    }

    @Override
    public CommandType type() {
        return CommandType.DISPLAY;
    }

    public abstract double run(Display display, String ... param)
                                                                 throws InvalidArgumentsNumberException;

}
