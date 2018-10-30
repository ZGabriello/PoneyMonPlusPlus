package fr.univ_lyon1.info.m1.poneymon_fx.model.track;

import static fr.univ_lyon1.info.m1.poneymon_fx.model.track.Line.numberOfAngles;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Classe package contenant toutes les fonctions mathématiques utilisées pour
 * manipuler les formes de voies.
 *
 */
public class Util {
    public static int modulo(int number, int base) {
        int reste = number % base;
        return (reste >= 0) ? reste : (reste + base);
    }
    
    public static double modulo(double number, double base) {
        double reste  = number % base;
        return (reste >= 0) ? reste : (reste + base);
    }
    
    public static int oppositeMultOf(int mult) {
        return modulo(mult + numberOfAngles / 2, numberOfAngles);
    }
    
    public static boolean haveEqualAngles(Line l1, Line l2) {
        return l1.multiple == l2.multiple;
    }
    
    public static boolean haveOppositeAngles(Line l1, Line l2) {
        return l1.multiple == oppositeMultOf(l2.multiple);
    }
    
    /**
     * Retourne la longueur de l'angle compris entre l1 et l2.
     *
     */
    public static double getAngleDifference(Line l1, Line l2) {
        int multDiff = modulo(l2.multiple - l1.multiple, numberOfAngles);
        
        return multDiff * Line.minAngle;
    }
    
    /**
     * Retourne le cos et le sin minimum et maximum sur l'intervalle délimité
     * par les angles de l1 et l2.
     *
     */
    public static double[] minMaxInAngleRange(Line l1, Line l2) {
        int noa = Line.numberOfAngles;
        int mult1 = oppositeMultOf(l1.multiple);
        int mult2 = oppositeMultOf(l2.multiple);
        
        if (mult2 < mult1) {
            mult2 += noa;
        }
        
        double minCos = 1;
        double minSin = 1;
        double maxCos = -1;
        double maxSin = -1;
        
        boolean contains0 = (mult1 == 0 || mult2 >= noa);
        if (contains0) {
            maxCos = 1;
        }
        
        boolean containsPiOver2 = (mult1 <= (noa / 4) && mult2 >= (noa / 4));
        if (containsPiOver2) {
            maxSin = 1;
        }
        
        boolean containsPi = (mult1 <= (noa / 2) && mult2 >= (noa / 2));
        if (containsPi) {
            minCos = -1;
        }
        
        boolean contains3PiOver2 = (mult1 <= (3 * (noa / 4)) && mult2 >= (3 * (noa / 4)));
        if (contains3PiOver2) {
            minSin = -1;
        }
        
        double minAngle = Line.minAngle;
        
        double cos1 = cos(mult1 * minAngle);
        double cos2 = cos(mult2 * minAngle);
        double sin1 = sin(mult1 * minAngle);
        double sin2 = sin(mult2 * minAngle);
        
        minCos = min(minCos, cos1, cos2);
        minSin = min(minSin, sin1, sin2);
        maxCos = max(maxCos, cos1, cos2);
        maxSin = max(maxSin, sin1, sin2);
        
        return new double[] {minCos, minSin, maxCos, maxSin};
    }
    
    public static boolean isInf(double slope) {
        // on considère qu'au dela de 100 il s'agit d'une droite verticale
        return abs(slope) > Line.slopeInf;
    }
    
    /**
     * Renvoie vrai si l1 et l2 sont alignées.
     *
     */
    public static boolean areAligned(Line l1, Line l2) {
        double xA = l1.x;
        double yA = l1.y;
        
        double angle = l1.getAngle();
        double xB = l1.x + cos(angle);
        double yB = l1.y + sin(angle);
        
        double xC = l2.x;
        double yC = l2.y;
        
        double slope1 = (yB - yA) / (xB - xA);
        double slope2 = (yC - yA) / (xC - xA);
        
        if (isInf(slope1) && isInf(slope2)) {
            return true;
        }
        
        // tolérance pour le test d'égalité
        return abs(slope1 - slope2) < 0.001;
    }
    
    public static double dist(double xA, double yA, double xB, double yB) {
        return sqrt((xB - xA) * (xB - xA) + (yB - yA) * (yB - yA));
    }
    
    /**
     * Renvoie le point d'intersection de l1 et l2.
     * @throws ParallelLinesException  if l1 and l2 are parallel
     *
     */
    public static double[] getIntersection(Line l1, Line l2) throws ParallelLinesException {
        
        if (haveEqualAngles(l1, l2) || haveOppositeAngles(l1, l2)) {
            if (areAligned(l1, l2)) {
                throw new ParallelLinesException("Can't find the intersection of 2 parallel lines");
            }
        }
        
        double centerX;
        double centerY;
        double x0 = l1.x;
        double y0 = l1.y;
        double x1 = l2.x;
        double y1 = l2.y;
        
        // l'équation de la droite d0 (x0, y0, x1, y1) est
        // d0 : y = a0 * x + b0
        // et celle de d1 (x2, y2, x3, y3) est
        // d1 : y = a1 * x + b1
        double a0 = l1.slope;
        double a1 = l2.slope;
        
        double b0 = y0 - x0 * a0;
        double b1 = y1 - x1 * a1;
        
        boolean isA0Inf = abs(a0) > 100;
        boolean isA1Inf = abs(a1) > 100;
        
        if (isA0Inf || isA1Inf) {
            if (isA0Inf && isA1Inf) {
                centerX = x0;
                centerY = (y0 + y1) / 2;
            } else {
                if (isA0Inf) {
                    centerX = x0;
                    centerY = a1 * x0 + b1;
                } else {
                    centerX = x1;
                    centerY = a0 * x1 + b0;
                }
            }
        } else {
            // a0 * x + b0 = a1 * x + b1
            // <=> x = (b1 - b0) / (a0 - a1)
            // le point d'intersection sera le centre de l'arc de cercle
            centerX = (b1 - b0) / (a0 - a1);
            centerY = a0 * centerX + b0;
        }
        
        return new double[] {centerX, centerY};
    }
    
    /**
     * Renvoie la pente de la droite décrite par le point (x,y) et l'angle.
     *
     */
    public static double computeSlope(double angle, double x, double y) {
        double xPrime = x + cos(angle);
        double yPrime = y + sin(angle);
        
        return (yPrime - y) / (xPrime - x);
    }

    /**
     * Renvoie le minimum parmi les nombres donnés en arguments.
     *
     */
    public static double min(double... numbers) {
        double curMin = numbers[0];
        
        for (double number : numbers) {
            if (number < curMin) {
                curMin = number;
            }
        }
        
        return curMin;
    }
    
    /**
     * Renvoie le maximum parmi les nombres donnés en arguments.
     *
     */
    public static double max(double... numbers) {
        double curMax = numbers[0];
        
        for (double number : numbers) {
            if (number > curMax) {
                curMax = number;
            }
        }
        
        return curMax;
    }

    public static class ParallelLinesException extends Exception {
        public ParallelLinesException(String message) {
            super(message);
        }
    }
}
