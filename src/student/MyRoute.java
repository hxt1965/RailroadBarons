package student;

import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * MyRoute is the class used to create routes for the game
 * @author srikanthtumati
 */
public class MyRoute implements model.Route {
    /** tracks is the list of tracks that is contained within the routes*/
    private List<Track> tracks = new ArrayList<>();
    /** owner is the owner of the route*/
    private Baron owner;
    /** orientation is the orientation of the route*/
    private Orientation orientation;
    /** origin is the origin station of the route*/
    private MyStation origin;
    /** destination is the destination station of the route*/
    private MyStation destination;

    /**
     * MyRoute is the constructor class for Route objects
     * @param owner is the owner of the Route
     * @param origin the origin station for the route
     * @param destination the destination station for the route
     */
    public MyRoute(Baron owner, MyStation origin, MyStation destination){
        if (origin.getCol()==destination.getCol()){
            if (origin.getRow()<destination.getRow()) {
                for (int i = origin.getRow() + 1; i < destination.getRow(); i++) {
                    tracks.add(new MyTrack(i, origin.getCol(), Orientation.VERTICAL, Baron.UNCLAIMED, this));
                }
            }
            else{
                for (int i = destination.getRow() + 1; i < origin.getRow(); i++) {
                    tracks.add(new MyTrack(i, origin.getCol(), Orientation.VERTICAL, Baron.UNCLAIMED, this));
                }
            }
        }
        else{
            if (origin.getCol()<destination.getCol()){
                for (int i = origin.getCol()+1; i < destination.getCol(); i++){
                    tracks.add(new MyTrack(origin.getRow(), i, Orientation.HORIZONTAL, Baron.UNCLAIMED, this));
                }
            }
            else{
                for (int i = destination.getCol()+1; i < origin.getCol(); i++){
                    tracks.add(new MyTrack(origin.getRow(), i, Orientation.HORIZONTAL, Baron.UNCLAIMED, this));
                }
            }
        }
        this.owner=owner;
        this.origin=origin;
        this.destination=destination;
        if (this.origin.getCol()==this.destination.getCol()){
            this.orientation = Orientation.VERTICAL;
        }
        else if (this.origin.getRow()==this.destination.getRow()){
            this.orientation = Orientation.HORIZONTAL;
        }
        else{
            System.out.println("Error in MyRoute.java: Orientation");
            return;
        }
        if (this.origin==this.destination){
            System.out.println("Error in MyRoute.java: Origin/Destination Match");
            return;
        }
        if (!(this.destination.getRow()-this.origin.getRow()>0) && !(this.destination.getCol()-this.origin.getCol()>0)){
            System.out.println("Error in MyRoute.java Origin not North/West of Destination!");
            return;
        }
    }

    /**
     * getBaron returns the owner of the route
     * @return the owner of the route
     */
    public Baron getBaron(){
        return this.owner;
    }

    /**
     * getOrigin returns the origin station for the route
     * @return the origin station
     */
    public MyStation getOrigin(){
        return this.origin;
    }

    /**
     * claim is the method for a player/Baron to claim the route
     * @param claimant The {@link Baron} attempting to claim the route. Must
     *                 not be null or {@link Baron#UNCLAIMED}.
     * @return true if possible and false otherwise
     */
    public boolean claim(Baron claimant){
        if (claimant!=null && claimant!=Baron.UNCLAIMED && this.owner==Baron.UNCLAIMED){
            this.owner=claimant;
            return true;
        }
        return false;
    }

    /**
     * Orientation returns the orientation of the route
     * @return the orientation of the route
     */
    public Orientation getOrientation(){
        return this.orientation;
    }

    /**
     * includesCoordinate determines if a coordinate is within the route
     * @param space The {@link Space} that may be in this route.
     *
     * @return true if the coordinate is within the route and false otherwise
     */
    public boolean includesCoordinate(Space space){
        for (Track t: tracks){
            if (t.getRow()==space.getRow() && t.getCol()==space.getCol()){
                return true;
            }
        }
        return false;
    }

    /**
     * getLength returns the length of the route
     * @return the length of the route
     */
    public int getLength(){
        return tracks.size();
    }

    /**
     * getTracks is a method that returns all of the tracks within the route
     * @return the tracks in the route
     */
    public List<Track> getTracks(){
        return tracks;
    }

    /**
     * getDestination returns the destination station for the route
     * @return the destination station for the route
     */
    public model.Station getDestination(){
        return this.destination;
    }

    /**
     * getPointValue returns the point value for the route
     * @return the point value
     */
    public int getPointValue(){
        if (getLength()==1){
            return 1;
        }
        else if (getLength()==2){
            return 2;
        }
        else if (getLength()==3){
            return 4;
        }
        else if (getLength()==4){
            return 7;
        }
        else if (getLength()==5){
            return 10;
        }
        else if (getLength()==6){
            return 15;
        }
        else{
            return 5 * (getLength() - 3);
        }

    }
}
