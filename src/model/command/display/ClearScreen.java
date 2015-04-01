package model.command.display;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import view.Display;


public class ClearScreen extends GeneralDisplayCommand {

    @Override
    public double run(Display display, String ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());

        display.clearScreen();
        return Constants.ZERO; // TODO: Return distance turtle moved
    }

    @Override
    public int paramNumber() {
        return Constants.ZERO;
    }
}
