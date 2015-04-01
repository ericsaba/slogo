package view;

import main.Constants;
import controller.Controller;


public class LanguagesMenu extends AbstractMenu {

    public LanguagesMenu(Controller controller) {
        super(Constants.LANGUAGE, controller);
    }

    @Override
    protected void updateOption(String s) {
        getController().setLanguage(s);
    }

}
