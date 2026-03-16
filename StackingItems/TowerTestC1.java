import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class TowerTestC1.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TowerTestC1
{
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
        
    }

    
     @Test
    public void shouldCreateTower(){
        Tower tower = new Tower(27, 27);
        tower.makeVisible();
    }
    
    @Test
    public void shouldPlaceCups(){
        Tower tower = new Tower(27, 27);
        tower.makeVisible();
        tower.pushCup(5);
        tower.pushCup(4);
        tower.pushCup(2);
        tower.pushCup(3);
    }
    
    @Test
    public void shouldPlaceLidsCups(){
        Tower tower = new Tower(27, 27);
        tower.makeVisible();
        tower.pushCup(5);
        tower.pushCup(4);
        tower.pushCup(2);
        tower.pushLid(3);
        tower.pushCup(3);
        tower.pushLid(1);
        tower.pushLid(2);
        tower.pushCup(1);
 
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