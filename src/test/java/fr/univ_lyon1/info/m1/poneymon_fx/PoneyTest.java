package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for the PoneyModel class.
 */
public class PoneyTest {
    @Test
    public void testTrueIsTrue() {
        assertTrue(true);
    }

    /**
     * On teste que les poneys avancent bien a la bonne vitesse.
     */
    @Test
    public void testMoveSpeed() {
        // Given
        PoneyModel p = new PoneyModel("green");
        p.setSpeed(0.42);
        double expectedSpeed = 0.42 / p.getSpeedDivider();
        
        // When
        p.step();

        // Then
        assertEquals(p.getProgress(), expectedSpeed, 0.001);
    }
    
    /**
     * On teste que les poneys ne sortent pas du terrain.
     */
    @Test
    public void testboundaries() {
        // Given
        PoneyModel p = new PoneyModel("orange");
        p.setRandSpeed();

        
        // When
        while(p.getNbTours() < 2) {
            // Then
            assertTrue(p.getProgress() >= 0);
            assertTrue(p.getProgress() <= 1);
            p.step();
        }
    }
    
    /**
     * On teste que les super poneys sont bien boostés.
     */
    @Test
    public void testboost() {
        // Given
        NyanPoneyModel p = new NyanPoneyModel("blue");
        p.setSpeed(0.42);
        double expectedSpeed = 0.42 / p.getSpeedDivider();
        expectedSpeed*=p.getSpeedMultiplier();
        //When
        p.usePower();
        p.step();     
        //Then
        assertEquals(p.getProgress(), expectedSpeed, 0.001);
    }
    
    /**
     * On teste que les super poneys ne sont pas trop dopés.
     */
    
    @Test
    public void testtoomuchboost() {
        // Given
        NyanPoneyModel p = new NyanPoneyModel("blue");
        p.setSpeed(0.42);
        double expectedSpeed = 0.42 / p.getSpeedDivider();
        //When
        p.usePower();
        p.endPower();
       p.setSpeed(0.42);
        p.usePower();
        //Then
        assertEquals(p.getSpeed(), expectedSpeed, 0.001);
    }
}
