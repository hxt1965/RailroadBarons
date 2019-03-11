package student;

import model.Card;
import model.Deck;

import java.util.*;

/**
 * MyDeck is the class that creates the deck for the game
 * @author harshtagotra
 */
public class MyDeck implements Deck
{
    /** deck is an arraylist that symbolizes a deck of cards*/
    private ArrayList<Card> deck;
    /** size is the size of the deck */
    private int size;
    /** cnt is the count of the card from the deck*/
    private int cnt;

    /**
     * MyDeck is the constructor for the MyDeck class and creates the deck that is shuffled
     */
    public MyDeck()
    {
        this.deck =  makeADeck();
        size = this.deck.size();
        cnt = 0;
    }

    /**
     * reset is the method that resets the deck and properly creates it
     */
    public void reset()
    {
        this.deck = makeADeck();
        size = deck.size();
        cnt = 0;
    }

    /**
     * drawACard is the method used to draw the card that is "on top" of the deck
     * @return the card on top
     */
    public Card drawACard()
    {
        cnt+=1;
        if (cnt>=deck.size()){
            return Card.NONE;
        }
        return deck.get(cnt);
    }

    /**
     * numberOfCardsRemaining returns the number of cards left in the deck
     * @return the number of cards left
     */
    public int numberOfCardsRemaining()
    {
        return size-cnt;
    }

    /**
     * makeADeck is the method that creates the deck and shuffles it
     * @return the shuffled deck
     */
    private ArrayList<Card> makeADeck()
    {
        ArrayList<Card> tempDeck = new ArrayList<>();

        for(int i=0; i<10*20; i++)
            if(i<20)
                continue;
            else if(i<40)
                tempDeck.add(Card.BLACK);
            else if(i<60)
                tempDeck.add(Card.BLUE);
            else if(i<80)
                tempDeck.add(Card.GREEN);
            else if(i<100)
                tempDeck.add(Card.ORANGE);
            else if(i<120)
                tempDeck.add(Card.PINK);
            else if(i<140)
                tempDeck.add(Card.RED);
            else if(i<160)
                tempDeck.add(Card.WHITE);
            else if(i<180)
                tempDeck.add(Card.WILD);
            else if(i<200)
                tempDeck.add(Card.YELLOW);

        Collections.shuffle(tempDeck);

        return tempDeck;
    }
}
