package org.example;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
}
