package model.command.math;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;


public class Not extends GeneralMathCommand {

    @Override
    public double run(double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        return (param[Constants.ZERO] == Constants.ZERO) ? Constants.ONE : Constants.ZERO;
    }

    @Override
    public int paramNumber() {
        return Constants.ONE;
    }
}
