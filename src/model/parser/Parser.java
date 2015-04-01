package model.parser;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import main.Constants;
import main.Language;
import model.command.GeneralCommand;
import model.command.exception.UnrecognizedNameException;


public class Parser {
    private List<Entry<Pattern, String>> languagePatterns;
    private List<Entry<Pattern, String>> syntaxPatterns;
    private static Parser instance;

    private Parser() {
        syntaxPatterns = new ArrayList<>();
        syntaxPatterns.addAll(makePatterns(Constants.DEFAULT_LISTS_PACKAGE +
                                           Constants.DEFAULT_SYNTAX));
        setLanguage(Constants.ENGLISH);
    }

    public static synchronized Parser getInstance() {
        if (instance == null) { return instance = new Parser(); }
        return instance;
    }

    private List<Entry<Pattern, String>> makePatterns(String resourcePath) {
        ResourceBundle resources = ResourceBundle.getBundle(resourcePath);
        List<Entry<Pattern, String>> patterns = new ArrayList<>();

        for (String key : resources.keySet()) {
            String regex = resources.getString(key).trim();
            patterns.add(new SimpleEntry<Pattern, String>
                    (Pattern.compile(regex, Pattern.CASE_INSENSITIVE),
                     key));
        }

        return patterns;
    }

    private String findMatch(String test, List<Entry<Pattern, String>> patterns)
                                                                                throws UnrecognizedNameException {
        for (Entry<Pattern, String> pat : patterns) {
            if (match(test, pat.getKey())) { return pat.getValue(); }
        }
        throw new UnrecognizedNameException();
    }

    private boolean match(String input, Pattern regex) {
        return regex.matcher(input).matches();
    }

    private Map<String, CommandType> getPossibleCommands() {
        ResourceBundle allCommands =
                ResourceBundle.getBundle(Constants.DEFAULT_LISTS_PACKAGE +
                                         Constants.DEFAULT_COMMANDS);
        Map<String, CommandType> result = new HashMap<>();
        for (String name : allCommands.keySet()) {
            result.put(name, CommandType.getType(allCommands.getString(name)));
        }
        return result;
    }

    public void setLanguage(String languageName) {
        Language.getInstance().setLanguage(languageName);
        languagePatterns = new ArrayList<>();
        languagePatterns.addAll(makePatterns(Constants.DEFAULT_LANGUAGE_PACKAGE + languageName));
    }

    public Map<String, GeneralCommand> loadCommands() {
        Map<String, CommandType> commandTypes = getPossibleCommands();
        Map<String, GeneralCommand> commands = new HashMap<>();
        boolean missingCommand = false;

        for (String commandName : commandTypes.keySet()) {
            try {
                commands.put(commandName,
                             (GeneralCommand)Class.forName(Constants.DEFAULT_COMMANDS_PACKAGE +
                                                           commandTypes.get(commandName) +
                                                           commandName).newInstance());
            }
            catch (Exception e) {
                missingCommand = true;
            }
        }

        if (missingCommand) {
            // TODO: Unhandled Exception -- Simple ignoring the non-existing commands.
        }

        return commands;
    }

    public String findCommandName(String test) throws UnrecognizedNameException {
        return findMatch(test, languagePatterns);
    }

    public SyntaxType decideSyntax(String test) throws UnrecognizedNameException {
        return SyntaxType.getType(findMatch(test, syntaxPatterns));
    }

}
