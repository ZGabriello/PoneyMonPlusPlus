package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.DoubleSpeedState;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for the PoneyModel class.
 */
public class PoneyTest {

    /**
     * On teste que les poneys avancent bien a la bonne vitesse.
     */
    @Test
    public void testMoveSpeed() {
        // Given
        PoneyModel p = new NyanPoneyModel();
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
    public void testBoundaries() {
        // Given
        PoneyModel p = new NyanPoneyModel();
        p.setRandSpeed();

        // When
        while (p.getNbTours() < 2) {
            // Then
            assertTrue(p.getProgress() >= 0);
            assertTrue(p.getProgress() <= 1);
            p.step();
        }
    }

    /**
     * On teste que les super poneys soient bien boostés.
     */
    @Test
    public void testBoost() {
        // Given
        NyanPoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.42);
        DoubleSpeedState state = new DoubleSpeedState(5000);
        p.addState(state);

        double expectedProgress = 0.42 / p.getSpeedDivider();
        expectedProgress *= p.getSpeedMultiplier();

        //When  
        for (int i = 0; i < p.getStates().size(); i++) {
            p.getStates().get(i).applyState(p);
            p.step();
        }
        
        //Then
        assertEquals(p.getProgress(), expectedProgress, 0.001);
    }

    /**
     * On teste que les super poneys ne conservent pas leur boost de vitesse
     * après la fin du pouvoir.
     */
    @Test
    public void testTooMuchBoost() {
        // Given
        NyanPoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.42);
        double expectedSpeed = 0.42;

        //When
        p.usePower();
        p.endPower();
        p.setSpeed(0.42);

        //Then
        assertEquals(p.getSpeed(), expectedSpeed, 0.001);
    }

    /**
     * On teste que les super poneys ne puissent pas réutiliser leur pouvoir une
     * 2ème fois.
     */
    @Test
    public void testBoostUsedTwice() {
        // Given
        NyanPoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.42);
        double expectedSpeed = 0.42;

        //When
        p.usePower();
        p.endPower();
        p.setSpeed(0.42);
        p.usePower();

        //Then
        assertEquals(p.getSpeed(), expectedSpeed, 0.001);
    }
}
