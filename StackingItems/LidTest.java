import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LidTest{

    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
        
    }

    
    @Test
    public void shouldCreateLid(){
        Canvas canva = Canvas.getCanvas();
        Lid lid3 = new Lid(3, 300, 400 - (5*Lid.PxPerCm), "magenta");
        lid3.makeVisible();
        Lid lid2 = new Lid(2, 300, 400 - (4*Lid.PxPerCm), "green");
        lid2.makeVisible();
        Lid lid1 = new Lid(1, 300, 400 - (3*Lid.PxPerCm), "blue");
        lid1.makeVisible();
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}