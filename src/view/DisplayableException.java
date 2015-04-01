package view;

import java.util.ResourceBundle;
import main.Constants;
import main.Language;
import org.controlsfx.dialog.Dialogs;


public class DisplayableException {
    public DisplayableException(Exception exception) {

        ResourceBundle myResources =
                ResourceBundle.getBundle(Constants.DEFAULT_LANGUAGE_PACKAGE +
                                         Language.getInstance().getLanguage());
        Dialogs.create()
                .title(myResources.getString(exception.getClass().getSimpleName()))
                .masthead(myResources.getString(exception.getClass().getSimpleName() +
                                                Constants.MASTHEAD))
                .message(myResources.getString(exception.getClass().getSimpleName() +
                                               Constants.MESSAGE))
                .showException(exception);
    }
}
