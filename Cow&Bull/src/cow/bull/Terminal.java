/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cow.bull;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lean
 */
public class Terminal  implements Runnable{
    GameLogic GL = new GameLogic();
    Scanner sc = new Scanner(System.in);
    
    Terminal() {
        new Thread(this).start();
    }
    
    
    @Override
    public void run() {
        
        System.out.println("Welcome to the Cow and Bull game \n");
        System.out.println("---Menu---" + "\n 1. New Game  \n 2. Rules \n 3. Exit" );
        boolean exit = false;
        
        do
        {
            try {
                String sw = sc.nextLine();
                switch(sw)
                {
                    case "1": StartGame();
                    break;
                    case "2": Rules();
                    break;
                    case "3": exit = true;
                    break;
                    default: System.out.println("Choose 1 or 2!");
                    break;
                }
                System.out.println("For show a game rules write '2', for exit write '3', for start a game write '1'");
            } catch (GameLogic.IncorrectValueException ex) {
                Logger.getLogger(Terminal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(!exit);
    }
    
    public void StartGame() throws GameLogic.IncorrectValueException {
        int val;
        int res[] = new int[2];
        boolean ch = false;
        do 
        {
        System.out.println("Write a max long number for a game (from 0 to 9): ");
        try {
        val = sc.nextInt();
        }catch(InputMismatchException e) {
            System.out.println("Incorrect! Length of your input will become a number");
            val = sc.nextLine().length();
            if(val > 9 || val < 0)
            val = 9;}
        if (val < 9 && val > 0)
            ch = true;
        } while(!ch);
       
        GL.setValue(val);
        
        try {
        System.out.println("Write a max amount of moves: ");
        GL.setMaxMovesAmount(sc.nextInt());
        }catch(InputMismatchException e) {
        System.out.println("You were should write a number");
        GL.setMaxMovesAmount(1);
        }
         
        String guess = "";
        System.out.println("---Game started---");
        do
        {
             System.out.println("You have " + GL.getMaxMovesAmount() + " moves left");
             System.out.println("Make a guess! (" + GL.getValue() + ") numbers ");
             // + GL.getNumberToGuess()
        do{
          try {
           guess = sc.nextLine();
           
           if((guess.length() < GL.getValue() && guess.length() > 0) || guess.length() > GL.getValue())
           {
               System.out.println("You need a number with a length " + GL.getValue() + 
                        " but you have " + guess.length());
           }
           }catch(NumberFormatException e)
           { }
           try {
           if(guess.length() == GL.getValue()) {
            res = GL.CountBullCows(Integer.parseInt(guess)); 
            
            if(GL.getMaxMovesAmount() == 0 && res[0] != GL.getValue())
                System.out.println("You lose. The number was " + GL.getNumberToGuess());
            else if (res[0] != GL.getValue())
            System.out.println("You have " + res[0] + " bulls and " + res[1] + " cows");
            
            
           }
           }catch( NumberFormatException e)
           { }
               
        } while(guess.length() != GL.getValue());
        
        }while(GL.getMaxMovesAmount() != 0);
        
    }
       
    public void Rules()
    {
        System.out.println(" The player should guess a digit secret number. \n "
                + "If the matching digits are in their right positions, they are \"bulls\", \n"
                + " if in different positions, they are \"cows\". Example:\n" +
             " Secret number: 4271\n" +
            " Opponent's try: 1234\n" +
            " Answer: 1 bull and 2 cows. (The bull is \"2\", the cows are \"4\" and \"1\".)");
    }
    
    public static void main(String[] args) {
        new Terminal();
    }
}