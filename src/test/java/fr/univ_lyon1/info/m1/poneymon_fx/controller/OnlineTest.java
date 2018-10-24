/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import org.junit.Test;

/**
 *
 * @author Alex
 */
public class OnlineTest {
    public OnlineTest(){
        OnlineController c1 = new OnlineController();
        c1.model = new FieldModel(5);
        c1.createLobby();
        c1.lobby.server.lobby = c1.lobby;
        OnlineController c2 = new OnlineController();
        c2.joinLobby(c1.lobby.hostIp, 9000);
        c1.startGame(5);
        c1.gamePause();
    }
    
    @Test
    public void testPause() throws InterruptedException{
        Thread.sleep(1000);
        assert(false);
    }
    
    
}
