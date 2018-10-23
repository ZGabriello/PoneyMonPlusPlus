/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.model;


public class PowerDoubleSpeed extends PowerModel {
    
@Override
public void use(PoneyModel p){
    if(this.getPowerIsCasted() == false){
        p.setSpeed(p.getSpeed()*2);
        this.setPowerIsCasted(true);
    }
}
}

