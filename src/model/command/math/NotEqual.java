package model.command.math;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;


public class NotEqual extends GeneralMathCommand {

    @Override
    public double run(double ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());
        return (param[Constants.ZERO] != param[Constants.ONE]) ? Constants.ONE : Constants.ZERO;
    }

    @Override
    public int paramNumber() {
        return Constants.TWO;
    }
}
