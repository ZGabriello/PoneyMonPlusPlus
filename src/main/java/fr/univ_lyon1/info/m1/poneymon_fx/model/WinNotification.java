package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Notification envoyée aux Observers quand un poney gagne.
 *
 */
public class WinNotification extends Notification {
    public int winner;
    
    public WinNotification(int winner) {
        super("WIN");
        this.winner = winner;
    }
}