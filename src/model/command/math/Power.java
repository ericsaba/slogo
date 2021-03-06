package model.command.math;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;


public class Power extends GeneralMathCommand {

    @Override
    public double run(double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        return Math.pow(param[Constants.ZERO], param[Constants.ONE]);
    }

    @Override
    public int paramNumber() {
        return Constants.TWO;
    }
}
