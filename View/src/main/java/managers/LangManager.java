package managers;

import sudoku.models.LocalizationService;

import java.util.Locale;
import java.util.ResourceBundle;

public class LangManager {
    public static ResourceBundle resources = ResourceBundle.getBundle("i18n.labels", Locale.ENGLISH);

    public static ResourceBundle getBundle() {
        return resources;
    }

    public static void setLocale(Locale locale) {
        resources = ResourceBundle.getBundle("i18n.labels", locale);
        LocalizationService.initialize(locale);
    }
}