package view;

import javafx.stage.Screen;
import main.Constants;
import model.data.TurtleData;
import model.turtle.TurtleList;
import view.window.CommandLineView;
// import view.window.DisplayView;
import view.window.FunctionsView;
import view.window.PreviousCommandsView;
import view.window.VariablesView;
import controller.Controller;


public class ViewCollection {
    private Controller myController;
    private FunctionsView myFunctions;
    private CommandLineView myCommandLine;
    private PreviousCommandsView myPreviousCommands;
    private VariablesView myVariables;
    private Display myDisplay;
    private double displayX;
    private double displayY;

    public ViewCollection(Controller controller) {
        displayX = Screen.getPrimary().getBounds().getWidth() /
                   Constants.TWO - Constants.DISPLAY_WIDTH /
                   Constants.TWO;
        displayY = Constants.ZERO;

        myController = controller;
        initializeViews();
    }

    private void initializeViews() {

        myCommandLine = new CommandLineView(myController, displayX, displayY);
        myController.addView(myCommandLine);

        myPreviousCommands =
                new PreviousCommandsView(myController, displayX - Constants.DISPLAY_WIDTH /
                                                       Constants.TWO, displayY);
        myVariables =
                new VariablesView(myController, displayX - Constants.DISPLAY_WIDTH /
                                                Constants.TWO, displayY +
                                                               Constants.DISPLAY_HEIGHT /
                                                               Constants.TWO +
                                                               Constants.NINE);
        myFunctions =
                new FunctionsView(myController, displayX +
                                                Constants.DISPLAY_WIDTH /
                                                Constants.TWO, displayY);
        myDisplay =
                new Display(myController, new TurtleList((TurtleData)myController.getRegisteredObject(Constants.TURTLE_DATA, myController.getModels())), Constants.DISPLAY_WIDTH,
                            Constants.DISPLAY_HEIGHT);

        myController.addView(myPreviousCommands);
        myController.addView(myVariables);
        myController.addView(myFunctions);
        myController.addView(myDisplay);
    }

    public FunctionsView getFunctions() {
        return myFunctions;
    }

    public PreviousCommandsView getPreviousCommands() {
        return myPreviousCommands;
    }

    public VariablesView getVariables() {
        return myVariables;
    }

    public Display getDisplay() {
        return myDisplay;
    }

    public CommandLineView getCommandLine() {
        return myCommandLine;
    }

}
