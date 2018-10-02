/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 *
 * @author Elo
 */
public enum Bonus {
    NyanPoney("NyanPoney");

    private final String name;

    Bonus(final String s) {
        this.name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}
