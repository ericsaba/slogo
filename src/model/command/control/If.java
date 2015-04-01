package model.command.control;

import main.Constants;
import model.command.Runner;
import model.command.exception.InvalidArgumentsClosingBracketException;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.InvalidArgumentsZeroDivisorException;
import model.command.exception.UnrecognizedNameException;


public class If extends GeneralControlCommand {

    @Override
    public double run(Runner runner, String ... param) throws UnrecognizedNameException,
                                                      InvalidArgumentsClosingBracketException,
                                                      InvalidArgumentsTypeException,
                                                      InvalidArgumentsNumberException,
                                                      NumberFormatException,
                                                      InvalidArgumentsZeroDivisorException {
        checkLength(param.length, paramNumber());

        if (Double.parseDouble(param[0]) == 1) { return runner.runCommand(param[Constants.ONE]); }
        return 0;
    }

    @Override
    public int paramNumber() {
        return Constants.TWO;
    }
}
