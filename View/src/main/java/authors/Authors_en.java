package authors;

import java.util.ListResourceBundle;

public class Authors_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"author1.name", "Martin Kaczmarek"},
                {"author2.name", "Bartholomew Infantry"},
        };
    }
}
