package controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableMap;
import javafx.scene.paint.Color;
import view.AbstractView;
import view.Display;
import view.window.CommandLineView;
import view.window.UpdatableView;
import xml.ReadXML;
import main.Constants;
import main.WriteXML;
import model.command.GeneralCommand;
import model.command.Runner;
import model.command.exception.InvalidArgumentsClosingBracketException;
import model.command.exception.InvalidArgumentsNumberException;
import model.command.exception.InvalidArgumentsTypeException;
import model.command.exception.InvalidArgumentsZeroDivisorException;
import model.command.exception.UnrecognizedNameException;
import model.command.personal.GeneralPersonalCommand;
import model.data.DisplayData;
import model.data.FunctionData;
import model.data.TurtleData;
import model.data.VariableData;
import model.parser.Parser;
import model.turtle.Turtle;
import model.turtle.TurtleList;


public class Controller extends AbstractController {
    private Parser myParser;
    private Map<String, GeneralCommand> myCommands;

    public Controller() {
        myParser = Parser.getInstance();
        myCommands = myParser.loadCommands();
    }

    // Display
    public void changeBackgroundColor(Color newBackgroundColor) {
        setModelProperty(Constants.BACKGROUND_COLOR_PROPERTY, newBackgroundColor);
        Display display = (Display)getRegisteredObject("Display", getViews());
        display.updateBackgroundColor(newBackgroundColor);
    }

    public void changeLanguage(String newLanguage) {
        setModelProperty(Constants.LANGUAGE_PROPERTY, newLanguage);
    }

    public void update(String name, String command) throws IllegalArgumentException,
                                                   IllegalAccessException {
        for (AbstractView view : getViews()) {
            if (view.getClass().getSimpleName().equals(name)) {
                UpdatableView currentView = (UpdatableView)view;
                currentView.update(command);
            }
        }
    }

    public Object getRegisteredObject(String className, List<?> list) {
        for (Object obj : list) {
            if (obj.getClass().getSimpleName().equals(className)) { return obj; }
        }
        // TODO: throw error?
        return null;
    }

    private CommandLineView getCommandLine() {
        return (CommandLineView)getRegisteredObject(Constants.COMMAND_LINE_VIEW, getViews());
    }

    public void setCommandLine(String userInput) {
        getCommandLine().setText(userInput);
    }

    public void appendCommandLine(String userInput) {
        getCommandLine().appendText(userInput);
    }

    public void runCommand(String userInput) throws UnrecognizedNameException,
                                            InvalidArgumentsClosingBracketException,
                                            InvalidArgumentsTypeException,
                                            InvalidArgumentsNumberException,
                                            InvalidArgumentsZeroDivisorException {

        List<?> views = getViews();
        Display display = (Display)getRegisteredObject(Constants.DISPLAY_NAME, views);
        TurtleList myTurtles = display.getTurtleList();
        Turtle[] iterate =
                myTurtles.getTellTurtles().toArray(new Turtle[myTurtles.getTellTurtles().size()]);
        Double[] identities = new Double[iterate.length];
        for (int i = 0; i < iterate.length; i++) {
            Double thisDouble = Double.parseDouble(String.valueOf(iterate[i].getName()));
            myTurtles.tell(new Double[] { (thisDouble) });
            Runner currentRunner =
                    new Runner(myTurtles, display, myCommands, getPersonalCommands(),
                               getVars());
            currentRunner.runCommand(userInput);
            identities[i] = Double.parseDouble(iterate[i].getName().toString());
        }
        if (!myTurtles.getTellCalled()) {
            myTurtles.resetTellTurtles();
            myTurtles.tell(identities);
        }

    }

    public void setLanguage(String languageName) {
        changeLanguage(languageName);
        myParser.setLanguage(languageName);
    }

    public void addPaletteColor(int index, Color color) {
        DisplayData displayData =
                (DisplayData)getRegisteredObject(Constants.DISPLAY_DATA, getModels());
        displayData.addPaletteColor(index, color);
    }

    public Color getColorFromIndex(int index) {
        DisplayData displayData =
                (DisplayData)getRegisteredObject(Constants.DISPLAY_DATA, getModels());
        return displayData.getPaletteColor(index);
    }

    public void writeXML(File file) {
        TurtleData turtleData = (TurtleData)getRegisteredObject(Constants.TURTLE_DATA, getModels());
        Display display = (Display)getRegisteredObject("Display", getViews());
        display.getTurtleList().linkTurtleData(turtleData);
        WriteXML.writeXML(this, turtleData.getTurtles(), file);
    }

    public ObservableMap<String, Double> getVars() {
        VariableData variableData =
                (VariableData)getRegisteredObject(Constants.VARIABLE_DATA, getModels());
        return variableData.getVars();
    }

    public void addVariable(String key, Double value) {
        VariableData variableData =
                (VariableData)getRegisteredObject(Constants.VARIABLE_DATA, getModels());
        variableData.addVariable(key, value);

    }

    public ObservableMap<String, GeneralPersonalCommand> getPersonalCommands() {
        FunctionData functionData =
                (FunctionData)getRegisteredObject(Constants.FUNCTION_DATA, getModels());
        return functionData.getPersonalCommands();
    }

    public void addFunction(String key, GeneralPersonalCommand personalCommand) {
        FunctionData functionData =
                (FunctionData)getRegisteredObject(Constants.FUNCTION_DATA, getModels());
        functionData.addFunction(key, personalCommand);
    }
    
    // Broken
    public void loadWorkspace(File file) throws Exception {
        ReadXML myReader = new ReadXML(file);
        @SuppressWarnings("unused")
		Map<String, List<String>> myMap = myReader.makeMap();

        // changeBackgroundColor(Color.web(myMap.get("backgroundColor").get(Constants.ZERO)));
        // changeLanguage(myMap.get("language").get(Constants.ZERO));
        // makeNewVariables(myMap);
        // makeNewFunctions(myReader.get("CurrentFunctions"));

    }
    
    // Broken (Used by loadWorkspace())
    @SuppressWarnings("unused")
	private void makeNewVariables(Map<String, List<String>> xmlMap) {
        VariableData variableData =
                (VariableData)getRegisteredObject(Constants.VARIABLE, getModels());
        variableData.clear();
        for (int i = 0; i < xmlMap.get(Constants.VARIABLE_NAME).size(); i++) {
            variableData.addVariable(xmlMap.get(Constants.VARIABLE_NAME).get(i),
                                     Double.parseDouble(xmlMap.get(Constants.VALUE).get(i)));
        }
    }

}
