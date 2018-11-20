package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.state.MultiplySpeedState;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for the PoneyModel class.
 */
public class PoneyTest {

    /**
     * On teste que les poneys avancent bien a la bonne vitesse.
     */
    //@Test
    public void testMoveSpeed() {
        // Given
        PoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.42);
        double expectedSpeed = 0.42 / p.getSpeedDivider();

        // When
        p.step();

        // Then
        assertEquals(expectedSpeed, p.getDistance(), 0.001);
    }

    /**
     * On teste que les poneys ne sortent pas du terrain.
     */
    //@Test
    public void testBoundaries() {
        // Given
        PoneyModel p = new NyanPoneyModel();
        p.setRandSpeed();

        // When
        while (p.getNbTours() < 2) {
            // Then
            assertTrue(p.getDistance() >= 0);
            assertTrue(p.getDistance() <= 1);
            p.step();
        }
    }

    /**
     * On teste que les super poneys soient bien boostés.
     */
    //@Test
    public void testBoost() {
        // Given
        NyanPoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.42);
        MultiplySpeedState state = new MultiplySpeedState(5000);
        p.addState(state);

        double expectedProgress = 0.42 / p.getSpeedDivider();
        expectedProgress *= p.getSpeedMultiplier();

        //When  
        for (int i = 0; i < p.getStates().size(); i++) {
            p.getStates().get(i).applyState(p);
            p.step();
        }

        //Then
        assertEquals(expectedProgress, p.getDistance(), 0.001);
    }

    //@Test
    public void testStateRemove() {
        // Given
        NyanPoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.42);
        MultiplySpeedState state = new MultiplySpeedState(5000);
        p.addState(state);

        //When  
        for (int i = 0; i < p.getStates().size(); i++) {
            p.getStates().get(i).applyState(p);
            p.step();
        }

        //Then
        assertEquals(0, p.getStates().size(), 0.001);
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
        assertEquals(expectedSpeed, p.getSpeed(), 0.001);
    }

    /**
     * On teste que les super poneys ne puissent pas réutiliser leur pouvoir une
     * 2ème fois.
     */
    /**
     * On test si les poneys voient leur vitesse augmenter après une
     * accélération.
     */
    @Test
    public void testAccelerer() {
        PoneyModel p = new NyanPoneyModel();
        p.setAcceleration(0.005);
        p.setSpeed(0.100);

        double expectedSpeed = 0.105;

        // When
        p.accelerer();

        //Then 
        assertEquals(expectedSpeed, p.getSpeed(), 0.001);

    }

    /**
     * On test si les poneys peuvent aller au dela de la vitesse autorisé après
     * une accélération.
     */
    @Test
    public void testTooMuchAccelerer() {
        PoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.499);
        p.setAcceleration(0.005);

        double expectedSpeed = 0.5;

        // When 
        p.accelerer();

        //Then
        assertEquals(expectedSpeed, p.getSpeed(), 0.001);
    }

    /**
     * On teste si le poney a bien sa vitesse divisé par deux après être touché
     * par un objet ou un pouvoir.
     */
    @Test
    public void testIsTouched() {
        PoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.500);
        p.setIsTouched(true);

        double expectedSpeed = 0.250;

        // When 
        p.isTouched();

        // Then 
        assertEquals(expectedSpeed, p.getSpeed(), 0.001);

    }

    /**
     * On teste si la vitesse du poney a bien doublé quand il a utilisé son
     * pouvoir.
     */
    @Test
    public void testNyanPower() {
        PoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.300);

        double expectedSpeed = 0.600;

        //When 
        p.usePower();

        //then 
        assertEquals(expectedSpeed, p.getSpeed(), 0.001);
    }

    /**
     * On teste si le poney touché par un poney enragé voit sa vitesse divisée
     * par 2.
     */
    @Test
    public void testEnragedPower() {
        EnragedPoneyModel p = new EnragedPoneyModel();
        PoneyModel victim = new NyanPoneyModel();
        victim.setSpeed(0.5);

        double expectedSpeed = 0.25;

        //When
        p.usePower(victim);

        //then
        assertEquals(expectedSpeed, victim.getSpeed(), 0.001);

    }

    @Test
    public void testBooleanPowerEnragedPoney() {
        EnragedPoneyModel p = new EnragedPoneyModel();
        PoneyModel victim = new NyanPoneyModel();
        boolean expectedPowerState = true;

        //When
        p.usePower(victim);

        //then 
        assertEquals(expectedPowerState, p.getPower().getPowerIsCasted());
    }

    @Test
    public void testBooleanPowerNyanPoney() {
        PoneyModel p = new NyanPoneyModel();
        boolean statePowerExpected = true;

        //When
        p.usePower();

        //then 
        assertEquals(statePowerExpected, p.getPower().getPowerIsCasted());
    }

}
