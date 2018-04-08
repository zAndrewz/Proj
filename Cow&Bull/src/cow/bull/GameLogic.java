/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cow.bull;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author lean
 */
public class GameLogic {
    private int MaxMovesAmount = 0; // amount of a moves
    private int value = 0; //long of the value
    private ArrayList <Integer> NumberToGuess = new ArrayList(); // Number to guess
    private int min = 0, max = 0;
    private String random;
    
    public int[] CountBullCows(int x) throws IncorrectValueException {
        
        MaxMovesAmount--;
        int count[] = new int [2];
        ArrayList <Integer> Player = new ArrayList();
        String xx = x + "";
        try {
        for (int u = 0; u < this.value; u++)
        {
            Player.add(Integer.parseInt(xx.charAt(u) + ""));
        }
        
        }catch( NumberFormatException e)
        {
        }
            
        for (int i = 0; i<this.value; i++)
            for (int j = 0; j<this.value; j++)
        {
            if (NumberToGuess.get(i) == Player.get(j) && i == j) {
                count[0]++; count[1]--; }
            if(NumberToGuess.get(i) == Player.get(j)) {
                count[1]++;}
            
            if(count[0] == this.value) {
                System.out.println("You won!\n");
                MaxMovesAmount = 0;
            }
        }
        
        return count;
    }
    

    public int getMaxMovesAmount() {
        return MaxMovesAmount;
    }

    public void setMaxMovesAmount(int MaxMovesAmount) {
        this.MaxMovesAmount = MaxMovesAmount;
    }

    public int getValue() {
             return value;
    }

    public void setValue(int value) throws IncorrectValueException {
        String min = "1";
        String max = "9";
        for (int i = 0; i < value-1; i++)
        {
            min += "0";
            max += "9";
        }
        try {
        this.min = Integer.parseInt(min); 
        this.max = Integer.parseInt(max);
        this.random = ThreadLocalRandom.current().nextInt(this.min, this.max) + "";
        throw new IncorrectValueException();
        } catch(IncorrectValueException e)
        {        }
        NumberToGuess.clear();
        for(int i = 0; i < random.length(); i++)
            NumberToGuess.add(Integer.parseInt(random.charAt(i) + ""));
        this.value = value;
    }

    public String getNumberToGuess() {
        String gNumG = "";
        for (int i = 0; i < NumberToGuess.size(); i++)
        {
             gNumG += NumberToGuess.get(i);
        }
             return gNumG;
    }

    public class IncorrectValueException extends Throwable{
    IncorrectValueException() {
        super("The value is not correct, try again.");
    }
}
//    public void setNumberToGuess(ArrayList<Integer> NumberToGuess) {
//        this.NumberToGuess = NumberToGuess;
//    }
}
