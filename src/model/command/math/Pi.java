package model.command.math;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;


public class Pi extends GeneralMathCommand {
    @Override
    public double run(double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        return Math.PI;
    }

    @Override
    public int paramNumber() {
        return Constants.ZERO;
    }
}
