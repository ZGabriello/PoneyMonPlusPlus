package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Classe donnant les IAs des poneys.
 *
 */
public class Strategy {

    FieldModel field;
    int position;
    PoneyModel monponey;

    /**
     * Constructeur de stratégie.
     * @param f terrain de jeu
     * @param i position du poney correspondant a la strategie
     */
    public Strategy(FieldModel f, int i) {
        field = f;
        position = i;
        monponey = f.poneys.get(i);
    }
    
    public Strategy() {}



    public void action() {}
}
