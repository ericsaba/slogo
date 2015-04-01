package view;

import javafx.scene.paint.Color;
import main.Constants;
import controller.Controller;


public class BackgroundMenu extends AbstractMenu {

    public BackgroundMenu(Controller controller) {
        super(Constants.BACKGROUND_COLOR, controller);
    }

    @Override
    protected void updateOption(String s) {
        getController().changeBackgroundColor(Color.web(s.toLowerCase()));
    }
}
