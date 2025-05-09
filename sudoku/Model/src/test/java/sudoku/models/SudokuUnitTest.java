package sudoku.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class SudokuUnitTest {

    @Test
    public void equalsCorrectnessTrue() {
        SudokuRow row1 = new SudokuRow();
        SudokuRow row2 = new SudokuRow();

        for (int i = 0; i < 9; i++) {
            row1.fields[i].setFieldValue(i + 1);
            row2.fields[i].setFieldValue(i + 1);
        }

        assertEquals(row1, row2);
    }

    @Test
    public void equalsCorrectnessFalse() {
        SudokuRow row1 = new SudokuRow();
        SudokuRow row2 = new SudokuRow();

        for (int i = 0; i < 9; i++) {
            row1.fields[i].setFieldValue(i + 1);
            row2.fields[i].setFieldValue(i + 1);
        }

        row2.fields[0].setFieldValue(9);

        assertNotEquals(row1, row2);
    }

    @Test
    public void hashCodeCorrectnessTrue() {
        SudokuRow row1 = new SudokuRow();
        SudokuRow row2 = new SudokuRow();

        for (int i = 0; i < 9; i++) {
            row1.fields[i].setFieldValue(i + 1);
            row2.fields[i].setFieldValue(i + 1);
        }

        assertEquals(row1.hashCode(), row2.hashCode());
    }

    @Test
    public void hashCodeCorrectnessFalse() {
        SudokuRow row1 = new SudokuRow();
        SudokuRow row2 = new SudokuRow();

        for (int i = 0; i < 9; i++) {
            row1.fields[i].setFieldValue(i + 1);
            row2.fields[i].setFieldValue(i + 1);
        }

        row2.fields[8].setFieldValue(0);

        assertNotEquals(row1.hashCode(), row2.hashCode());
    }

    @Test
    public void toStringCorrectness() {
        SudokuRow row = new SudokuRow();

        for (int i = 0; i < 9; i++) {
            row.fields[i].setFieldValue(i + 1);
        }

        String stringOutput = row.toString();

        assertNotNull(stringOutput);

        for (int i = 1; i <= 9; i++) {
            assertTrue(stringOutput.contains(Integer.toString(i)));
        }
    }

    @Test
    public void equalsWithNull() {
        SudokuRow row = new SudokuRow();
        assertNotEquals(null, row);
    }

    @Test
    public void equalsWithDifferentClass() {
        SudokuRow row = new SudokuRow();
        String notARow = "I am not a SudokuRow";

        assertNotEquals(row, notARow);
    }

    @Test
    public void equalsWithSameInstance() {
        SudokuRow row = new SudokuRow();

        assertEquals(row, row);
    }

    @Test
    public void equalsWithNullObjectTriggersNullCheck() {
        SudokuRow row = new SudokuRow();
        assertFalse(row.equals(null));
    }

    @Test
    public void testCloneIsDeepCopy() {
        SudokuRow original = new SudokuRow();
        original.fields[0].setFieldValue(5);
        original.fields[1].setFieldValue(3);

        SudokuRow clone = (SudokuRow) original.clone();

        assertNotSame(original, clone);
        assertNotSame(original.fields, clone.fields); // This now passes

        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            assertNotSame(original.fields[i], clone.fields[i]);
            assertEquals(original.fields[i].getFieldValue(), clone.fields[i].getFieldValue());
        }
    }
}