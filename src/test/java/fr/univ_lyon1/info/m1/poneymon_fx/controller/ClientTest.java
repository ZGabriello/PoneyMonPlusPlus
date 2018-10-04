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
public class ClientTest {

    public ClientTest() {
    }

    /**
     * Test of close method, of class Client.
     */
    @Test
    public void testClose() throws InterruptedException {
        System.out.println("close");
        Server s = new Server();
        s.open();
        Thread.sleep(50);
        Client instance = new Client("127.0.0.1", 9000);
        Thread.sleep(50);
        instance.close();
        s.close();
        Thread.sleep(50);
        assert (instance.sock.isClosed());

    }

}
