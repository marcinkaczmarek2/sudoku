package org.example;

import java.util.HashSet;

public abstract class SudokuUnit {
    private SudokuField[] fields;

    public SudokuUnit() {
        this.fields = new SudokuField[SudokuBoard.BOARD_SIZE];
    }

    public boolean verify() {
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
}
