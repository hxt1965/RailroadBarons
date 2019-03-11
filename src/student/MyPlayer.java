package student;

import model.*;

import java.util.*;

/**
 * MyPlayer is the class used to create MyPlayer objects
 * @author srikanthtumati
 */
public class MyPlayer implements Player {
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
    /** graph is a graphical representation of the claimed routes*/
    private HashMap<String, MyNode> graph;
    /** game is a reference to the game the player is in*/
    private RailroadMap game;
    /** north contains all stations at row 0*/
    private ArrayList<Station> north = new ArrayList<>();
    /** north contains all stations at the lowest row*/
    private ArrayList<Station> south = new ArrayList<>();
    /** north contains all stations at column 0*/
    private ArrayList<Station> west = new ArrayList<>();
    /** north contains all stations at the right most column*/
    private ArrayList<Station> east = new ArrayList<>();

    /**
     * MyPlayer is the constructor used for MyPlayer objects
     * @param baron is the baron used to represent the player
     * @param trainpieces is the number of train pieces the player has
     */
    public MyPlayer(Baron baron, int trainpieces, RailroadMap game){
        canClaim=true;
        score=0;
        this.baron=baron;
        this.trainpieces=trainpieces;
        this.graph=new HashMap<>();
        this.game=game;
    }

    /**
     * getBaron returns the baron that represents the player
     * @return the baron that represents the player
     */
    public Baron getBaron(){
        return this.baron;
    }

    /**
     * canClaimRoute is a method that determines if a route can be claimed or not
     * @param route The {@link Route} being tested to determine whether or not
     *              the player is able to claim it.
     * @return true if the route can be claimed and false otherwise
     */
    public boolean canClaimRoute(Route route){
        if (route.getBaron()==Baron.UNCLAIMED && canClaim && canContinuePlaying(route.getLength()) && trainpieces>=route.getLength()){
            return true;
        }
        return false;
    }

    /**
     * getNumberOfPieces returns the number of train pieces the player has
     * @return the number of train pieces the player has
     */
    public int getNumberOfPieces(){
        return this.trainpieces;
    }

    /**
     * claimRoute is the method used to claim the route for the player
     * @param route The {@link Route} to claim.
     *
     * @throws RailroadBaronsException
     */
    public void claimRoute(Route route) throws RailroadBaronsException {
        if (route.getBaron()==Baron.UNCLAIMED && canClaim && canContinuePlaying(route.getLength()) && trainpieces>=route.getLength()){
            routes.add(route);
            route.claim(this.getBaron());
            for (int i = 1; i < cardtypes.length; i ++){
                if (cardtypes[i]>=route.getLength()){
                    cardtypes[i]-=route.getLength();
                    this.score+=route.getPointValue();
                    this.trainpieces-=route.getLength();
                    canClaim=false;
                    determineNeighbors(route);
                    break;
                }
            }
            if (canClaim){
                for (int i = 1; i < cardtypes.length; i++){
                    if (cardtypes[0]+cardtypes[i]>=route.getLength()){
                        cardtypes[0]-=route.getLength()-cardtypes[i];
                        cardtypes[i]=0;
                        this.score+=route.getPointValue();
                        this.trainpieces-=route.getLength();
                        determineNeighbors(route);
                        break;
                    }
                }
                canClaim=false;
            }
            for (PlayerObserver playerObserver : observers){
                playerObserver.playerChanged(this);
            }
        }
        else{
            throw new RailroadBaronsException("Route cannot be claimed!");
        }
    }

    /**
     * removePlayerObserver removes the observer from the arraylist
     * @param observer The {@link PlayerObserver} to remove.
     */
    public void removePlayerObserver(PlayerObserver observer){
        observers.remove(observer);
    }

    /**
     * getClaimedRoutes returns all of the routes the player claimed
     * @return all the routes the player claimed
     */
    public Collection<Route> getClaimedRoutes(){
        return this.routes;
    }

    /**
     * addPlayerObserver adds the observer to the arraylist
     * @param observer The new {@link PlayerObserver}.
     */
    public void addPlayerObserver(PlayerObserver observer){
        observers.add(observer);
    }

    /**
     * canContinuePlaying determines if the player can keep playing the game
     *
     * @param shortestUnclaimedRoute The length of the shortest unclaimed
     *                               {@link Route} in the current game.
     *
     * @return true if the player can still play and false otherwise
     */
    public boolean canContinuePlaying(int shortestUnclaimedRoute){
        if (shortestUnclaimedRoute==0){
            return false;
        }
        for (int i = 1; i < cardtypes.length; i++){
            if (cardtypes[0]+cardtypes[i]>=shortestUnclaimedRoute && trainpieces>=shortestUnclaimedRoute){
                return true;
            }
        }
        this.score+=connected();
        for (PlayerObserver playerObserver : observers){
            playerObserver.playerChanged(this);
        }
        return false;
    }

    /**
     * reset is the method used to reset the player's cards to a new game
     *
     * @param dealt The hand of {@link Card cards} dealt to the player at the
     */
    public void reset(Card... dealt){
        cards = new ArrayList<>(Arrays.asList(dealt));
        trainpieces=45;
        for (Card c : dealt){
            determineCard(c);
        }
        newpair= new MyPair(dealt[dealt.length-2], dealt[dealt.length-1]);
        for (PlayerObserver playerObserver : observers){
            playerObserver.playerChanged(this);
        }
    }

    /**
     * getScore returns the score of the player
     *
     * @return the score for the player
     */
    public int getScore(){

        return this.score;
    }

    /**
     * startTurn is a method used to start the player's turn
     *
     * @param dealt a {@linkplain Pair pair of cards} to the player. Note that
     */
    public void startTurn(Pair dealt){
        this.canClaim=true;
        newpair=dealt;
        determineCard(dealt.getFirstCard());
        determineCard(dealt.getSecondCard());
        cards.add(dealt.getFirstCard());
        cards.add(dealt.getSecondCard());
        for (PlayerObserver playerObserver : observers) {
            playerObserver.playerChanged(this);
        }
        if ((this instanceof MyBot)) {
            ((MyBot) this).claimRoute();

        }
    }

    /**
     * countCardsInHand is the method used to count the number of cards in the player's hand
     *
     * @param card The {@link Card} of interest.
     * @return the number of cards the player has
     */
    public int countCardsInHand(Card card){
        return countCards(card);
    }

    /**
     * getLastTwoCards returns the last two cards the player gets
     * @return the last two cards the player receives
     */
    public Pair getLastTwoCards(){
        return newpair;
    }

    /**
     * determineCard is a method used to determine what the type of the card is and add it to the list
     *
     * @param card the card that needs to be added
     */
    public void determineCard(Card card){
            if(card == Card.WILD){
                cardtypes[0]+=1;
            }
        else if(card == Card.BLUE){
                cardtypes[1]+=1;
            }
        else if(card == Card.GREEN){
                cardtypes[2]+=1;
            }
        else if(card == Card.ORANGE){
                cardtypes[3]+=1;
            }
        else if(card == Card.PINK){
                cardtypes[4]+=1;
        }
        else if(card == Card.RED){
                cardtypes[5]+=1;
            }
        else if(card == Card.WHITE){
                cardtypes[6]+=1;
            }
        else if(card == Card.BLACK){
                cardtypes[7]+=1;
            }
        else if(card == Card.YELLOW){
                cardtypes[8]+=1;
            }
    }

    /**
     * countCards is the method used to count the number of cards for one specific type
     *
     * @param card the number of one type of card
     * @return the number of one specific type of card
     */
    public int countCards(Card card){
        if(card == Card.WILD){
            return cardtypes[0];
        }
        else if(card == Card.BLUE){
            return cardtypes[1];
        }
        else if(card == Card.GREEN){
            return cardtypes[2];
        }
        else if(card == Card.ORANGE){
            return cardtypes[3];
        }
        else if(card == Card.PINK){
            return cardtypes[4];
        }
        else if(card == Card.RED){
            return cardtypes[5];
        }
        else if(card == Card.WHITE){
            return cardtypes[6];
        }
        else if(card == Card.BLACK){
            return cardtypes[7];
        }
        else if(card == Card.YELLOW){
            return cardtypes[8];
        }
        return -1;
    }

    /**
     * String representation of a player
     *
     * @return: "Color" Baron
     */
    public String toString(){
        if (this.baron==Baron.RED){
            return ("RED baron");
        }
        else if(this.baron==Baron.BLUE){
            return ("BLUE baron");
        }
        else if(this.baron==Baron.GREEN){
            return ("GREEN baron");
        }
        else if(this.baron==Baron.YELLOW){
            return ("YELLOW baron");
        }
        return null;
    }

    /**
     * puts all the neighbors of the origin and destination into a graph
     *
     * @param route: the route to be worked with
     */
    public void determineNeighbors(Route route){
        MyNode origin = new MyNode(route.getOrigin());
        MyNode destination = new MyNode(route.getDestination());
        if (!graph.containsKey(origin.getName())){
            origin.addNeighbor(destination);
            graph.put(origin.getName(), origin);
        }
        else{
            graph.get(origin.getName()).addNeighbor(destination);
        }
        if (!graph.containsKey(destination.getName())){
            destination.addNeighbor(origin);
            graph.put(destination.getName(), destination);
        }
        else{
            graph.get(destination.getName()).addNeighbor(origin);
        }
    }

    /**
     * Checks if two stations are connected and returns points accordingly
     *
     * @return: number of extra points
     */
    public int connected(){
        int count = 0;
        for (Route r : routes){
            if (r.getOrigin().getCol()==0){
                if (!west.contains(r.getOrigin())){
                    west.add(r.getOrigin());
                }
            }
            else if (r.getDestination().getCol()==game.getCols()-1){
                if (!east.contains(r.getDestination())){
                    east.add(r.getDestination());
                }
            }
            else if (r.getOrigin().getRow()==0){
                if (!north.contains(r.getOrigin())){
                north.add(r.getOrigin());
                }
            }
            else if (r.getDestination().getRow()==game.getRows()-1){
                if (!south.contains(r.getDestination())){
                    south.add(r.getDestination());
                }
            }
        }
        if (checkconnections(north, south)){
            count+=5*(game.getRows()-1);
        }

     return count;
    }

    /**
     * checks if two stations are connected
     *
     * @param a1: stations at the north and west
     * @param a2: stations at the south and east
     * @return: true/false
     */
    public boolean checkconnections(ArrayList<Station> a1, ArrayList<Station> a2){
        for (Station r : a1){
            for (Station s : a2){
                MyNode start = graph.get(r.getName());
                MyNode finish = graph.get(s.getName());
                Set<String> done = new HashSet<>();
                List<MyNode> crosscountry = new LinkedList<>();
                crosscountry.add(start);
                done.add(start.getName());
                while (!crosscountry.isEmpty()){
                    if (graph.size()==8){
                        for (Map.Entry<String, MyNode> a : graph.entrySet()){
                            String temp = a.getValue().getName();
                            System.out.println(temp);
                            for (MyNode temp1 : a.getValue().getNeighbors()){
                                System.out.println(temp1.getName());
                            }
                            System.out.println();
                            System.out.println();
                        }
                    }
                    MyNode currentstation = crosscountry.remove(0);
                    for (MyNode neighbors : currentstation.getNeighbors()){
                        if (!done.contains(neighbors.getName())){
                            done.add(neighbors.getName());
                            crosscountry.add(graph.get(neighbors.getName()));
                        }
                    }
                }
                if (done.contains(finish.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *  sets the game up
     *
     * @param game: the RailRoadMap instance
     */
    public void setGame(RailroadMap game){
        this.game=game;
    }

    /**
     * returns the instance of the game
     *
     * @return: the RailRoadMap instance (game)
     */
    public RailroadMap getGame(){
        return this.game;
    }
}
