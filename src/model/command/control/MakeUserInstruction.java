package model.command.control;

import main.Constants;
import model.command.Runner;
import model.command.exception.InvalidArgumentsClosingBracketException;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.InvalidArgumentsZeroDivisorException;
import model.command.exception.UnrecognizedNameException;
import model.command.personal.GeneralPersonalCommand;


public class MakeUserInstruction extends GeneralControlCommand {

    @Override
    public double run(Runner runner, String ... param) throws InvalidArgumentsNumberException {
        checkLength(param.length, paramNumber());

        String[] vars;

        if (param[Constants.ONE].equals(Constants.NO_COMMAND)) {
            vars = new String[Constants.ZERO];
        }
        else {
            vars = param[Constants.ONE].split(Constants.WHITE_SPACES);
        }

        runner.cmdPut(param[Constants.ZERO], new GeneralPersonalCommand() {

            @Override
            public double run(Runner runner, String ... parameters)
                                                                   throws UnrecognizedNameException,
                                                                   InvalidArgumentsClosingBracketException,
                                                                   InvalidArgumentsTypeException,
                                                                   InvalidArgumentsNumberException,
                                                                   InvalidArgumentsZeroDivisorException {
                checkLength(parameters.length, paramNumber());

                for (int i = 0; i < vars.length; i++) {
                    runner.varPut(vars[i], Double.parseDouble(parameters[i]));
                }

                return runner.runCommand(param[Constants.TWO]);
            }

            @Override
            public int paramNumber() {
                return vars.length;
            }

            @Override
            public String getVars() {
                return param[Constants.ONE];
            }

            @Override
            public String getText() {
                return param[Constants.TWO];
            }
        });

        return Constants.ONE;
    }

    @Override
    public int paramNumber() {
        return Constants.THREE;
    }
}
