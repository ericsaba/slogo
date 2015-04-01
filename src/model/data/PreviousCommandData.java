package model.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class PreviousCommandData extends AbstractDataModel {
    private ObservableList<String> myPreviousCommands;

    public PreviousCommandData() {
        myPreviousCommands = FXCollections.observableArrayList();
    }

    public ObservableList<String> getPreviousCommands() {
        return myPreviousCommands;
    }
}
