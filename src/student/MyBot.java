package student;

import model.Baron;
import model.RailroadMap;
import model.Route;

/**
 * MyBot is the class used to create Bots
 * and enable the lonely version of the game
 * to function
 *
 * @author srikanthtumati
 */
public class MyBot extends MyPlayer{
    /** game is a reference to the railroadmap used in the game*/
    private RailroadMap game;
    /** lonely is the reference to the railroadbaronslonely object to start/end turns*/
    private MyRailRoadBaronsLonely lonely;

    /**
     * MyBot is the constructor used to create Bots in the game
     * @param baron the baron associated with the bot
     * @param trainpieces the number of train pieces
     * @param game the map reference
     * @param lonely the lonely reference to the barons class
     */
    public MyBot(Baron baron, int trainpieces, RailroadMap game, MyRailRoadBaronsLonely lonely){
        super(baron, trainpieces, game);
        this.lonely=lonely;
    }

    /**
     * claimRoute allows the Bot to claim a route and end its turn
     */
    public void claimRoute(){
        int longest = 0;
        Route temp = null;
        // checks the longest route the bot can claim
        for (Route r : super.getGame().getRoutes()){
            if (canClaimRoute(r) && r.getLength()>longest){
                longest=r.getLength();
                temp=r;
            }
        }
        //if valid, route is claimed
        if (temp!=null) {
            try {
                lonely.claimRoute(temp.getTracks().get(0).getRow(), temp.getTracks().get(0).getCol());
            }
            catch(Exception ex){System.err.println("ERROR SOMETHIGN WRONG");}
            lonely.endTurn();
        }

    }






}
