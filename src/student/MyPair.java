package student;

import model.*;

/**
 * MyPair is the class used to create a pair of cards for the game
 * @author sarahpeterson srikanthtumati
 */
public class MyPair implements Pair{
    /** c1 is the first card in the pair*/
    private Card c1;
    /** c2 is the second card in the pair*/
    private Card c2;

    /**
     * MyPair is the constructor for a pair of cards
     * @param c1 the first card in the pair
     * @param c2 the second card in the pair
     */
    public MyPair(Card c1, Card c2){
        this.c1=c1;
        this.c2=c2;
    }

    /**
     * getFirstCard returns the first card in the pair
     * @return the first card
     */
    public Card getFirstCard(){
        return this.c1;
    }

    /**
     * getSecondCard returns the second card in the pair
     * @return the second card in the pair
     */
    public Card getSecondCard(){
        return this.c2;
    }
}
