package model.command.control;

import java.util.ArrayList;
import java.util.List;
import main.Constants;
import model.command.Runner;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.UnrecognizedNameException;
import model.parser.Parser;
import model.parser.SyntaxType;


public class Tell extends GeneralControlCommand {

    @Override
    public double run(Runner runner, String ... param) throws UnrecognizedNameException,
                                                      InvalidArgumentsTypeException {
        String[] limits = param[Constants.ZERO].split(Constants.WHITE_SPACES);

        if (Parser.getInstance().decideSyntax(limits[Constants.ZERO]) != SyntaxType.CONSTANT) { throw new InvalidArgumentsTypeException(); }

        List<Double> turtsToAdd = new ArrayList<Double>();
        for (String p : limits) {
            turtsToAdd.add(Double.parseDouble(p));
        }
        runner.getTurtleList().setTellCalled();
        return runner.getTurtleList().tell(turtsToAdd.toArray(new Double[turtsToAdd.size()]));
    }

    @Override
    public int paramNumber() {
        return Constants.ONE;
    }

}
