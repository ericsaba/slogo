package view.window;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.Constants;
import model.command.personal.GeneralPersonalCommand;
import view.AbstractView;


public class FunctionsView extends Stage implements AbstractView {

    private Controller myController;
    private StackPane myRoot = new StackPane();
    private TableView<Function> myTable = new TableView<Function>();
    private ObservableMap<String, GeneralPersonalCommand> personalCommands;
    private ObservableList<Function> myList = FXCollections
            .observableList(new ArrayList<Function>());

    public FunctionsView(Controller controller,
                         double x,
                         double y) {
        myController = controller;
        setX(x + Constants.SCREEN_WIDTH);

        setY(Constants.ZERO);// - CommandLine.AREA_HEIGHT);
        setHeight(Constants.DISPLAY_HEIGHT + Constants.FIELD_HEIGHT * Constants.THREE /
                  Constants.TWO);
        makeColumns();
        setScene(new Scene(myRoot, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setTitle(this.getClass().getSimpleName());
        myRoot.getChildren().add(myTable);
        handleClicks();
        show();

        personalCommands = myController.getPersonalCommands();
        personalCommands.addListener(new MapChangeListener<String, GeneralPersonalCommand>() {
            @Override
            public void onChanged(Change<? extends String, ? extends GeneralPersonalCommand> arg) {
                update();
            }
        });
    }

    private void handleClicks() {
        // TODO Auto-generated method stub
        myTable.getSelectionModel().selectedItemProperty()
                .addListener(
                             new ChangeListener<Object>() {
                                 @Override
                                 public void changed(ObservableValue<? extends Object> observable,
                                                     Object oldValue, Object newValue) {
                                     Function function = (Function)newValue;
                                     String name = function.myNameProperty().get();
                                     String arguments = function.myArgumentsProperty().get();
                                     myController
                                             .setCommandLine(name + Constants.SPACE + arguments);
                                 }
                             });

    }

    public class Function {
        private StringProperty myName;
        private StringProperty myArguments;
        private StringProperty myCommands;

        public Function(String name, String arguments, String commands) {
            myNameProperty().set(name);
            myArgumentsProperty().set(arguments);
            myCommandsProperty().set(commands);
        }

        public StringProperty myNameProperty() {
            if (myName == null) {
                myName = new SimpleStringProperty(this, "myName");
            }
            return myName;
        }

        public StringProperty myArgumentsProperty() {
            if (myArguments == null) {
                myArguments = new SimpleStringProperty(this, "myArguments");
            }
            return myArguments;
        }

        public StringProperty myCommandsProperty() {
            if (myCommands == null) {
                myCommands = new SimpleStringProperty(this, "myCommands");
            }
            return myCommands;
        }
    }

    private void update() {
        myList.clear();
        for (String name : personalCommands.keySet()) {
            Function current =
                    new Function(name, personalCommands.get(name).getVars(), personalCommands
                            .get(name).getText());
            myList.add(Constants.ZERO, current);
        }
    }

    @SuppressWarnings("unchecked")
    protected void makeColumns() {
        myTable.setItems(myList);

        TableColumn<Function, String> nameColumn = new TableColumn<Function, String>("Function");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Function, String>("myName"));
        TableColumn<Function, String> varColumn = new TableColumn<Function, String>("Variables");
        varColumn.setCellValueFactory(new PropertyValueFactory<Function, String>("myArguments"));
        TableColumn<Function, String> cmdColumn = new TableColumn<Function, String>("Command");
        cmdColumn.setCellValueFactory(new PropertyValueFactory<Function, String>("myCommands"));

        nameColumn.setPrefWidth(Constants.SCREEN_WIDTH / Constants.THREE);
        varColumn.setPrefWidth(Constants.SCREEN_WIDTH / Constants.THREE);
        cmdColumn.setPrefWidth(Constants.SCREEN_WIDTH / Constants.THREE);

        myTable.getColumns().setAll(nameColumn, varColumn, cmdColumn);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub
    }

}
