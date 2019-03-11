package student;

import model.Card;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyDeckTest {
    private MyDeck myDeck;
    @Before
    public void setUp() throws Exception {
        myDeck = new MyDeck();
    }
    @Test
    public void numberOfCardsRemaining() {
        assertEquals(myDeck.numberOfCardsRemaining(), 180);
    }
}