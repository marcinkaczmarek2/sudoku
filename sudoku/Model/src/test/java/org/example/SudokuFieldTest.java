package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SudokuFieldTest {
    @Test
    public void toStringCorrectnessTrue() {
        SudokuField field = new SudokuField();
        field.setFieldValue(5);
        assertEquals("SudokuField[5]", field.toString());
    }

    @Test
    public void toStringCorrectnessFalse() {
        SudokuField field = new SudokuField();
        field.setFieldValue(1);
        assertNotEquals("SudokuField[5]", field.toString());
    }

    @Test
    public void equalsCorrectnessTrue() {
        SudokuField fieldExpected = new SudokuField();
        SudokuField fieldActual = new SudokuField();
        fieldExpected.setFieldValue(5);
        fieldActual.setFieldValue(5);
        assertTrue(fieldExpected.equals(fieldActual));
    }

    @Test
    public void equalsCorrectnessFalse() {
        SudokuField fieldExpected = new SudokuField();
        SudokuField fieldActual = new SudokuField();
        fieldExpected.setFieldValue(1);
        fieldActual.setFieldValue(5);
        assertFalse(fieldExpected.equals(fieldActual));
    }

    @Test
    public void equalsSameObject() {
        SudokuField field = new SudokuField();
        field.setFieldValue(5);
        assertTrue(field.equals(field));
    }

    @Test
    public void equalsNullObject() {
        SudokuField field = new SudokuField();
        field.setFieldValue(5);
        assertFalse(field.equals(null));
    }

    @Test
    public void equalsDifferentClassObject() {
        SudokuField field = new SudokuField();
        Integer testInt = 5;
        field.setFieldValue(5);
        assertFalse(field.equals(testInt));
    }

    @Test
    public void hashCodeCorrectnessTrue() {
        SudokuField field = new SudokuField();
        SudokuField fieldCopy = new SudokuField();
        field.setFieldValue(5);
        fieldCopy.setFieldValue(5);
        assertEquals(field.hashCode(), fieldCopy.hashCode());
        assertTrue(field.equals(fieldCopy));
    }

    @Test
    public void hashCodeCorrectnessFalse() {
        SudokuField field = new SudokuField();
        SudokuField fieldCopy = new SudokuField();
        field.setFieldValue(1);
        fieldCopy.setFieldValue(5);
        assertNotEquals(field.hashCode(), fieldCopy.hashCode());
        assertFalse(field.equals(fieldCopy));
    }
}
