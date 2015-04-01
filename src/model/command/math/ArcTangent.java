package model.command.math;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;


public class ArcTangent extends GeneralMathCommand {

    @Override
    public double run(double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        return Math.toDegrees(Math.atan(param[Constants.ZERO]));
    }

    @Override
    public int paramNumber() {
        return Constants.ONE;
    }
}
