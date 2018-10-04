/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */
public class ClientToServerProcessorTest {

    Server s;
    Client c;
    ClientToServerProcessor instance;

    public ClientToServerProcessorTest() {
        s = new Server();
        s.open();
        c = new Client("localhost", s.port);
        instance = c.processor;

    }

    /**
     * Test of run method, of class ClientToServerProcessor.
     */
    @Test
    public void testReceive() throws IOException, InterruptedException {
        System.out.println("run");
        Thread.sleep(50);
        s.processor.sendCommand("CLOSE");
        Thread.sleep(50);
        assert (instance.connexionFermee);
        c.close();
        s.close();
    }

}
