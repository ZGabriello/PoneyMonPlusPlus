package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Classe créant les bouts de voie du bon type en détectant leur forme.
 *
 */
public class LanePartBuilder {
    /**
     * Crée le bout de voie correspondant à la forme décrite par les lignes reliées.
     * @param l1 Line de début de LanePart
     * @param beginLaneId voie concernée sur la Line de début
     * @param l2 Line de fin de LanePart
     * @param endLaneId voie concernée sur la line de fin
     *
     */
    public LanePart buildLanePart(Line l1, int beginLaneId, Line l2, int endLaneId) {
        boolean haveEqualAngles = Util.haveEqualAngles(l1, l2);
        boolean haveOppositeAngles = Util.haveOppositeAngles(l1, l2);
        boolean areAligned = Util.areAligned(l1, l2);
        
        if (haveEqualAngles || haveOppositeAngles) {
            if (areAligned) {
                // demi-cercle, doivent devenir opposite pointant vers intersection
                return new ArcLanePart(l1, beginLaneId, l2, endLaneId);
                // if (haveEqualAngles) {
            } else {
                // ligne droite, doivent être equals
                return new StraightLanePart(l1, beginLaneId, l2, endLaneId);
            }
        } else {
            // faire pointer vers intersection et arc de cercle
            return new ArcLanePart(l1, beginLaneId, l2, endLaneId);
        }
    }
}
