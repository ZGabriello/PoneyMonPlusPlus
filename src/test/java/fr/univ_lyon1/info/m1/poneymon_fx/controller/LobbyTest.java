/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */
public class LobbyTest {
    
    public LobbyTest() {
    }

    /**
     * Test of getUsedIp method, of class Lobby.
     */
    @Test
    public void testGetUsedIp() throws UnknownHostException {
        System.out.println("getUsedIp");
        Lobby instance = new Lobby();
        String expResult = InetAddress.getLocalHost().getHostAddress();
        
        String result = instance.getUsedIp();
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
}
