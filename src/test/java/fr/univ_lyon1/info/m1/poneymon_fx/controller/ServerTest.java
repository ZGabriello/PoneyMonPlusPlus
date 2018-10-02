/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */



public class ServerTest {
    Server instance;
    public ServerTest() {
        instance = new Server();
    }

    
    /**
     * Test of close method, of class Server.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        instance.open();
        instance.close();
        assert(instance.sSocket.isClosed());
    }
    
    
    
    /**
     * Test of open method, of class Server.
     */
    @Test
    public void testOpen() {
        System.out.println("open");
        instance.open();        
        assert(instance.isRunning);
        instance.close();
    }

    @Test
    public void testConnection(){
        System.out.println("tentative de connexion au serveur");
        instance.open();
        Client c = new Client("localhost",instance.port);
        assert(instance.nbConnections>0);
        instance.close();
    }
    
    
    
    
}
