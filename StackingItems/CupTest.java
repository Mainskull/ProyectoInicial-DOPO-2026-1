import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CupTest{

    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
        
    }

    
     @Test
    public void shouldCreateCup(){
        Canvas canva = Canvas.getCanvas();
        Cup cup3 = new Cup(3, 300, 400, "magenta");
        cup3.makeVisible();
        Cup cup2 = new Cup(2, 300, 400 - (Cup.PxPerCm), "green");
        cup2.makeVisible();
        Cup cup1 = new Cup(1, 300, 400 - (2*Cup.PxPerCm), "blue");
        cup1.makeVisible();
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