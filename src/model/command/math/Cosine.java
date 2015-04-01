package model.command.math;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;


public class Cosine extends GeneralMathCommand {

    @Override
    public double run(double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        return Math.cos(Math.toRadians(param[Constants.ZERO]));
    }

    @Override
    public int paramNumber() {
        return Constants.ONE;
    }
}
