package view;

import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.Constants;


public class HelpWindow extends Stage {
    public HelpWindow() {
        WebView browser = new WebView();
        String url = HelpWindow.class.getResource(Constants.HELP_PATH).toExternalForm();
        browser.getEngine().load(url);
        setScene(new Scene(browser, Constants.HELP_WINDOW_WIDTH, Constants.HELP_WINDOW_HEIGHT));
        show();
    }
}
