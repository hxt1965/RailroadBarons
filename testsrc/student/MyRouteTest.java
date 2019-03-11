package student;

import model.Baron;
import model.Orientation;
import model.Track;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyRouteTest {

    private MyRoute myRoute;
    private MyStation origin;
    private MyStation destination;
    @Before
    public void setUp(){
        origin = new MyStation("Rochester", 3, 1);
        destination = new MyStation("Syracuse", 3 ,4);
        myRoute = new MyRoute(Baron.UNCLAIMED, origin, destination);
    }

    @Test
    public void getBaron() {
        assertEquals(myRoute.getBaron(), Baron.UNCLAIMED);
    }

    @Test
    public void getOrigin() {
        assertEquals(myRoute.getOrigin(), origin);
    }

    @Test
    public void claim() {
        assertEquals(myRoute.claim(Baron.BLUE), true);
    }

    @Test
    public void getOrientation() {
        assertEquals(myRoute.getOrientation(), Orientation.HORIZONTAL);
    }

    @Test
    public void includesCoordinate() {
        assertEquals(myRoute.includesCoordinate(new MySpace(3,3)), true );
        assertEquals(myRoute.includesCoordinate(new MySpace(1,3)), false );
    }

    @Test
    public void getLength() {
        assertEquals(myRoute.getLength(), 2);
    }

    @Test
    public void getTracks() {
        List<Track> tracks = new ArrayList<>();
        tracks.add(myRoute.getTracks().get(0));
        tracks.add(myRoute.getTracks().get(1));
        assertEquals(myRoute.getTracks(), tracks);
    }

    @Test
    public void getDestination() {
        assertEquals(myRoute.getDestination(), destination);
    }

    @Test
    public void getPointValue() {
        assertEquals(myRoute.getPointValue(), 2);
    }
}