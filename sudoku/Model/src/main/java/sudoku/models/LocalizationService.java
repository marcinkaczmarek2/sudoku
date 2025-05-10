package sudoku.models;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationService {
    private final ResourceBundle bundle;
    private static LocalizationService instance;

    private LocalizationService(Locale locale) {
        this.bundle = ResourceBundle.getBundle("messages", locale);
    }

    public static void initialize(Locale locale) {
        instance = new LocalizationService(locale);
    }

    public static LocalizationService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("LocalizationService not initialized");
        }
        return instance;
    }

    public String get(String key) {
        return bundle.getString(key);
    }
}

