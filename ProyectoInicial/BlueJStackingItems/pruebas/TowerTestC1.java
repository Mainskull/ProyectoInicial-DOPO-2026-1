package pruebas;

import dominio.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TowerTestC1
{
    private Tower tower;

    @Before
    public void setUp()
    {
        tower = new Tower(10, 20);
        tower.makeInvisible();
    }

    @Test
    public void shouldAddCupCorrectly()
    {
        tower.pushCup(3);

        String[][] items = tower.stackingItems();

        assertEquals(1, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("3", items[0][1]);
    }

    @Test
    public void shouldAddCupAndLidCorrectly()
    {
        tower.pushCup(4);
        tower.pushLid(4);

        String[][] items = tower.stackingItems();

        assertEquals(2, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("4", items[0][1]);
        assertEquals("lid", items[1][0]);
        assertEquals("4", items[1][1]);
    }

    @Test
    public void shouldRemoveCupCorrectly()
    {
        tower.pushCup(5);
        tower.pushLid(2);
        tower.removeCup(5);

        String[][] items = tower.stackingItems();

        assertEquals(1, items.length);
        assertEquals("lid", items[0][0]);
        assertEquals("2", items[0][1]);
    }

    @Test
    public void shouldFailWhenRemovingCupThatDoesNotExist()
    {
        tower.pushCup(3);
        tower.removeCup(8);

        String[][] items = tower.stackingItems();

        assertEquals(1, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("3", items[0][1]);
    }

    @Test
    public void shouldFailWhenRemovingLidThatDoesNotExist()
    {
        tower.pushLid(4);
        tower.removeLid(9);

        String[][] items = tower.stackingItems();

        assertEquals(1, items.length);
        assertEquals("lid", items[0][0]);
        assertEquals("4", items[0][1]);
    }

    @Test
    public void shouldFailWhenTryingToAddRepeatedCup()
    {
        tower.pushCup(2);
        tower.pushCup(2);

        String[][] items = tower.stackingItems();

        assertEquals(1, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("2", items[0][1]);
    }
}