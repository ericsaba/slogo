package view;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;
import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import main.Constants;
import model.data.TurtleData;
import model.turtle.Turtle;
import main.Workspace;


public class PreferencesPanel extends MenuBar {
    private BackgroundMenu myBackgroundMenu;
    private LanguagesMenu myLanguagesMenu;
    private TurtleMenu myTurtleMenu;
    private Display myDisplay;
    private Controller myController;

    public PreferencesPanel(Display display,
                            Controller controller,
                            List<String> colors,
                            List<String> languages) {
        myDisplay = display;
        myController = controller;
        makeList(colors, Constants.DEFAULT_COLORS);
        makeList(languages, Constants.DEFAULT_LANGUAGES);
        makeMenus(colors, languages);
    }

    private void makeList(List<String> a, String propertyName) {
        for (String key : ResourceBundle.getBundle(Constants.DEFAULT_LISTS_PACKAGE +
                                                   propertyName).keySet()) {
            a.add(key);
        }
    }

    private void makeMenus(List<String> colors, List<String> languages) {
        myBackgroundMenu = new BackgroundMenu(myController);
        myLanguagesMenu = new LanguagesMenu(myController);
        Menu other = createOtherMenu();
        Menu fileMenu = createFileMenu();

        addItemsToMenus(colors, languages);

        getMenus().addAll(fileMenu, myTurtleMenu, myBackgroundMenu, myLanguagesMenu, other);
    }

    private Menu createOtherMenu() {
        MenuItem changeImageMenu = new MenuItem(Constants.CHANGE_TURTLE_IMAGE);
        changeImageMenu.setOnAction(e -> changeImage());
        MenuItem helpMenu = new MenuItem(Constants.HELP);
        helpMenu.setOnAction(e -> showHelp());
        Menu other = new Menu(Constants.OTHER);
        other.getItems().addAll(changeImageMenu, helpMenu);
        return other;
    }

    private Menu createFileMenu() {
        MenuItem newMenu = new MenuItem(Constants.NEW_WORKSPACE);
        newMenu.setOnAction(e -> addWorkspace());
        MenuItem saveMenu = new MenuItem(Constants.SAVE);
        saveMenu.setOnAction(e -> saveWorkspace());
        MenuItem loadMenu = new MenuItem(Constants.LOAD);
        loadMenu.setOnAction(e -> loadWorkspace());
        Menu fileMenu = new Menu(Constants.FILE);
        fileMenu.getItems().addAll(newMenu, saveMenu, loadMenu);
        return fileMenu;
    }

    private void loadWorkspace() {

        File file =
                getFileChooser(Constants.LOAD_XML, Constants.XML_TYPE, Constants.XML_EXTENSION)
                        .showOpenDialog(myDisplay);
        try {
            myController.loadWorkspace(file);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void saveWorkspace() {
        File xmlFile = getFileChooser(Constants.SAVE_XML,
                                      Constants.XML_TYPE,
                                      Constants.XML_EXTENSION)
                .showSaveDialog(myDisplay);
        if (xmlFile != null) {
            myController.writeXML(xmlFile);
        }
    }

    private void addWorkspace() {
        new Workspace();
    }

    private void addItemsToMenus(List<String> colors, List<String> languages) {
        myBackgroundMenu.addChoices(colors);
        // myLineMenu.addChoices(colors);
        myLanguagesMenu.addChoices(languages);

        TurtleData turtleData =
                (TurtleData)myController.getRegisteredObject(Constants.TURTLE_DATA,
                                                             myController.getModels());

        ObservableMap<String, Turtle> turtleMap = FXCollections.observableHashMap();
        turtleData.getBoolean().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean arg1, Boolean arg2) {
                updateMap(turtleData.getTurtles(), turtleMap);
            }
        });
        updateMap(turtleData.getTurtles(), turtleMap);

        myTurtleMenu = new TurtleMenu(myController, turtleMap);
    }

    private void updateMap(List<Turtle> list, ObservableMap<String, Turtle> turtleMap) {
        for (Turtle t : list) {
            turtleMap.put(String.format("Turtle %d", t.getName()), t);
        }
    }

    private void changeImage() {
        File newImage = getFileChooser(Constants.CHOOSE_A_NEW_IMAGE,
                                       Constants.IMAGE_FILE,
                                       Constants.IMAGE_EXTENSIONS)
                .showOpenDialog(myDisplay);
        if (newImage != null) {
            myDisplay.setImage(newImage.toURI().toString());
        }
    }

    private FileChooser getFileChooser(String title, String fileType, String[] extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter(fileType, extensions));
        return fileChooser;
    }

    private void showHelp() {
        new HelpWindow();
    }
}
