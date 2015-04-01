package model.command.math;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;


public class NaturalLog extends GeneralMathCommand {

    @Override
    public double run(double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        return Math.log(param[Constants.ZERO]);
    }

    @Override
    public int paramNumber() {
        return Constants.ONE;
    }
}
