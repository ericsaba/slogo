package model.command.control;

import javafx.collections.ObservableMap;
import main.Constants;
import model.command.Runner;
import model.command.exception.InvalidArgumentsClosingBracketException;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.InvalidArgumentsZeroDivisorException;
import model.command.exception.UnrecognizedNameException;
import model.parser.Parser;
import model.parser.SyntaxType;


public class Ask extends GeneralControlCommand {

    @Override
    public double run(Runner runner, String ... param) throws UnrecognizedNameException,
                                                      InvalidArgumentsTypeException,
                                                      InvalidArgumentsNumberException,
                                                      NumberFormatException,
                                                      InvalidArgumentsClosingBracketException,
                                                      InvalidArgumentsZeroDivisorException {

        String[] limits = param[Constants.ZERO].split(Constants.WHITE_SPACES);
        String[] comms = param[Constants.ONE].split(Constants.WHITE_SPACES);
        if (Parser.getInstance().decideSyntax(limits[Constants.ZERO]) != SyntaxType.CONSTANT ||
            Parser.getInstance().decideSyntax(comms[Constants.ZERO]) != SyntaxType.COMMAND) { throw new InvalidArgumentsTypeException(); }

        double returnValue = Constants.ZERO;

        for (String p : limits) {
            ObservableMap<String, Double> temp = runner.getVars();

            runner.getTurtleList().setTempTurtles(new int[] { Integer.parseInt(p) });
            returnValue = runner.runCommand(param[Constants.ONE]);

            runner.setVars(temp);

        }
        runner.getTurtleList().resetTellTurtles();
        return returnValue;
    }

    @Override
    public int paramNumber() {
        return Constants.TWO;
    }
}
