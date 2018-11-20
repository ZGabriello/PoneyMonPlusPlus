package fr.univ_lyon1.info.m1.poneymon_fx.model.notification;

/**
 * Classe décrivant les changements du modèle aux Observers.
 *
 */
public abstract class Notification {

    public String name;

    public Notification(String name) {
        this.name = name;
    }
}
