/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ_lyon1.info.m1.poneymon_fx.model;

import static fr.univ_lyon1.info.m1.poneymon_fx.model.NyanPoneyModel.SPEED_MULTIPLIER;
import java.sql.Timestamp;

/**
 * Classe gérant le boost de vitesse (Bonus).
 * @author Elo
 */
public class DoubleSpeedState extends State {
    
    
    public DoubleSpeedState(long duration) {
        super(duration);
    }
    
    public void applyState(PoneyModel pm) {
        this.startTime = new Timestamp(System.currentTimeMillis());
        this.endTime = new Timestamp(this.startTime.getTime() + duration);

        while (checkExpired() == false) {            
            pm.multiplySpeed(SPEED_MULTIPLIER);       
        }
        unapplyState(pm);
    }

}
