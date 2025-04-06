package org.example;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

public class SudokuField {
    private int fieldValue;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public int getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(int value) {
        int oldValue = fieldValue;
        this.fieldValue = value;
        support.firePropertyChange("fieldValue", oldValue, value);
    }

    public void addFieldValueListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void removeFieldValueListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) obj;
        return this.fieldValue == that.fieldValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldValue);
    }

    @Override
    public String toString() {
        return "SudokuField[" + fieldValue + "]";
    }
}
