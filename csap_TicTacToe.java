import java.util.*;
public class Abt_TicTacToe{
    public static void main(String[] args){
        System.out.println("Welcome to Tic Tac Toe!!!");

        //im brute forcing this im tired WOMP WOMP
        System.out.println("The board indexing is: \n");

        System.out.println("ROW:   0   1   2 ");
        System.out.println("COL: 0   |   |   ");
        System.out.println("      -----------");
        System.out.println("     1   |   |   ");
        System.out.println("      -----------");
        System.out.println("     2   |   |   ");

        // make & fill board
        String[][] board = new String[3][3];
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[r].length; c++){
                board[r][c] = " ";
            }
        }        

        String turn = "X";
        boolean playing = true;

        while(playing){
            // print board
            System.out.println(" ");
            printBoard(board);

            // make move
            makeMove(board, turn);

            // check win?
            if(checkWin(board, "X")){
                System.out.println(" ");
                System.out.println("X's win!");
                playing = false;
            }
            else if(checkWin(board, "O")){
                System.out.println(" ");
                System.out.println("O's win!");
                playing = false;
            }

            // check tie?
            if(isFull(board) && !checkWin(board, turn)){
                System.out.println(" ");
                System.out.println("Tie game!");
                playing = false;
            }

            // change turn X->O or O->X
            if(turn.equals("X")){
                turn = "O";
            }
            else{
                turn = "X";
            }
        }

        //when game is done -> print board
        printBoard(board);
        System.out.println(" ");
        System.out.println("Thanks for playing!");
    }

    public static void printBoard(String[][] board){
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[r].length; c++){
                System.out.print(board[r][c]);
                if(c < 2){
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if(r < 2){
                System.out.println("---------");
            }
        } 
    }

    public static void makeMove(String[][] board, String turn){
        //use scanner for input
        Scanner scan = new Scanner(System.in);
        System.out.println(" ");
        System.out.println("It is " + turn + "'s turn!");

        System.out.print("Enter the ROW index to play at: ");
        
        int r = scan.nextInt();
        while(r > 2){
            System.out.print("Number is too big, try again: ");
            r = scan.nextInt();
        }

        System.out.print("Enter the COLUMN index to play at: ");
        int c = scan.nextInt();
        while(c > 2){
            System.out.print("Number is too big, try again: ");
            c = scan.nextInt();
        }

        if(board[r][c].equals(" ")){
            board[r][c] = turn;
        }
        else{
            System.out.println("That spot is full, try again:");
            while(!(board[r][c].equals(" "))){
                System.out.print("Enter the ROW index to play at: ");
                r = scan.nextInt();
                while(r > 2){
                    System.out.print("Number is too big, try again: ");
                    r = scan.nextInt();
                }

                System.out.print("Enter the COLUMN index to play at: ");
                c = scan.nextInt();
                while(c > 2){
                    System.out.print("Number is too big, try again: ");
                    c = scan.nextInt();
                }
            }
            board[r][c] = turn;
        }
    }

    public static boolean checkWin(String[][] board, String turn){
        if(turn.equals("X")){
            if(board[0][0].equals("X") && board[0][1].equals("X") && board[0][2].equals("X")){
                return true;
            }
            else if(board[1][0].equals("X") && board[1][1].equals("X") && board[1][2].equals("X")){
                return true;
            }
            else if(board[2][0].equals("X") && board[2][1].equals("X") && board[2][2].equals("X")){
                return true;
            }
            else if(board[0][0].equals("X") && board[1][1].equals("X") && board[2][2].equals("X")){
                return true;
            }
            else if(board[0][2].equals("X") && board[1][1].equals("X") && board[2][0].equals("X")){
                return true;
            }
            else if(board[0][0].equals("X") && board[1][0].equals("X") && board[2][0].equals("X")){
                return true;
            }
            else if(board[0][1].equals("X") && board[1][1].equals("X") && board[2][1].equals("X")){
                return true;
            }

            else if(board[0][2].equals("X") && board[1][2].equals("X") && board[2][2].equals("X")){
                return true;
            }
        }
        if(turn.equals("O")){
            if((board[0][0].equals("O") && board[0][1].equals("O") && board[0][2].equals("O"))){
                return true;
            }
            else if((board[1][0].equals("O") && board[1][1].equals("O") && board[1][2].equals("O"))){
                return true;
            }
            else if((board[2][0].equals("O") && board[2][1].equals("O") && board[2][2].equals("O"))){
                return true;
            }
            else if((board[0][0].equals("O") && board[1][1].equals("O") && board[2][2].equals("O"))){
                return true;
            }
            else if((board[0][2].equals("O") && board[1][1].equals("O") && board[2][0].equals("O"))){
                return true;
            }
            else if((board[0][0].equals("O") && board[1][0].equals("O") && board[2][0].equals("O"))){
                return true;
            }
            else if((board[0][1].equals("O") && board[1][1].equals("O") && board[2][1].equals("O"))){
                return true;
            }
            else if((board[0][2].equals("O") && board[1][2].equals("O") && board[2][2].equals("O"))){
                return true;
            }
        }
        return false;
    }

    public static boolean isFull(String[][] board){
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[r].length; c++){
                if(board[r][c].equals(" ")){
                    return false;
                }
            }
        }  
        return true;
    }
}