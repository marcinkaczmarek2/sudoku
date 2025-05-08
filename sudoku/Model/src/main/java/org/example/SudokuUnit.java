package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashSet;

public abstract class SudokuUnit implements Cloneable {
    protected SudokuField[] fields;

    public SudokuUnit() {
        this.fields = new SudokuField[SudokuBoard.BOARD_SIZE];
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            fields[i] = new SudokuField();
        }
    }

    protected boolean verify() {
        HashSet<Integer> checkUnit = new HashSet<>();
        HashSet<Integer> correctUnit = new HashSet<>();
        for (SudokuField field : fields) {
            checkUnit.add(field.getFieldValue());
        }
        for (int i = 1; i < 10; i++) {
            correctUnit.add(i);
        }
        return checkUnit.equals(correctUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SudokuUnit that = (SudokuUnit) obj;
        return new EqualsBuilder()
                .append(fields, that.fields)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(fields)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("fields", fields)
                .toString();
    }

    @Override
    public SudokuUnit clone() {
        try {
            SudokuUnit cloned = (SudokuUnit) super.clone();

            // Create a new array and deep clone each field
            cloned.fields = new SudokuField[SudokuBoard.BOARD_SIZE];
            for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
                cloned.fields[i] = (SudokuField) this.fields[i].clone();
            }

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
