package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.ImStillHereNyanStrategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.MoreSpeedNyanStrategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.strategy.NotEnoughSpeedNyanStrategy;
import fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for the PoneyModel class.
 */
public class StrategyTest {
    @Test
    public void testIAMoreSpeed() {
        // Given
        FieldModel f=new FieldModel(1);
        NyanPoneyModel p=(NyanPoneyModel)f.getPoneyModel(0);
        
        p.setStrategy(new MoreSpeedNyanStrategy(f, p, 0));
        p.setSpeed(0.6);
        
        double expectedSpeed = 0.6;
        expectedSpeed *= p.getSpeedMultiplier();
        
        //When
        p.step();
        
        //Then
        assertEquals(p.getSpeed(), expectedSpeed, 0.001);
    }
    
    @Test
    public void testIANotEnoughSpeed() {
        // Given
        FieldModel f=new FieldModel(1);
        NyanPoneyModel p=(NyanPoneyModel)f.getPoneyModel(0);
        
        p.setStrategy(new NotEnoughSpeedNyanStrategy(f, p, 0));
        p.setSpeed(0.3);
        
        double expectedSpeed = 0.3;
        expectedSpeed *= p.getSpeedMultiplier();
        
        //When
        p.step();
        
        //Then
        assertEquals(expectedSpeed, p.getSpeed(), 0.001);
    }
    
    @Test
    public void testIAImStillHere() {
        // Given
        FieldModel f=new FieldModel(2);
        NyanPoneyModel p=(NyanPoneyModel)f.getPoneyModel(0);
        
        p.setStrategy(new ImStillHereNyanStrategy(f, p, 0));
        p.setSpeed(0.3);
        
        double expectedSpeed = 0.3;
        expectedSpeed *= p.getSpeedMultiplier();
        p.setSpeed(NyanPoneyModel.getSpeedMultiplier() * p.getSpeed());
        
        NyanPoneyModel p2=(NyanPoneyModel)f.getPoneyModel(1);
        p2.setProgress(0.5);
        p2.setSpeed(0.4);
        
        //When
        p.step();
        
        //Then
        assertEquals(p.getSpeed(), expectedSpeed, 0.001);
    }
}
