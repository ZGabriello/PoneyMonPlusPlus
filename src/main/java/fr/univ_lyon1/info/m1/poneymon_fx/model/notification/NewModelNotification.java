/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.model.notification;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

/**
 *
 * @author Alex
 */
public class NewModelNotification extends Notification{
    FieldModel newField;
    public NewModelNotification(FieldModel field) {
        super("NEWMODEL");
        this.newField = field;
    }
    
    public FieldModel getField(){
        return this.newField;
    }
    
}
