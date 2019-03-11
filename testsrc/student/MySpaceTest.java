package student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MySpaceTest {
    private MySpace space;
    @Before
    public void setUp() throws Exception {
        space=new MySpace(3,4);
    }

    @Test
    public void getRow() {
        assertEquals(space.getRow(), 3);
    }

    @Test
    public void getCol() {
        assertEquals(space.getCol(), 4);
    }

    @Test
    public void collocated() {
        assertEquals(space.collocated(new MySpace(3,4)), true);
    }
}