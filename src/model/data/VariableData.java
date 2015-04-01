package model.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;


public class VariableData extends AbstractDataModel {
    private ObservableMap<String, Double> vars;

    public VariableData() {
        vars = FXCollections.observableHashMap();
    }

    public ObservableMap<String, Double> getVars() {
        return vars;
    }

    public void addVariable(String key, Double value) {
        vars.put(key, value);
    }

    public void clear() {
        vars.clear();
    }
}
