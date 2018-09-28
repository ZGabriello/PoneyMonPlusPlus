package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Notification envoyée quand un Poney utilise son pouvoir.
 *
 */
public class PowerNotification extends Notification {
    public boolean state;
    
    public PowerNotification(boolean state) {
        super("POWER");
        this.state = state;
    }
}
