/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.model;


import static fr.univ_lyon1.info.m1.poneymon_fx.model.EnragedPoneyModel.SPEED_DIVIDER_ENRAGED;
import java.sql.Timestamp;

/**
 *
 * @author Elo
 */
public class DivideSpeedState extends State {
    
    public DivideSpeedState(long duration) {
        super(duration);
    }
    
    public void applyState(PoneyModel pm) {
        this.startTime = new Timestamp(System.currentTimeMillis());
        this.endTime = new Timestamp(this.startTime.getTime() + duration);

        //while (checkExpired() == false) {            
            pm.divideSpeed(SPEED_DIVIDER_ENRAGED);       
        //}
        unapplyState(pm);
    }
            
    
}
