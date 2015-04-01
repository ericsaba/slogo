package model.command.math;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsZeroDivisorException;


public class Quotient extends GeneralMathCommand {

    @Override
    public double run(double ... param) throws InvalidArgumentsZeroDivisorException,
                                       InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        checkZero(param[Constants.ONE]);
        return param[Constants.ZERO] / param[Constants.ONE];
    }

    @Override
    public int paramNumber() {
        return Constants.TWO;
    }
}
