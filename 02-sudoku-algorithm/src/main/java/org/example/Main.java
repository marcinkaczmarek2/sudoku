package org.example;

public class Main {
    public static void main(String[] args) {

        SudokuBoard board1 = new SudokuBoard();
        board1.fillBoard();
        board1.displayBoard();

        System.out.println("\n________________________\n");

        SudokuBoard board2 = new SudokuBoard();
        board2.fillBoard();
        board2.displayBoard();
    }
}