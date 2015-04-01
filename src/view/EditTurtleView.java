package view;

import java.util.ArrayList;
import java.util.TreeMap;

import main.Constants;
import model.turtle.Pen;
import model.turtle.Turtle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EditTurtleView extends Stage {
	private StackPane myRoot = new StackPane();
    private TableView<Variable> myTable = new TableView<Variable>();
    private ObservableMap<String, Double> myVars = FXCollections.observableMap(new TreeMap<String, Double>());
    private ObservableList<Variable> myList = FXCollections
            .observableList(new ArrayList<Variable>());

    public EditTurtleView(Turtle t) {
    	makeColumns();
        setScene(new Scene(myRoot, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setTitle(String.format("%s %d", Constants.EDIT_TURTLE, t.getName()));
        myRoot.getChildren().add(myTable);
        show();
        
        makeMap(t);
//        myVars.addListener(new MapChangeListener<String, Double>() {
//            @Override
//            public void onChanged(Change<? extends String, ? extends Double> arg) {
//                update();
//            }
//        });
        update();
    }
    
    private void update() {
        myList.clear();
        for (String name : myVars.keySet()) {
            Variable current = new Variable(name, myVars.get(name));
            myList.add(Constants.ZERO, current);
        }
    }
    
    private void makeMap(Turtle turtle) {
		myVars.put(Constants.TURTLE_ID, turtle.getName().doubleValue());
		myVars.put(Constants.TURTLE_XCOR, turtle.getX());
		myVars.put(Constants.TURTLE_YCOR, turtle.getY());
		myVars.put(Constants.TURTLE_HEADING, turtle.getHeading());
		myVars.put(Constants.TURTLE_SHOWING, turtle.isShowing());
		myVars.put(Constants.TURTLE_PENDOWN, turtle.isPenDown());
		Pen pen = turtle.getPen();
		myVars.put(Constants.PEN_THICKNESS, pen.getThickness());
		myVars.put(Constants.PEN_COLOR_INDEX, (double)pen.getColorIndex());
		myVars.put(Constants.PEN_LINE_STYLE, (double)pen.getStyle().getIndex());
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
}