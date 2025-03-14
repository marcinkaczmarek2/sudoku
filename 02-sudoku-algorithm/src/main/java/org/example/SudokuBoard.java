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
                int counter=0;
                do{
                    board[row][column]=getRandomNumber();
                    counter++;
                    if(counter>20){
                        for (int k=0;k<9;k++){
                            board[row][k]=0;
                        }
                        row--;
                        break;
                    }
                } while(!isNumberValid(row, column));
            }
        }
    }

    public static boolean isBoxValid(int row, int column){
        if (row<3){
            if (column<3){ //box1
                for (int i=0;i<3;i++){
                    for (int j=0;j<3;j++){
                        if (i==row && j==column){
                            continue;
                        }
                        else{
                            if(board[row][column]==board[i][j]){
                                return false;
                            }
                        }
                    }
                }
            }
            else if (column<6){ //box2
                for (int i=0;i<3;i++){
                    for (int j=3;j<6;j++){
                        if (i==row && j==column){
                            continue;
                        }
                        else{
                            if(board[row][column]==board[i][j]){
                                return false;
                            }
                        }
                    }
                }
            }
            else{ //box3
                for (int i=0;i<3;i++){
                    for (int j=6;j<9;j++){
                        if (i==row && j==column){
                            continue;
                        }
                        else{
                            if(board[row][column]==board[i][j]){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        if (row>2 && row<6){
            if (column<3){ //box4
                for (int i=3;i<6;i++){
                    for (int j=0;j<3;j++){
                        if (i==row && j==column){
                            continue;
                        }
                        else{
                            if(board[row][column]==board[i][j]){
                                return false;
                            }
                        }
                    }
                }
            }
            else if (column<6){ //box5
                for (int i=3;i<6;i++){
                    for (int j=3;j<6;j++){
                        if (i==row && j==column){
                            continue;
                        }
                        else{
                            if(board[row][column]==board[i][j]){
                                return false;
                            }
                        }
                    }
                }
            }
            else{ //box6
                for (int i=3;i<6;i++){
                    for (int j=6;j<9;j++){
                        if (i==row && j==column){
                            continue;
                        }
                        else{
                            if(board[row][column]==board[i][j]){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        if(row>5 && row<9){
            if (column<3){ //box7
                for (int i=6;i<9;i++){
                    for (int j=0;j<3;j++){
                        if (i==row && j==column){
                            continue;
                        }
                        else{
                            if(board[row][column]==board[i][j]){
                                return false;
                            }
                        }
                    }
                }
            }
            else if (column<6){ //box8
                for (int i=6;i<9;i++){
                    for (int j=3;j<6;j++){
                        if (i==row && j==column){
                            continue;
                        }
                        else{
                            if(board[row][column]==board[i][j]){
                                return false;
                            }
                        }
                    }
                }
            }
            else{ //box9
                for (int i=6;i<9;i++){
                    for (int j=6;j<9;j++){
                        if (i==row && j==column){
                            continue;
                        }
                        else{
                            if(board[row][column]==board[i][j]){
                                return false;
                            }
                        }
                    }
                }
            }
        }
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
        for (int i=0;i<9;i++){
            if (row==i){
                continue;
            }
            else{
                if(board[i][column]==board[row][column]){
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isNumberValid(int row, int column){
        if (isRowValid(row, column)  && isColumnValid(row, column) && isBoxValid(row, column)){
            return true;
        }
        return false;
    }
}
