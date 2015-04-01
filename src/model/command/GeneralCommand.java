package model.command;

import model.command.exception.InvalidArgumentsClosingBracketException;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.InvalidArgumentsZeroDivisorException;
import model.command.exception.UnrecognizedNameException;
import model.parser.CommandType;


public interface GeneralCommand {

    /**
     * Standard Implementation of run
     *
     * @param the parameters of the specific command
     * @return the double return of the specific command
     * @throws InvalidArgumentsTypeException
     * @throws UnrecognizedNameException
     * @throws InvalidArgumentsClosingBracketException
     * @throws InvalidArgumentsNumberException
     * @throws InvalidArgumentsZeroDivisorException
     */
    public double run(Object ... param) throws UnrecognizedNameException,
                                       InvalidArgumentsTypeException,
                                       InvalidArgumentsClosingBracketException,
                                       InvalidArgumentsNumberException,
                                       InvalidArgumentsZeroDivisorException;

    /**
     * The way of finding what is the command type
     * of the command
     *
     * @return the command's type
     */
    public CommandType type();

    /**
     * The number of parameters the command requires
     *
     * @return the number of parameters the command
     *         requires
     */
    public int paramNumber();
}
