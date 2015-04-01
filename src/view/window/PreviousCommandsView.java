package view.window;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.Constants;
import view.AbstractView;


public class PreviousCommandsView extends Stage implements UpdatableView, AbstractView {

    private StackPane myRoot = new StackPane();
    private Controller myController;
    private TableView<PreviousCommandObject> myTable = new TableView<PreviousCommandObject>();
    private ObservableList<PreviousCommandObject> myList = FXCollections
            .observableList(new ArrayList<PreviousCommandObject>());

    public PreviousCommandsView(Controller controller, double x, double y) {
        setX(x);
        setY(y);
        myController = controller;
        makeColumns();
        setScene(new Scene(myRoot, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setTitle(this.getClass().getSimpleName());
        myRoot.getChildren().add(myTable);
        handleClicks();
        show();
    }

    public class PreviousCommandObject {
        private StringProperty myPreviousCommand;

        public PreviousCommandObject(String command) {
            myPreviousCommandProperty().set(command);
        }

        public StringProperty myPreviousCommandProperty() {
            if (myPreviousCommand == null) {
                myPreviousCommand = new SimpleStringProperty(this, Constants.MY_PREVIOUS_COMMAND);
            }
            return myPreviousCommand;
        }
    }

    private void handleClicks() {
        myTable.getSelectionModel().selectedItemProperty()
                .addListener(
                             new ChangeListener<Object>() {
                                 @Override
                                 public void changed(ObservableValue<? extends Object> observable,
                                                     Object oldValue, Object newValue) {
                                     myController.setCommandLine(((PreviousCommandObject)newValue)
                                             .myPreviousCommandProperty().get());
                                 }
                             });
    }

    @Override
    public void update(Object obj) {
        String str = (String)obj;
        PreviousCommandObject current = new PreviousCommandObject(str);
        myList.add(Constants.ZERO, current);
    }

    @SuppressWarnings("unchecked")
    protected void makeColumns() {
        myTable.setItems(myList);
        TableColumn<PreviousCommandObject, String> previousCommandColumn =
                new TableColumn<PreviousCommandObject, String>("Previous Command");
        previousCommandColumn
                .setCellValueFactory(new PropertyValueFactory<PreviousCommandObject, String>(
                                                                                             "myPreviousCommand"));
        previousCommandColumn.setPrefWidth(Constants.SCREEN_WIDTH);
        myTable.getColumns().setAll(previousCommandColumn);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub
    }

}
