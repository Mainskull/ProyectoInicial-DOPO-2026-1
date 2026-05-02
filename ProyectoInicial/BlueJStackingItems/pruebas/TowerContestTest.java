package pruebas;

import dominio.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TowerContestTest
{
    private TowerContest contest;

    @Before
    public void setUp()
    {
        contest = new TowerContest();
    }

    @Test
    public void shouldSolveSingleCup()
    {
        String respuesta = contest.solve(1, 1);
        assertEquals("1", respuesta);
    }

    @Test
    public void shouldReturnImpossibleWhenHeightIsTooSmall()
    {
        String respuesta = contest.solve(4, 6);
        assertEquals("impossible", respuesta);
    }

    @Test
    public void shouldReturnImpossibleWhenHeightIsTooLarge()
    {
        String respuesta = contest.solve(4, 17);
        assertEquals("impossible", respuesta);
    }

    @Test
    public void shouldReturnImpossibleForForbiddenHeight()
    {
        String respuesta = contest.solve(4, 14);
        assertEquals("impossible", respuesta);
    }

    @Test
    public void shouldReturnMinimumArrangement()
    {
        String respuesta = contest.solve(4, 7);
        assertEquals("7 5 3 1", respuesta);
    }

    @Test
    public void shouldReturnMaximumArrangement()
    {
        String respuesta = contest.solve(4, 16);
        assertEquals("1 3 5 7", respuesta);
    }

    @Test
    public void acceptanceSample2ShouldBeImpossible()
    {
        String respuesta = contest.solve(4, 100);
        assertEquals("impossible", respuesta);
    }

    @Test
    public void acceptanceSample1ShouldReturnSomeSolution()
    {
        String respuesta = contest.solve(4, 9);

        assertNotEquals("impossible", respuesta);

        String[] datos = respuesta.split(" ");
        assertEquals(4, datos.length);
    }

    @Test
    public void shouldReturnFourHeightsForFourCups()
    {
        String respuesta = contest.solve(4, 9);

        assertNotEquals("impossible", respuesta);

        String[] datos = respuesta.split(" ");
        assertEquals(4, datos.length);
    }

    @Test
    public void shouldUseOddHeightsOnly()
    {
        String respuesta = contest.solve(4, 9);

        assertNotEquals("impossible", respuesta);

        String[] datos = respuesta.split(" ");
        for (int i = 0; i < datos.length; i++) {
            int altura = Integer.parseInt(datos[i]);
            assertTrue(altura % 2 != 0);
        }
    }
}
