package student;

import model.Card;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyPairTest {
    private MyPair a;
    private MyPair b;
    @Before
    public void setUp(){
        a = new MyPair(Card.BLACK, Card.GREEN);
        b = new MyPair(Card.NONE, Card.WILD);
    }

    @Test
    public void getFirstCard() {
        assertEquals(a.getFirstCard(), Card.BLACK);
        assertEquals(b.getFirstCard(), Card.NONE);
    }
    @Test
    public void getSecondCard() {
        assertEquals(a.getSecondCard(), Card.GREEN);
        assertEquals(b.getSecondCard(), Card.WILD);
    }
}