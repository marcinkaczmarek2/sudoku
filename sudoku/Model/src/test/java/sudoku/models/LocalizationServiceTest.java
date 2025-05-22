package sudoku.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.models.LocalizationService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocalizationServiceTest {

    @BeforeEach
    public void resetSingleton() throws Exception {
        Field instanceField = LocalizationService.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    @Test
    public void testGetInstanceThrowsIfNotInitialized() {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> LocalizationService.getInstance()
        );
        assertEquals("LocalizationService not initialized", exception.getMessage());
    }
}
