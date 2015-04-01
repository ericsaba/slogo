package model.command.math;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;


public class Tangent extends GeneralMathCommand {
    private static final int FAULT_TAN = 90;

    @Override
    public double run(double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        if (param[Constants.ZERO] % FAULT_TAN == Constants.ZERO &&
            (param[Constants.ZERO] / FAULT_TAN) % Constants.TWO != 0) { return Constants.ZERO; }
        return Math.tan(Math.toRadians(param[Constants.ZERO]));
    }

    @Override
    public int paramNumber() {
        return Constants.ONE;
    }
}
