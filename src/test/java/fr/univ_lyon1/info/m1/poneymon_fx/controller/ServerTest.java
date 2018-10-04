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
        instance.open();
    }

    /**
     * Test of close method, of class Server.
     */
    @Test
    public void testClose() throws InterruptedException {
        System.out.println("close");
        instance.close();
        Thread.sleep(50);
        assert (instance.sSocket.isClosed());
    }

    /**
     * Test of open method, of class Server.
     */
    @Test
    public void testOpen() throws InterruptedException {
        System.out.println("open");
        instance.close();
        Thread.sleep(50);
        instance.open();
        assert (instance.isRunning);
        instance.close();
        Thread.sleep(50);
    }

    @Test
    public void testConnection() throws InterruptedException {
        System.out.println("tentative de connexion au serveur");

        Client c = new Client("localhost", instance.port);
        Thread.sleep(50);
        assert (instance.nbConnections > 0);
        instance.close();
        Thread.sleep(50);

    }

}
