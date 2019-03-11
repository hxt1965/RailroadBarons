package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * MyRailRoadBaronsLonely uses most of the code from MyRailRoadBarons and modifies it to allow bots
 * @author srikanthtumati
 */
public class MyRailRoadBaronsLonely extends MyRailRoadBarons{
    public MyRailRoadBaronsLonely(){
        super();
        super.getPlayers().add(new MyBot(Baron.RED, 45, null, this));
        super.getPlayers().add(new MyBot(Baron.GREEN, 45, null, this));
        super.getPlayers().add(new MyBot(Baron.YELLOW, 45, null, this));
    }
    /**
     * addRailroadBaronsObservers adds an observer to the arraylist
     * @param observer The {@link RailroadBaronsObserver} to add to the
     */
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer){
        super.addRailroadBaronsObserver(observer);
    }

    /**
     * removeRailroadBaronsObservers removes the observer from the arraylist
     * @param observer The {@link RailroadBaronsObserver} to remove.
     */
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer){
        super.removeRailroadBaronsObserver(observer);
    }

    /**
     * startaGameWith starts the game with the designated RailroadMap
     * @param map The {@link RailroadMap} on which the game will be played.
     */
    public void startAGameWith(RailroadMap map){
        super.startAGameWith(map);
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
        super.startAGameWith(map, deck);
    }

    /**
     * getRailroadMap returns the railroadmap used in the game
     * @return the railroadmap used in the game
     */
    public RailroadMap getRailroadMap(){
        return super.getRailroadMap();
    }

    /**
     * numberOfCardsRemaining returns the number of cards left in the deck
     * @return the number of cards left
     */
    public int numberOfCardsRemaining(){
        return super.numberOfCardsRemaining();
    }

    /**
     * canCurrentPlayerClaimRoute determines if the current player can claim the route
     * @param row The row of a {@link Track} in the {@link Route} to check.
     * @param col The column of a {@link Track} in the {@link Route} to check.
     * @return true if the player can claim and false otherwise
     */
    public boolean canCurrentPlayerClaimRoute(int row, int col){
        MyTrack temp = ((MyTrack) super.getRailroadMap().getSpace(row, col));
        return ((ArrayList<Player>)super.getPlayers()).get(super.getCurrentIndex()).canClaimRoute(temp.getRoute());
    }

    /**
     * claimRoute is the method used to claim a route
     * @param row The row of a {@link Track} in the {@link Route} to claim.
     * @param col The column of a {@link Track} in the {@link Route} to claim.
     * @throws RailroadBaronsException if the route cannot be claimed
     */
    public void claimRoute(int row, int col) throws RailroadBaronsException{
        ((ArrayList<Player>)super.getPlayers()).get(super.getCurrentIndex()).claimRoute(((MyTrack)super.getRailroadMap().getSpace(row, col)).getRoute());
        super.getRailroadMap().routeClaimed(((MyTrack)super.getRailroadMap().getSpace(row, col)).getRoute());
    }

    /**
     * endTurn completes the proper actions at the end of the turn
     */
    public void endTurn(){
        for (RailroadBaronsObserver railroadBaronsObserver : observers){
            railroadBaronsObserver.turnEnded(this, ((ArrayList<Player>)super.getPlayers()).get(currentplayer));
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
                players.get(currentplayer).startTurn(new MyPair(deck.drawACard(), deck.drawACard()));
            }
        }
    }

    /**
     * getCurrentPlayer returns the current player for the game
     * @return the current player
     */
    public Player getCurrentPlayer(){
        return super.getCurrentPlayer();
    }

    /**
     * getPlayers returns the players in the game
     * @return the players in the game
     */
    public Collection<Player> getPlayers(){
        return super.getPlayers();
    }

    /**
     * gameIsOver is the method that determines if the game is over
     * @return true if the game is over and false otherwise
     */
    public boolean gameIsOver(){
        return super.gameIsOver();
    }
}
