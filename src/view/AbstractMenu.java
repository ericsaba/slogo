package view;

import java.util.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import controller.Controller;


public abstract class AbstractMenu extends Menu {
    private Controller myController;

    public AbstractMenu(String name, Controller controller) {
        super(name);
        myController = controller;
    }

    public void addChoices(List<String> list) {
        getItems().removeAll(getItems());
        for (String c : list) {
            MenuItem curItem = new MenuItem(c);
            curItem.setOnAction(e -> updateOption(c));
            getItems().add(curItem);
        }
    }

    protected abstract void updateOption(String s);

    protected Controller getController() {
        return myController;
    }
}
