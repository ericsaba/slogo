package model.command.math;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;


public class Remainder extends GeneralMathCommand {

    @Override
    public double run(double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        return param[Constants.ZERO] % param[Constants.ONE];
    }

    @Override
    public int paramNumber() {
        return Constants.TWO;
    }
}
