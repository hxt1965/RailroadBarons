package student;

import model.Baron;
import model.Orientation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyTrackTest {
    private MyTrack myTrack;
    private MyRoute route;
    @Before
    public void setUp() throws Exception {
        route = new MyRoute(Baron.UNCLAIMED , new MyStation("Rochester", 3, 1) , new MyStation("Syracuse", 3 ,4));
        myTrack = new MyTrack(3, 4, Orientation.VERTICAL, Baron.UNCLAIMED, route);
    }

    @Test
    public void getBaron() {
        assertEquals(myTrack.getBaron(), Baron.UNCLAIMED);
    }

    @Test
    public void getOrientation() {
        assertEquals(myTrack.getOrientation(), Orientation.VERTICAL);
    }

    @Test
    public void getRoute() {
        assertEquals(myTrack.getRoute(), route);
    }
}