package model.command.control;

import main.Constants;
import model.command.Runner;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.UnrecognizedNameException;
import model.parser.Parser;
import model.parser.SyntaxType;


public class MakeVariable extends GeneralControlCommand {

    @Override
    public double run(Runner runner, String ... param) throws UnrecognizedNameException,
                                                      InvalidArgumentsTypeException,
                                                      InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        if (Parser.getInstance().decideSyntax(param[Constants.ZERO]) != SyntaxType.VARIABLE) { throw new InvalidArgumentsTypeException(); }
        runner.varPut(param[Constants.ZERO], Double.parseDouble(param[Constants.ONE]));
        return Double.parseDouble(param[Constants.ONE]);
    }

    @Override
    public int paramNumber() {
        return Constants.TWO;
    }
}
