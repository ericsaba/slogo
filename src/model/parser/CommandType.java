package model.parser;

import main.Constants;


public enum CommandType {
    DISPLAY(Constants.DISPLAY_DOT),
    MATH(Constants.MATH_DOT),
    TURTLE(Constants.TURTLE_DOT),
    CONTROL(Constants.CONTROL_DOT),
    PERSONAL(Constants.PERSONAL_DOT); // NOT SUPPOSED TO BE PREVIOUSLY IMPLEMENTED

    private String path;

    private CommandType(String aPath) {
        path = aPath;
    }

    @Override
    public String toString() {
        return path;
    }

    public static CommandType getType(String test) {
        return valueOf(test.toUpperCase());
    }
}
