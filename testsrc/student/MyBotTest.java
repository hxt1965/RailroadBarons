package student;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyBotTest {
    /** observers is the arraylist of observers*/
    List<PlayerObserver> observers = new ArrayList<>();
    /** routes is the arraylist of routes that the player has*/
    List<Route> routes = new ArrayList<>();
    /** cards is the list of cards that the player has*/
    List<Card> cards;
    /** cardtypes is used to remember how many of each card the player has*/
    int[] cardtypes = new int[9];;
    /** score is used for the player's score*/
    private int score;
    /** baron is the baron in which the player represents*/
    private Baron baron;
    /** canClaim determines if the player can claim*/
    private boolean canClaim;
    /** trainpieces counts how many train pieces the player has*/
    private int trainpieces;
    /** newpair is the newest pair of cards the player gets*/
    Pair newpair;
    private MyPlayer myPlayer;
    @Before
    public void setUp() throws Exception {
        //myBot = new MyPlayer(Baron.BLUE, 34, );
    }

    @Test
    public void getBaron() {
        assertEquals(myPlayer.getBaron(), Baron.BLUE);
    }

    @Test
    public void getNumberOfPieces() {
        assertEquals(myPlayer.getNumberOfPieces(), 34);
    }

    @Test
    public void getClaimedRoutes() {
        assertEquals(myPlayer.getClaimedRoutes(), routes);
    }

    @Test
    public void canContinuePlaying() {
        assertEquals(myPlayer.canContinuePlaying(0), false);
    }

    @Test
    public void getScore() {
        assertEquals(myPlayer.getScore(), score);
    }
    @Test
    public void countCards() {
        assertEquals(myPlayer.countCards(Card.BLACK),0);
    }
    @Test
    public void claimRoute() {
    }
}