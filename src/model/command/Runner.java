package model.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableMap;
import main.Constants;
import model.command.exception.InvalidArgumentsClosingBracketException;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.InvalidArgumentsZeroDivisorException;
import model.command.exception.UnrecognizedNameException;
import model.command.personal.GeneralPersonalCommand;
import model.parser.Parser;
import model.parser.SyntaxType;
import model.turtle.TurtleList;
import view.Display;


public class Runner {

    private Map<String, GeneralCommand> myCommands;
    private ObservableMap<String, GeneralPersonalCommand> personalCommands;
    private ObservableMap<String, Double> myVars;
    private Display myDisplay;
    private Parser myParser;
    private TurtleList turtle;
    private String[] exprs;
    private int stackTracker;

    public Runner(TurtleList tut,
                  Display display,
                  Map<String, GeneralCommand> commands,
                  ObservableMap<String, GeneralPersonalCommand> pCommands,
                  ObservableMap<String, Double> vars) {
        turtle = tut;
        myDisplay = display;
        myCommands = commands;
        personalCommands = pCommands;
        myVars = vars;
        myParser = Parser.getInstance();
    }

    public double runCommand(String userInput) throws UnrecognizedNameException,
                                              InvalidArgumentsClosingBracketException,
                                              InvalidArgumentsTypeException, NumberFormatException,
                                              InvalidArgumentsNumberException,
                                              InvalidArgumentsZeroDivisorException {
        exprs = userInput.split(Constants.WHITE_SPACES);
        stackTracker = 0;

        double returnValue = 0;

        while (stackTracker < exprs.length) {
            // runNext is run with a null to SPECIFICALLY give an error and
            returnValue = Double.parseDouble(runNext(Constants.NO_COMMAND));
            myDisplay.updateTurtleList(turtle);
        }

        return returnValue;
    }

    public TurtleList getTurtleList() {
        return turtle;
    }

    public void varPut(String key, Double value) {
        myVars.put(key, value);
    }

    public void cmdPut(String key, GeneralPersonalCommand cmd) {
        myCommands.put(key, cmd);
        personalCommands.put(key, cmd);
    }

    private String runNext(String currentCommandName) throws UnrecognizedNameException,
                                                     InvalidArgumentsClosingBracketException,
                                                     InvalidArgumentsTypeException,
                                                     InvalidArgumentsNumberException,
                                                     InvalidArgumentsZeroDivisorException {
        try {
            return String.valueOf(runPiece(myParser.findCommandName(exprs[stackTracker])));
        }
        catch (UnrecognizedNameException e) {
            // there might be the case that this is a new command, not standard
            if (currentCommandName.equals(Constants.COMMAND_CREATOR_NAME)) { return exprs[stackTracker++]; }
            if (myCommands.get(exprs[stackTracker]) != null) { return String
                    .valueOf(runPiece(exprs[stackTracker])); }
            throw e;
        }
    }

    private double runPiece(String currentCommandName) throws UnrecognizedNameException,
                                                      InvalidArgumentsClosingBracketException,
                                                      InvalidArgumentsTypeException,
                                                      InvalidArgumentsNumberException,
                                                      InvalidArgumentsZeroDivisorException {
        List<String> args = new ArrayList<String>();
        stackTracker++;
        while (stackTracker < exprs.length &&
               args.size() < myCommands.get(currentCommandName).paramNumber()) {
            switch (myParser.decideSyntax(exprs[stackTracker])) {
                case CONSTANT:
                    args.add(exprs[stackTracker++]);
                    break;
                case VARIABLE:
                    args.add(exprs[stackTracker++]);
                    break;
                case COMMAND:
                    args.add(String.valueOf(runNext(currentCommandName)));
                    myDisplay.updateTurtleList(turtle);
                    break;
                case LISTSTART:
                    args.add(getBracketExpr());
                    break;
                case LISTEND:
                    throw new InvalidArgumentsClosingBracketException();
                default: // Ignore other types, have nothing to do with comments and dunno groups
                    break;

            }
        }

        String[] argsArray = new String[args.size()];
        args.toArray(argsArray);
        return runSpecificCommand(myCommands.get(currentCommandName), argsArray);
    }

    // Get the entire string inside of brackets, excluding the brackets
    private String getBracketExpr() throws InvalidArgumentsClosingBracketException,
                                   UnrecognizedNameException {
        StringBuilder expression = new StringBuilder();
        int brackLeft = 1;

        stackTracker++;
        while (brackLeft > 0 && stackTracker < exprs.length) {
            switch (myParser.decideSyntax(exprs[stackTracker])) {
            // Found an additional opening bracket!
                case LISTSTART:
                    expression.append(exprs[stackTracker++] + Constants.DIVISOR);
                    brackLeft++;
                    break;
                case LISTEND:
                    switch (brackLeft) {
                    // Found a closing bracket without a opening bracket
                        case 0:
                            throw new InvalidArgumentsClosingBracketException();
                            // Found the last closing bracket!
                        case 1:
                            stackTracker++;
                            return expression.toString();
                            // Found a closing bracket for an additional opening bracket
                        default:
                            expression.append(exprs[stackTracker++] + Constants.DIVISOR);
                            brackLeft--;
                            break;
                    }
                    break;
                // Found a random piece of text
                default:
                    expression.append(exprs[stackTracker++] + Constants.DIVISOR);
                    break;
            }
        }

        // Reached the end of the line and did not find the closing bracket
        throw new InvalidArgumentsClosingBracketException();
    }

    private double runSpecificCommand(GeneralCommand command, String[] args)
                                                                            throws UnrecognizedNameException,
                                                                            InvalidArgumentsTypeException,
                                                                            InvalidArgumentsClosingBracketException,
                                                                            InvalidArgumentsNumberException,
                                                                            InvalidArgumentsZeroDivisorException {
        switch (command.type()) {
            case DISPLAY:
                varCast(args);
                return command.run(myDisplay, args);
            case TURTLE:
                varCast(args);
                return command.run(turtle, args);
            case MATH:
                varCast(args);
                return command.run((Object[])args);
            case CONTROL:
                return command.run(new Runner(turtle, myDisplay, myCommands, personalCommands,
                                              myVars), args);
            case PERSONAL:
                return command.run(new Runner(turtle, myDisplay, myCommands, personalCommands,
                                              myVars), args);
            default:
                throw new UnrecognizedNameException();
        }
    }

    private void varCast(String[] args) throws UnrecognizedNameException {
        for (int i = 0; i < args.length; i++) {
            if (myParser.decideSyntax(args[i]) == SyntaxType.VARIABLE) {
                args[i] = String.valueOf(myVars.get(args[i]));
            }
        }
    }

    public void setVars(ObservableMap<String, Double> oldVars) {
        myVars = oldVars;
    }

    public ObservableMap<String, Double> getVars() {
        return myVars;
    }

}
