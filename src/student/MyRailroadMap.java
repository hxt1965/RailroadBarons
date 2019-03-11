package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * MyRailroadMap is the class used to gather all necessary data values for the map
 * @author srikanthtumati
 */
public class MyRailroadMap implements model.RailroadMap{
    /** observers is the list of observers for the myrailroadmap*/
    List<RailroadMapObserver> observers = new ArrayList<>();
    /** spaces is the arraylist of the spaces in the game*/
    ArrayList<MySpace> spaces;
    /** stations is the arraylist of all the stations*/
    ArrayList<MyStation> stations;
    /** tracks is the arraylist of all the tracks in the game*/
    ArrayList<Track> tracks = new ArrayList<>();
    /** routes is the arraylist of all the routes in the game*/
    ArrayList<Route> routes;

    /**
     * MyRailRoadMap is the constructor for the RailRoadMap object
     * @param spaces is the arraylist of spaces in the map
     * @param stations is the arraylist of stations in the map
     * @param routes is the arraylist of routes in the map
     */
    public MyRailroadMap(ArrayList<MySpace> spaces, ArrayList<MyStation> stations, ArrayList<Route> routes){
        this.spaces=spaces;
        this.stations=stations;
        this.routes=routes;
        for (Route r : routes){
            tracks.addAll(r.getTracks());
        }
    }

    /**
     * getLengthOfShortestUnclaimedRoute is the method that returns the length of the shortest possible route
     * @return the shortest possible route to be claimed
     */
    public int getLengthOfShortestUnclaimedRoute(){
        Route temp = routes.get(0);
        int shortest = Integer.MAX_VALUE;
        for (model.Route r : routes){
            if (r.getLength()<shortest && r.getBaron()==Baron.UNCLAIMED){
                shortest=r.getLength();
                temp = r;
            }
        }
        if (temp.getBaron()!=Baron.UNCLAIMED){
            return 0;
        }
        return shortest;
    }

    /**
     * removeObserver is the method used to remove the observer from the arraylist
     * @param observer The observer to remove from the collection of
     *                 registered observers that will be notified of
     */
    public void removeObserver(RailroadMapObserver observer){
        observers.remove(observer);
    }

    /**
     * getRoutes returns all of the routes in the map
     * @return all of the routes
     */
    public Collection<Route> getRoutes(){
        return this.routes;
    }

    /**
     * getSpace is the method used to return the space at the required coordinates
     * @param row The row of the desired {@link Space}.
     * @param col The column of the desired {@link Space}.
     *
     * @return the space at the row and column
     */
    public Space getSpace(int row, int col){
        for (MyStation s: stations){
            if (s.getCol()==col && s.getRow()==row){
                return s;
            }
        }
        for (Track s: tracks){
            if (s.getCol()==col && s.getRow()==row){
                return s;
            }
        }
        for (Space s : spaces){
            if (s.getCol()==col && s.getRow()==row){
                return s;
            }
        }
        return null;
    }

    /**
     * getRows is the method that returns the rows in the game
     * @return the number of rows
     */
    public int getRows(){
        int numrows = stations.get(0).getRow();
        for (MyStation s : stations){
            if (s.getRow()>numrows){
                numrows=s.getRow()+1;
            }
        }
        return numrows;
    }

    /**
     * getCols is the method that returns the columns in the game
     * @return the number of columns
     */
    public int getCols(){
        int numcols = stations.get(0).getCol();
        for (MyStation s : stations){
            if (s.getCol()>numcols){
                numcols=s.getCol();
            }
        }
        return numcols+1;
    }

    /**
     * addObserver adds an observer to the arraylist
     * @param observer The {@link RailroadMapObserver} being added to the map.
     */
    public void addObserver(RailroadMapObserver observer){
        observers.add(observer);
    }

    /**
     * routeClaimed is the method used to properly claim a route in the map
     * @param route The {@link Route} that has been claimed.
     */
    public void routeClaimed(model.Route route){
        int temp = routes.indexOf(route);
        routes.get(routes.indexOf(route)).claim(route.getBaron());
        for (RailroadMapObserver railroadMapObserver : observers){
            railroadMapObserver.routeClaimed(this, routes.get(temp));
        }
    }

    /**
     * getRoute is the method used to get a Route at a required row and column
     * @param row The row of the location of one of the {@link Track tracks}
     *            in the route.
     * @param col The column of the location of one of the
     * {@link Track tracks} in the route.
     *
     * @return the route at a row and column
     */
    public Route getRoute(int row, int col){
        for (Route r : routes){
            List<Track> temp = r.getTracks();
            for (Track t : temp){
                if (t.collocated(new MySpace(row, col))){
                    return r;
                }
            }
        }
        return null;
    }
}
