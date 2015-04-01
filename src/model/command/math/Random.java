package model.command.math;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;


public class Random extends GeneralMathCommand {

    private static final java.util.Random RAND = new java.util.Random();

    @Override
    public double run(double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        return RAND.nextInt((int)param[Constants.ZERO]);
    }

    @Override
    public int paramNumber() {
        return Constants.ONE;
    }
}
