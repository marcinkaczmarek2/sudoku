package authors;

import java.util.ListResourceBundle;

public class Authors_pl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"author1.name", "Marcin Kaczmarek"},
                {"author2.name", "Bartłomiej Piechota"},
        };
    }
}
