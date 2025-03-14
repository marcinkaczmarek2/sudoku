package org.example;

import java.util.Random;

public class SudokuBoard {
    public static int[][] board = new int[9][9];

    private static final Random random = new Random();

    public static int getRandomNumber() {
        return random.nextInt(9) + 1;
    }

    public static void displayBoard(){
        System.out.print("\n_ _ _ _ _ _ _ _ _ _ _ _\n");
        for (int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print(board[i][j] + " ");
                if (j==2 || j==5 || j==8) System.out.print("| ");
            }

            if (i==2 || i==5 || i==8) System.out.print("\n_ _ _ _ _ _ _ _ _ _ _ _");
            System.out.println();
        }
    }
    public static void fillBoard(){


        for (int row=0;row<9;row++){
            for(int column=0;column<9;column++){
                do{
                    board[row][column]=getRandomNumber();
                } while(!isNumberValid(row, column));
            }
        }
    }

    public static boolean isBoxValid(int row, int column){
        return true;
    }
    public static boolean isRowValid(int row, int column){
        for (int i=0;i<9;i++){
            if (column==i){
                continue;
            }
            else{
                if(board[row][i]==board[row][column]){
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isColumnValid(int row, int column){
//        for (int i=0;i<9;i++){
//            if (row==i){
//                continue;
//            }
//            else{
//                if(board[i][column]==board[row][column]){
//                    return false;
//                }
//            }
//        }
//        return true;
















        for (int i=0;i<9;i++){
            if ()
        }
    }
    public static boolean isNumberValid(int row, int column){
        if (isBoxValid(row, column) && isRowValid(row, column) && isColumnValid(row, column)){
            return true;
        }
        return false;
    }
}
