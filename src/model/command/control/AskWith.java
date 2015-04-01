package model.command.control;

import javafx.collections.ObservableMap;
import main.Constants;
import model.parser.Parser;
import model.parser.SyntaxType;
import model.turtle.Turtle;
import model.command.Runner;
import model.command.exception.InvalidArgumentsClosingBracketException;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.InvalidArgumentsZeroDivisorException;
import model.command.exception.UnrecognizedNameException;


public class AskWith extends GeneralControlCommand {
    @Override
    public double run(Runner runner, String ... param) throws UnrecognizedNameException,
                                                      InvalidArgumentsTypeException,
                                                      InvalidArgumentsNumberException,
                                                      NumberFormatException,
                                                      InvalidArgumentsClosingBracketException,
                                                      InvalidArgumentsZeroDivisorException {

        String[] comms = param[Constants.ONE].split(Constants.WHITE_SPACES);

        if (Parser.getInstance().decideSyntax(comms[Constants.ZERO]) != SyntaxType.COMMAND) { throw new InvalidArgumentsTypeException(); }

        double returnValue = Constants.ZERO;

        for (Turtle t : runner.getTurtleList().getAllTurtles()) {
            ObservableMap<String, Double> temp = runner.getVars();
            runner.getTurtleList().setTempTurtles(new int[] { t.getName() });

            if (runner.runCommand(param[Constants.ZERO]) == 1) {
                returnValue = runner.runCommand(param[Constants.ONE]);
            }

            runner.setVars(temp);
        }

        return returnValue;
    }

    @Override
    public int paramNumber() {
        return Constants.TWO;
    }
}
