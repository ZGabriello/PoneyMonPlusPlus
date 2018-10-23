package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.DoubleSpeedState;
import fr.univ_lyon1.info.m1.poneymon_fx.model.EnragedPoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    @Test
    public void testMoveSpeed() {
        // Given
        PoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.42);
        double expectedSpeed = 0.42 / p.getSpeedDivider();

        // When
        p.step();

        // Then
        assertEquals(p.getProgress(), expectedSpeed, 0.01);
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

        assertEquals(p.getProgress(), expectedProgress, 0.01);

       


    }
    
    @Test
    public void testStateRemove() {
        
        // Given
        NyanPoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.42);
        DoubleSpeedState state = new DoubleSpeedState(5000);
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
        assertEquals(p.getSpeed(), expectedSpeed, 0.001);
    }

    /**
     * On teste que les super poneys ne puissent pas réutiliser leur pouvoir une
     * 2ème fois.
     */
    

    
    /**
     * On test si les poneys voient leur vitesse augmenter après une accélération.
     */
    @Test 
    public void testAccelerer(){
        PoneyModel p = new NyanPoneyModel();
        p.setAcceleration(0.005);
        p.setSpeed(0.100);
        
        double speedExpected = 0.105;
        
        // When
        p.accelerer();
        
        //Then 
        assertEquals(speedExpected, p.getSpeed(), 0.001);
        
    }
    
    /**
     * On test si les poneys voient leur vitesse diminuer après une déccéleration.
     */
    @Test 
    public void testDeccelerer(){
        PoneyModel p = new NyanPoneyModel();
        p.setAcceleration(0.005);
        p.setSpeed(0.505);
        
        double speedExpected = 0.500;
        
        // When 
        p.deccelerer();
        
        //Then
        assertEquals(speedExpected, p.getSpeed(), 0.001);
        
        
    }
    
    /**
     *  On test si les poneys peuvent aller au dela de la vitesse autorisé après une accélération.
     */
    @Test
    public void testTooMuchAccelerer(){
        PoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.899);
        p.setAcceleration(0.005);
        double speedExpected = 0.900;
        
        
        // When 
        p.accelerer();
        
        //Then
        assertEquals(speedExpected, p.getSpeed(), 0.001);
    }
    
    /**
     *  On test si les poneys aller en dessous de la vitesse autorisé après une décélération.
     */
    @Test 
    public void testTooMuchDeccelerer(){
        PoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.101);
        p.setAcceleration(0.005);
        double speedExpected = 0.100;
        
        
        // When 
        p.deccelerer();
        
        //Then
        assertEquals(speedExpected, p.getSpeed(), 0.001);
        
    }
    
    /**
     *  On test si le poney a bien sa vitesse divisé par deux après être touché par un objet ou un pouvoir.
     */
    @Test 
    public void testIsTouched(){
        PoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.500);
        p.setIsTouched(true);
        double speedExpected = 0.250;
        
        
        // When 
        p.IsTouched();
        
        
        // Then 
        assertEquals(speedExpected, p.getSpeed(), 0.001);
        
    }
    
    @Test 
    public void testNyanPower(){
        PoneyModel p = new NyanPoneyModel();
        p.setSpeed(0.300);
        double speedExpected = 0.600;
        
        
        //When 
        p.usePower();
        
        //then 
        assertEquals(speedExpected, p.getSpeed(),0.001);
    }
    
    @Test
    public void testEnragedPower(){
        EnragedPoneyModel p = new EnragedPoneyModel();
        PoneyModel victim = new NyanPoneyModel();
        double speedExpected = 0.400;
        victim.setSpeed(0.800);
        
        //When
        p.usePower(victim);
        
        //then
        assertEquals(speedExpected,victim.getSpeed(),0.001);
        
    }
    
    @Test 
    public void testBooleanPowerEnragedPoney(){
        EnragedPoneyModel p = new EnragedPoneyModel();
        PoneyModel victim = new NyanPoneyModel();
        boolean statePowerExpected = true;
        
        //When
        p.usePower(victim);
        
        //then 
        assertEquals(statePowerExpected,p.getPower().getPowerIsCasted());
    }
    
    
    @Test 
    public void testBooleanPowerNyanPoney(){
       PoneyModel p = new NyanPoneyModel();
       boolean statePowerExpected = true;
        
        //When
        p.usePower();
        
        //then 
        assertEquals(statePowerExpected,p.getPower().getPowerIsCasted());
    }
    
}




