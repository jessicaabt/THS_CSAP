import java.io.*;
import java.util.*;

public class csap_Hangman{
    static int attemptsRemaining = 10; //if guess is incorrect (indexOf = -1)
    public static void main(String[] args) throws Exception{
        String playerGuess = ""; //getValidGuess method
        String displayWord = ""; //getDisplayWord(String, String);
        boolean playing = false; //playing or now
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to Jessica's Hangman!!\n");
        Thread.sleep(1500);
        System.out.println("Please input ONE LETTER at a time for your guess!\n");
        playing = true;
        
        String hiddenWord = getRandomWord();
        
        for(int i = 0; i < hiddenWord.length(); i++){
            displayWord += "_";
        }
        System.out.println("Mystery Word: " + displayWord + "\n");
        
        while(playing){
            Thread.sleep(1500);
            System.out.println("Input your guess: ");
            playerGuess = scan.nextLine();
            System.out.println("");
            displayWord = getValidGuess(hiddenWord, playerGuess, displayWord);
            System.out.println(displayWord + "\n");
            
            if(attemptsRemaining == 0){
                System.out.println("You Lose..");
                System.out.println("The word was: " + hiddenWord);
                playing = false;
            }
            
            if(displayWord.equals(hiddenWord)){
                System.out.println("You Win!!");
                playing = false;
            }
        }
    }

    public static String getValidGuess(String hidden, String guess, String display){
        if(guess.length() != 1){
            System.out.println("no.. i said a LETTER.. goober!");
            return display;
        }
        if(!Character.isAlphabetic(guess.charAt(0))){
            System.out.println("no.. i said a LETTER.. goober!");
            return display;
        }
        
        for(int i = 0; i < display.length(); i++){
            if(hidden.indexOf(guess) > 0){
                for(int x = 0; x < display.length(); x++){
                    if(hidden.substring(x,x+1).equals(guess)){
                        display = display.substring(0, x) + guess + display.substring(x+1);
                    }
                }
            }
            else if(i == display.length() - 1){
                System.out.println("Not in the word! Guess again.");
            }
        }
        attemptsRemaining--;
        return display;
    }

    public static String getRandomWord(){
        File wordListFile = new File("popular.txt");
        int numOfLineInFile = countFileLines(wordListFile);
        int randomLineNumber = new Random().nextInt(numOfLineInFile);
        try{
            Scanner fileScane = new Scanner(wordListFile);
            for(int i = 0; i < randomLineNumber; i++){
                fileScane.nextLine();
            }
            return fileScane.next();
        }
        catch(FileNotFoundException e){
            System.out.println("Word List File Not Available");
            e.printStackTrace();
        }
        return null;
    }

    public static int countFileLines(File file){
        int count = 0;
        try{
            Scanner fileScane = new Scanner(file);
            while(fileScane.hasNextLine()){
                count++;
                fileScane.nextLine();
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Word List File Not Available");
            e.printStackTrace();
        }
        return count;
    }

}
