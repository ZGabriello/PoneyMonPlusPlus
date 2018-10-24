package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Objet boost.
 * @author Elo
 */
public class BoostItem extends Item implements PickableUp {
        
    public BoostItem() {
        this.state = new DoubleSpeedState(1000);
    }

    @Override
    public void immediateEffect(PoneyModel pm) {
        state.applyState(pm);
    }

    @Override
    public void useable(PoneyModel pm) {
        
    }
}
