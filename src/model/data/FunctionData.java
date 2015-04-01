package model.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import model.command.personal.GeneralPersonalCommand;


public class FunctionData extends AbstractDataModel {
    private ObservableMap<String, GeneralPersonalCommand> myPersonalCommands;

    public FunctionData() {
        myPersonalCommands = FXCollections.observableHashMap();
    }

    public ObservableMap<String, GeneralPersonalCommand> getPersonalCommands() {
        return myPersonalCommands;
    }

    public void addFunction(String key, GeneralPersonalCommand personalCommand) {
        myPersonalCommands.put(key, personalCommand);
    }
}
