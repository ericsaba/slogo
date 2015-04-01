package view.window;

import java.beans.PropertyChangeEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.Constants;
import view.AbstractView;
import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import controller.Controller;


public class CommandLineView extends Stage implements AbstractView {

    public class ResizedTextField extends TextField {
        ResizedTextField() {
            setPrefWidth(Constants.DISPLAY_WIDTH);
        }
    }

    private Group myRoot = new Group();
    private TextInputControl myCommandLine = new ResizedTextField();
    private Scene myScene;
    private Controller myController;
    private BooleanProperty enterPressed = new SimpleBooleanProperty(false);
    private BooleanProperty shiftPressed = new SimpleBooleanProperty(false);

    public CommandLineView(Controller controller, double displayX, double displayY) {
        myRoot.getChildren().add(myCommandLine);
        myController = controller;
        setTitle(Constants.COMMAND_LINE);
        initializeCommandLine();
        setScene(myScene);
        setX(displayX);
        setY(Constants.DISPLAY_HEIGHT + displayY + Constants.GAP_HEIGHT);
        show();
    }

    public void setText(String s) {
        myCommandLine.setText(s);
    }

    public void appendText(String s) {
        myCommandLine.appendText(s);
    }

    private void initializeCommandLine() {

        myScene = new Scene(myRoot);
        myScene.setOnKeyPressed(e -> handleKeyInput(e));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e));
    }

    private void handleKeyInput(KeyEvent e) {
        if (myCommandLine instanceof TextArea) {
            handleAreaPress(e);
        }
        else {
            handlePress(e, true);
            if (enterPressed.and(shiftPressed.not()).get()) {
                handleEnter();
            }
            else if (enterPressed.and(shiftPressed).get()) {
                handleShiftEnter();
            }
        }
    }

    private void handleKeyRelease(KeyEvent e) {
        handlePress(e, false);
    }

    private void handlePress(KeyEvent e, boolean press) {
        KeyCode keyCode = e.getCode();
        if (keyCode == KeyCode.SHIFT) {
            shiftPressed.set(press);
        }
        else if (keyCode == KeyCode.ENTER) {
            enterPressed.set(press);
        }
    }

    public void handleAreaPress(KeyEvent event) {
        if (myCommandLine instanceof TextArea && event.getCode() == KeyCode.ENTER &&
            ((TextAreaSkin)myCommandLine.getSkin()).getBehavior() instanceof TextAreaBehavior) {
            if (event.isShiftDown()) {
                handleShiftEnter();
            }
            event.consume();
        }
    }

    private void handleEnter() {
        resizeCommandLine(Constants.TEXT_AREA, Constants.AREA_HEIGHT);
    }

    private void handleShiftEnter() {
        String command = myCommandLine.getText();
        try {
            myController.runCommand(sanitizeOutput(command));
            myController.update(Constants.PREVIOUS_COMMANDS_VIEW, command);
        }
        catch (Exception myException) {
            myException.printStackTrace();
            // new DisplayableException(myException);

            shiftPressed.set(false);
            enterPressed.set(false);
        }
        resizeCommandLine(Constants.TEXT_FIELD, Constants.FIELD_HEIGHT);
    }

    private void resizeCommandLine(String type, double height) {
        myRoot.getChildren().remove(myCommandLine);
        makeNewCommandLine(type);
        myRoot.getChildren().add(myCommandLine);
        setHeight(height);

    }

    private String sanitizeOutput(String s) {
        return s.replaceAll(Constants.NEW_LINE, Constants.SPACE).trim()
                .replaceAll(Constants.S_PLUS, Constants.SPACE);
    }

    private void makeNewCommandLine(String type) {
        if (type.equals(Constants.TEXT_AREA)) {
            String curText = myCommandLine.getText();
            myCommandLine = new TextArea(curText + Constants.NEW_LINE);
            myCommandLine.end();
        }
        else {
            myCommandLine = new ResizedTextField();
        }

    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub

    }

}
