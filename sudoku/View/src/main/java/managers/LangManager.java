package managers;

import java.util.Locale;
import java.util.ResourceBundle;

public class LangManager {
    public static ResourceBundle resources = ResourceBundle.getBundle("i18n.messages", Locale.ENGLISH);

    public static ResourceBundle getBundle() {
        return resources;
    }

    public static void setLocale(Locale locale) {
        resources = ResourceBundle.getBundle("i18n.messages", locale);
    }
}