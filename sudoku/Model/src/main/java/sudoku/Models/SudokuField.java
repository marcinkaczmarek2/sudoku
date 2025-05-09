package sudoku.Models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;

public class SudokuField implements Serializable, Comparable<SudokuField>, Cloneable {
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

    @Override
    public int compareTo(SudokuField other) {
        if (other == null) {
            throw new NullPointerException("Argument to compareTo is null");
        }
        return Integer.compare(this.fieldValue, other.fieldValue);
    }

    protected SudokuField doClone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }

    @Override
    protected SudokuField clone() {
        try {
            return doClone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
