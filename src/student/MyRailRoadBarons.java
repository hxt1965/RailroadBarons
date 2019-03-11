package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * MyRailRoadBarons is the class that contains the logic used to play the game
 * @author srikanthtumati
 */
public class MyRailRoadBarons implements RailroadBarons {
    /** observers is a list of observers for railroadbarons*/
    List<RailroadBaronsObserver> observers = new ArrayList<>();
    /** game is the railroadmap that the game is run upon*/
    public RailroadMap game;
    /** players is the array list of players playing the game*/
    public ArrayList<Player> players;
    /** deck is the deck object used in the game*/
    public Deck deck;
    /** currentplayer is the current player in the game*/
    public int currentplayer;

    /**
     * MyRailRoadBarons is the constructor for RailRoadBarons
     */
    public MyRailRoadBarons(){
        this.players = new ArrayList<>();
        players.add(new MyPlayer(Baron.BLUE, 45, null));
        if (!(this instanceof MyRailRoadBaronsLonely)) {
            players.add(new MyPlayer(Baron.RED, 45, null));
            players.add(new MyPlayer(Baron.GREEN, 45, null));
            players.add(new MyPlayer(Baron.YELLOW, 45, null));
            Collections.shuffle(players);
            currentplayer = 0;
        }
    }

    /**
     * addRailroadBaronsObservers adds an observer to the arraylist
     * @param observer The {@link RailroadBaronsObserver} to add to the
     */
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer){
        observers.add(observer);
    }

    /**
     * removeRailroadBaronsObservers removes the observer from the arraylist
     * @param observer The {@link RailroadBaronsObserver} to remove.
     */
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer){
        observers.remove(observer);
    }

    /**
     * startaGameWith starts the game with the designated RailroadMap
     * @param map The {@link RailroadMap} on which the game will be played.
     */
    public void startAGameWith(RailroadMap map){
            this.deck = new MyDeck();
            this.game=map;
        for (Player p : players){
            ((MyPlayer)p).setGame(this.game);
            p.reset(deck.drawACard(), deck.drawACard(), deck.drawACard(), deck.drawACard());
        }
        for (RailroadBaronsObserver railroadBaronsObserver : observers){
            railroadBaronsObserver.turnStarted(this, players.get(currentplayer));
        }
    }

    /**
     * startAGameWith starts the game with the designated railroadmap and deck
     * @param map The {@link RailroadMap} on which the game will be played.
     * @param deck The {@link Deck} of cards used to play the game. This may
     *             be ANY implementation of the {@link Deck} interface,
     *             meaning that a valid implementation of the
     *             {@link RailroadBarons} interface should use only the
     */
    public void startAGameWith(RailroadMap map, Deck deck){
        this.deck=deck;
        this.game = map;
        Collections.shuffle(players);
        for (RailroadBaronsObserver railroadBaronsObserver : observers){
            railroadBaronsObserver.turnStarted(this, players.get(currentplayer));
        }
    }

    /**
     * getRailroadMap returns the railroadmap used in the game
     * @return the railroadmap used in the game
     */
    public RailroadMap getRailroadMap(){
        return this.game;
    }

    /**
     * numberOfCardsRemaining returns the number of cards left in the deck
     * @return the number of cards left
     */
    public int numberOfCardsRemaining(){
        return this.deck.numberOfCardsRemaining();
    }

    /**
     * canCurrentPlayerClaimRoute determines if the current player can claim the route
     * @param row The row of a {@link Track} in the {@link Route} to check.
     * @param col The column of a {@link Track} in the {@link Route} to check.
     * @return true if the player can claim and false otherwise
     */
    public boolean canCurrentPlayerClaimRoute(int row, int col){
        MyTrack temp = ((MyTrack) this.game.getSpace(row, col));
        return players.get(currentplayer).canClaimRoute(temp.getRoute());
    }

    /**
     * claimRoute is the method used to claim a route
     * @param row The row of a {@link Track} in the {@link Route} to claim.
     * @param col The column of a {@link Track} in the {@link Route} to claim.
     * @throws RailroadBaronsException if the route cannot be claimed
     */
    public void claimRoute(int row, int col) throws RailroadBaronsException{
        players.get(currentplayer).claimRoute(((MyTrack)this.game.getSpace(row, col)).getRoute());
        this.game.routeClaimed(((MyTrack)this.game.getSpace(row, col)).getRoute());
    }

    /**
     * endTurn completes the proper actions at the end of the turn
     */
    public void endTurn(){
        for (RailroadBaronsObserver railroadBaronsObserver : observers){
            railroadBaronsObserver.turnEnded(this, players.get(currentplayer));
        }
        if (currentplayer==3){
            currentplayer=0;
            if (!gameIsOver()){
                for (RailroadBaronsObserver railroadBaronsObserver : observers){
                    railroadBaronsObserver.turnStarted(this, players.get(currentplayer));
                }
                players.get(currentplayer).startTurn(new MyPair(deck.drawACard(), deck.drawACard()));
            }
        }
        else{
            if (!gameIsOver()){
                currentplayer+=1;
                for (RailroadBaronsObserver railroadBaronsObserver : observers){
                    railroadBaronsObserver.turnStarted(this, players.get(currentplayer));
                }
                players.get(currentplayer).startTurn(new MyPair(deck.drawACard(), deck.drawACard()));
            }
        }
    }

    /**
     * getCurrentPlayer returns the current player for the game
     * @return the current player
     */
    public Player getCurrentPlayer(){
        return players.get(currentplayer);
    }
    /**
     * getPlayers returns the players in the game
     * @return the players in the game
     */
    public Collection<Player> getPlayers(){
        return this.players;
    }

    public int getCurrentIndex(){
        return currentplayer;
    }

    /**
     * gameIsOver is the method that determines if the game is over
     * @return true if the game is over and false otherwise
     */
    public boolean gameIsOver(){
        for (Player p : players){
            if (((p.canContinuePlaying(this.getRailroadMap().getLengthOfShortestUnclaimedRoute())) || deck.numberOfCardsRemaining()<20) || p.getNumberOfPieces()>=this.getRailroadMap().getLengthOfShortestUnclaimedRoute()){
                if (this.getRailroadMap().getLengthOfShortestUnclaimedRoute()==0){
                    int highest = -1;
                    Player temp = players.get(0);
                    for (Player z : players){
                        if (z.getScore()>highest){
                            highest=z.getScore();
                            temp=z;
                        }
                    }
                    for (RailroadBaronsObserver railroadBaronsObserver : observers){
                        railroadBaronsObserver.gameOver(this, temp);
                    }
                    return true;
                }
                return false;
                }
        }
        /**
         * for (RailroadBaronsObserver railroadBaronsObserver : observers){
         *             railroadBaronsObserver.gameOver(this, players.get(currentplayer));
         *         }
         *         return true;
         */
        return false;
    }
}
