package fr.univ_lyon1.info.m1.poneymon_fx.model.track;

import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for the PoneyModel class.
 */
public class UtilTest {
    final static double tolerance = 1e-6;
    /**
     * On teste l'égalité des angles.
     */
    @Test
    public void testAreEquals() {
        // Given
        Line l1 = new Line(0, 0, 0, 0, 0, 0);
        Line l2 = new Line(1, 1, 1, 0, 0, 0);
        
        // When

        // Then
        assertEquals(true, Util.haveEqualAngles(l1, l2));
    }
    
    /**
     * On teste si 2 angles sont bien opposés.
     */
    @Test
    public void testAreOpposite1() {
        // Given
        Line l1 = new Line(0, 0, 0, 1, 0, 0);
        Line l2 = new Line(1, 1, 1, 1 + Line.numberOfAngles / 2, 0, 0);
        
        // When

        // Then
        assertEquals(true, Util.haveOppositeAngles(l1, l2));
    }
    
    /**
     * On teste si 2 angles sont bien opposés.
     */
    @Test
    public void testAreOpposite2() {
        // Given
        Line l1 = new Line(0, 0, 0, Line.numberOfAngles / 2, 0, 0);
        Line l2 = new Line(1, 1, 1, 0, 0, 0);
        
        // When

        // Then
        assertEquals(true, Util.haveOppositeAngles(l1, l2));
    }
    
    /**
     * On teste si 2 angles sont bien opposés.
     */
    @Test
    public void testAreOpposite3() {
        // Given
        Line l1 = new Line(0, 0, 0, -1, 0, 0);
        Line l2 = new Line(1, 1, 1, -1 + Line.numberOfAngles / 2, 0, 0);
        
        // When

        // Then
        assertEquals(true, Util.haveOppositeAngles(l1, l2));
    }
    
    /**
     * On teste si 2 angles ne sont bien pas opposés.
     */
    @Test
    public void testAreNotOpposite() {
        // Given
        Line l1 = new Line(0, 0, 0, 2, 0, 0);
        Line l2 = new Line(1, 1, 1, 2 - Line.numberOfAngles, 0, 0);
        
        // When
        
        // Then
        assertEquals(false, Util.haveOppositeAngles(l1, l2));
    }
    
    /**
     * On teste si 2 lignes sont alignées.
     */
    @Test
    public void testAreAligned1() {
        // Given
        Line l1 = new Line(0, 0, 0, Line.numberOfAngles / 2, 0, 0);
        Line l2 = new Line(1, 1, 0, Line.numberOfAngles / 2, 0, 0);
        
        // When
        
        // Then
        assertEquals(true, Util.areAligned(l1, l2));
    }
    
    /**
     * On teste si 2 lignes sont alignées.
     */
    @Test
    public void testAreAligned2() {
        // Given
        Line l1 = new Line(0, 0, 6, -2, 0, 0);
        Line l2 = new Line(1, 0, 0, -2 + Line.numberOfAngles / 2, 0, 0);
        
        // When
        
        // Then
        assertEquals(true, Util.areAligned(l1, l2));
    }
    
    /**
     * On teste si 2 lignes ne sont pas alignées.
     */
    @Test
    public void testAreNotAligned() {
        // Given
        Line l1 = new Line(0, 0, 0, 0, 0, 0);
        Line l2 = new Line(1, 0, 0.1, 0, 0, 0);
        
        // When
           
        // Then
        assertEquals(false, Util.areAligned(l1, l2));
    }
    
    @Test
    public void testMin() {
        // Given
        
        // When
        
        // Then
        assertEquals(-3, Util.min(17, Double.POSITIVE_INFINITY, 0, -2, 35, -3, 12), tolerance);
    }
    
    @Test
    public void testMax() {
        // Given
        
        // When
        
        // Then
        assertEquals(35, Util.max(17, Double.NEGATIVE_INFINITY, 0, -2, 35, -3, 12), tolerance);
    }
    
    /**
     * On teste MinMaxInAngleRange().
     */
    @Test
    public void testMinMaxInAngleRange1() {
        //Given
        Line l1 = new Line(0, 0, 6, 0, 0, 0);
        Line l2 = new Line(1, 0, 0, Line.numberOfAngles / 4, 0, 0);
        
        // When
        double[] minMax = Util.minMaxInAngleRange(l1.multiple, l2.multiple);
        
        // Then
        assertEquals(0, minMax[0], tolerance);
        assertEquals(0, minMax[1], tolerance);
        assertEquals(1, minMax[2], tolerance);
        assertEquals(1, minMax[3], tolerance);
    }
    
    /**
     * On teste MinMaxInAngleRange().
     */
    @Test
    public void testMinMaxInAngleRange2() {
        //Given
        Line l1 = new Line(0, 9, 0, 2 * Line.numberOfAngles / 8, 0, 3);
        Line l2 = new Line(1, 11.121320344, 0.878679656, 3 * Line.numberOfAngles / 8, 0, 3);
        
        // When
        double[] minMax = Util.minMaxInAngleRange(l1.multiple, l2.multiple);
        
        // Then
        assertEquals(-sqrt(2) / 2, minMax[0], tolerance);
        assertEquals(sqrt(2) / 2, minMax[1], tolerance);
        assertEquals(0, minMax[2],  tolerance);
        assertEquals(1, minMax[3], tolerance);
    }
}