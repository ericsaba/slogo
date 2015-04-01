package model.command.control;

import main.Constants;
import model.command.Runner;
import model.command.exception.InvalidArgumentsClosingBracketException;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.InvalidArgumentsZeroDivisorException;
import model.command.exception.UnrecognizedNameException;


public class Repeat extends GeneralControlCommand {

    private static final String VAR = ":repcount";

    @Override
    public double run(Runner runner, String ... param) throws UnrecognizedNameException,
                                                      InvalidArgumentsClosingBracketException,
                                                      InvalidArgumentsTypeException,
                                                      InvalidArgumentsNumberException,
                                                      NumberFormatException,
                                                      InvalidArgumentsZeroDivisorException {
        checkLength(param.length, paramNumber());

        double returnValue = Constants.ZERO;
        for (int i = Constants.ONE; i <= Integer.parseInt(param[Constants.ZERO]); i++) {
            runner.varPut(VAR, (double)i);
            returnValue = runner.runCommand(param[Constants.ONE]);
        }

        return returnValue;
    }

    @Override
    public int paramNumber() {
        return Constants.TWO;
    }
}
