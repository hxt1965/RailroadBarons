package student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyStationTest {
    private MyStation myStation;
    @Before
    public void setUp() throws Exception {
        myStation = new MyStation("Rochester", 3, 4);
    }

    @Test
    public void getName() {
        assertEquals(myStation.getName(), "Rochester");
    }
}