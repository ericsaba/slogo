package model.data;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import main.Constants;


public class DisplayData extends AbstractDataModel {

    private Map<Integer, Color> myPalette = new HashMap<Integer, Color>();
    private Color myBackgroundColor;
    private StringProperty myLanguage = new SimpleStringProperty();
    private IntegerProperty myTurtleCount = new SimpleIntegerProperty();
    private ResourceBundle myResources = ResourceBundle.getBundle(Constants.DEFAULT_LISTS_PACKAGE +
                                                                  Constants.DEFAULT_COLORS);

    public DisplayData() {
        makeColorsMap();
        setBackgroundColor(Constants.DEFAULT_START_COLOR_INDEX);
        setLanguage(Constants.ENGLISH);
        setTurtleCount(Constants.ONE);
    }

    private void makeColorsMap() {
        int count = Constants.ZERO;
        for (String key : myResources.keySet()) {
            myPalette.put(count, Color.web(key.toLowerCase()));
            count++;
        }
    }

    // Accessors

    public StringProperty languageProperty() {
        return myLanguage;
    }

    public IntegerProperty turtleCountProperty() {
        return myTurtleCount;
    }

    public final Color getBackgroundColor() {
        return myBackgroundColor;
    }

    public final String getLanguage() {
        return myLanguage.get();
    }

    public final int getTurtleCount() {
        return myTurtleCount.get();
    }

    public final void setBackgroundColor(int backgroundColor) {
        myBackgroundColor = myPalette.get(backgroundColor);
    }

    public final void setBackgroundColor(Color color) {
        myBackgroundColor = color;
    }

    public final void setLanguage(String language) {
        myLanguage.set(language);
    }

    public final void setTurtleCount(int turtleCount) {
        myTurtleCount.set(turtleCount);
    }

    public final void addPaletteColor(int index, Color color) {
        myPalette.put(index, color);
    }

    public final Color getPaletteColor(int index) {
        return myPalette.get(index);
    }

    public final Map<Integer, Color> getPaletteMap() {
        return myPalette;
    }
}
