package model.command.control;

import main.Constants;
import model.command.Runner;
import model.command.exception.InvalidArgumentsClosingBracketException;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.InvalidArgumentsZeroDivisorException;
import model.command.exception.UnrecognizedNameException;
import model.parser.Parser;
import model.parser.SyntaxType;


public class DoTimes extends GeneralControlCommand {

    @Override
    public double run(Runner runner, String ... param) throws UnrecognizedNameException,
                                                      InvalidArgumentsTypeException,
                                                      InvalidArgumentsClosingBracketException,
                                                      InvalidArgumentsNumberException,
                                                      NumberFormatException,
                                                      InvalidArgumentsZeroDivisorException {
        checkLength(param.length, paramNumber());

        String[] limits = param[Constants.ZERO].split(Constants.WHITE_SPACES);
        checkLength(limits.length, Constants.TWO);

        if (Parser.getInstance().decideSyntax(limits[Constants.ZERO]) != SyntaxType.VARIABLE ||
            Parser.getInstance().decideSyntax(limits[Constants.ONE]) != SyntaxType.CONSTANT) { throw new InvalidArgumentsTypeException(); }

        double returnValue = Constants.ZERO;
        for (int i = Constants.ONE; i <= Integer.parseInt(limits[Constants.ONE]); i++) {
            runner.varPut(limits[Constants.ZERO], (double)i);
            returnValue = runner.runCommand(param[Constants.ONE]);
        }
        return returnValue;
    }

    @Override
    public int paramNumber() {
        return Constants.TWO;
    }
}
