package student;

/**
 * MyStation is the class used for station objects in the game
 * @author srikanthtumati
 */
public class MyStation extends MySpace implements model.Station{
    /** name is the name for the station*/
    private String name;

    /**
     * MyStation is the constructor for station objects
     * @param name is the name of the station
     * @param row the row of the station
     * @param col the col of the station
     */
    public MyStation(String name, int row, int col) {
        super(row, col);
        this.name=name;
    }

    /**
     * getName returns the name of the station
     * @return the name of the station
     */
    public String getName(){
        return this.name;
    }
}
