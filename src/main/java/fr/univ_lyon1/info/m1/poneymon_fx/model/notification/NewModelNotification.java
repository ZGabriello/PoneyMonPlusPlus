package fr.univ_lyon1.info.m1.poneymon_fx.model.notification;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

/**
 * notifie de la mise e place d'un nouveau modèle.
 * @author Alex
 */
public class NewModelNotification extends Notification {

    FieldModel newField;

    public NewModelNotification(FieldModel field) {
        super("NEWMODEL");
        this.newField = field;
    }

    public FieldModel getField() {
        return this.newField;
    }

}
