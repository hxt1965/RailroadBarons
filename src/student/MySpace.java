package student;

/**
 * MySpace is the class used to create space objects for the game
 * @author srikanthtumati
 */
public class MySpace implements model.Space {
    /** row is the row in which the space is located*/
    private int row;
    /** col is the column in which the space is located*/
    private int col;

    /**
     * MySpace is the constructor for space objects
     * @param row the row in which the space is located
     * @param col the column in which the space is located
     */
    public MySpace(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * getRow is the method that returns the row of the space
     * @return the row of the space
     */
    public int getRow() {
        return this.row;
    }

    /**
     * getCol returns the column of the space
     * @return the column of the space
     */
    public int getCol() {
        return this.col;
    }

    /**
     * collocated determines if two space objects are equal
     * @param other The other space to which this space is being compared for
     *              collocation.
     *
     * @return true if a match and false otherwise
     */
    public boolean collocated(model.Space other) {
        return (other.getCol() == this.getCol() && other.getRow() == this.getRow());
    }


}