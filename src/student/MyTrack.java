package student;

import model.Baron;
import model.Orientation;

/**
 * MyTrack is the class that is used for spaces that are tracks
 * @author srikanthtumati
 */
public class MyTrack extends MySpace implements model.Track{
    /** orientation is the orientation of the track*/
    private Orientation orientation;
    /** owner is the owner of the track*/
    private Baron owner;
    /** route is the Route that the track is contained within*/
    private MyRoute route;

    /**
     * MyTrack is the constructor for track objects
     * @param row the row where the track is located
     * @param col the column where the track is located
     * @param orientation the orientation of the track
     * @param owner the owner of the track
     * @param route the route in which the track is located within
     */
    public MyTrack(int row, int col, Orientation orientation, Baron owner, MyRoute route){
        super(row, col);
        this.orientation=orientation;
        this.owner=owner;
        this.route=route;
    }

    /**
     * getBaron is the method that returns the owner of the track
     * @return the owner of the track
     */
    public Baron getBaron(){
        if (route.getBaron()==Baron.UNCLAIMED) {
            return this.owner;
        }
        else{
            this.owner=route.getBaron();
            return route.getBaron();
        }
    }

    /**
     * getOrientation returns the orientation of the track
     * @return the orientation of the track
     */
    public Orientation getOrientation(){
        return this.orientation;
    }

    /**
     * getRoute returns the route in which the track is located within
     * @return the route in which the track is located within
     */
    public MyRoute getRoute(){
        return this.route;
    }

}
