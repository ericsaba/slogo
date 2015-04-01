package model.command;

import main.Constants;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsZeroDivisorException;


public class CommandsUtility {

    public static double[] convertToDouble(Object ... param) {
        double[] newParam = new double[param.length];
        for (int i = Constants.ZERO; i < param.length; i++) {
            newParam[i] = Double.parseDouble((String)(param[i]));
        }
        return newParam;
    }

    public static String[] convertToString(Object ... param) {
        String[] newParam = new String[param.length];
        for (int i = Constants.ZERO; i < param.length; i++) {
            newParam[i] = (String)param[i];
        }
        return newParam;
    }

    public static void checkLength(int current, int desired) throws InvalidArgumentsNumberException {
        if (current != desired) { throw new InvalidArgumentsNumberException(); }
    }

    public static void checkZero(double value) throws InvalidArgumentsZeroDivisorException {
        if (value == Constants.ZERO) { throw new InvalidArgumentsZeroDivisorException(); }
    }
}
