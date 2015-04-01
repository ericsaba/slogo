package view.window;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import controller.Controller;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import view.AbstractView;


public class VariablesView extends Stage implements AbstractView {

    private StackPane myRoot = new StackPane();
    private TableView<Variable> myTable = new TableView<Variable>();
    private ObservableMap<String, Double> myVars;
    private Controller myController;
    private ObservableList<Variable> myList = FXCollections
            .observableList(new ArrayList<Variable>());

    public VariablesView(Controller controller,
                         double x,
                         double y) {

        setX(x);
        setY(y + Constants.FIELD_HEIGHT * Constants.THREE / Constants.FOUR);
        makeColumns();
        myController = controller;
        setScene(new Scene(myRoot, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setTitle(this.getClass().getSimpleName());
        myRoot.getChildren().add(myTable);
        handleClicks();
        show();

        myVars = myController.getVars();
        myVars.addListener(new MapChangeListener<String, Double>() {
            @Override
            public void onChanged(Change<? extends String, ? extends Double> arg) {
                update();
            }
        });
    }

    public class Variable {
        private StringProperty myName;
        private DoubleProperty myValue;

        public Variable(String name, Double value) {
            myNameProperty().set(name);
            myValueProperty().set(value);
        }

        public StringProperty myNameProperty() {
            if (myName == null) {
                myName = new SimpleStringProperty(this, "myName");
            }
            return myName;
        }

        public DoubleProperty myValueProperty() {
            if (myValue == null) {
                myValue = new SimpleDoubleProperty(this, "myValue");
            }
            return myValue;
        }
    }

    private void update() {
        myList.clear();
        for (String name : myVars.keySet()) {
            Variable current = new Variable(name, myVars.get(name));
            myList.add(Constants.ZERO, current);
        }
    }

    private void handleClicks() {
        myTable.getSelectionModel().selectedItemProperty()
                .addListener(
                             new ChangeListener<Object>() {
                                 @Override
                                 public void changed(ObservableValue<? extends Object> observable,
                                                     Object oldValue, Object newValue) {
                                     myController.appendCommandLine(((Variable)newValue)
                                             .myNameProperty().get());
                                 }
                             });
    }

    @SuppressWarnings("unchecked")
    protected void makeColumns() {
        myTable.setItems(myList);

        TableColumn<Variable, String> nameColumn = new TableColumn<Variable, String>("Variable");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Variable, String>("myName"));
        TableColumn<Variable, Double> valueColumn = new TableColumn<Variable, Double>("Value");
        valueColumn.setCellValueFactory(new PropertyValueFactory<Variable, Double>("myValue"));

        nameColumn.setPrefWidth(Constants.SCREEN_WIDTH / Constants.TWO);
        valueColumn.setPrefWidth(Constants.SCREEN_WIDTH / Constants.TWO);

        myTable.getColumns().setAll(nameColumn, valueColumn);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub
    }

    public ObservableMap<String, Double> getMap() {
        return myVars;
    }
}
