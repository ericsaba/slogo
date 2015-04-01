package model.command.display;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import view.Display;


public class SetBackground extends GeneralDisplayCommand {

    @Override
    public double run(Display display, String ... param) throws InvalidArgumentsNumberException {
        checkLength(Constants.ONE, param.length);
        display.updateBackgroundColor(Integer.parseInt(param[Constants.ZERO]));
        return Integer.parseInt(param[Constants.ZERO]);
    }

    @Override
    public int paramNumber() {
        return Constants.ONE;
    }
}
