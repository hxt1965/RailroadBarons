package student;

import model.Station;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Node representing a station and its neighbors
 */

public class MyNode {

    //the station
    private Station station;
    //list containing its neighbors
    private List<MyNode> neighbors;

    /**
     * constructor for node instantiation created
     *
     * @param station: the station the node represents
     */
    public MyNode(Station station){
        this.station=station;
        this.neighbors=new LinkedList<MyNode>();
    }

    /**
     * getter for the name of the station
     *
     * @return: the name of the station
     */
    public String getName(){
        return this.station.getName();
    }

    /**
     * returns the list containing all neighbors
     *
     * @return: the list of neighbors
     */
    public Collection<MyNode> getNeighbors(){
        return new ArrayList<>(this.neighbors);
    }

    /**
     * adds neighbor (instance of node) to the list of neighbors
     *
     * @param myNode: the node (Station) to be added
     */
    public void addNeighbor(MyNode myNode){
        if (!neighbors.contains(myNode)) {
            neighbors.add(myNode);
        }
    }
}
