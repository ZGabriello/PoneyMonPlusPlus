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
public class ServerToClientProcessorTest {
    Client c;
    Server s;
    ServerToClientProcessor instance;
    public ServerToClientProcessorTest() {
        s = new Server();
        s.open();
        c = new Client("127.0.0.1",9000);
        instance = s.processor;
    }

    /**
     * Test of run method, of class ServerToClientProcessor.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
