package model.parser;

public enum SyntaxType {
    COMMENT,
    CONSTANT,
    VARIABLE,
    COMMAND,
    LISTSTART,
    LISTEND,
    GROUPSTART,
    GROUPEND;

    public static SyntaxType getType(String test) {
        return valueOf(test.toUpperCase());
    }
}
