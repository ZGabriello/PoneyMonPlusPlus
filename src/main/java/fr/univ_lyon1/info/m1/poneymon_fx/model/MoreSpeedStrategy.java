package fr.univ_lyon1.info.m1.poneymon_fx.model;

public class MoreSpeedStrategy extends Strategy {

    public MoreSpeedStrategy(FieldModel f,int i) {
        super(f,i);
    }
    
    @Override
    public void action() {
        if ((monponey.progress == 0) 
                && (monponey.getSpeed() > 0.5 / monponey.getSpeedDivider() 
                        || monponey.getNbTours() == field.winAt - 1)) {
            monponey.usePower();
        }
    }
}