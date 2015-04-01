package main;

public class Language {
    private static Language instance;
    private String myLanguage;

    private Language() {
        myLanguage = Constants.ENGLISH;
    }

    public static synchronized Language getInstance() {
        if (instance == null) {
            instance = new Language();
        }
        return instance;
    }

    public String getLanguage() {
        return myLanguage;
    }

    public void setLanguage(String language) {
        myLanguage = language;
    }
}
