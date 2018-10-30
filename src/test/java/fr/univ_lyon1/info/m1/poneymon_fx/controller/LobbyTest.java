/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */
public class LobbyTest {

    public LobbyTest() {
    }

//    /**
//     * Test of getUsedIp method, of class Lobby.
//     */
//    @Test
//    public void testGetUsedIp() throws UnknownHostException {
//        System.out.println("getUsedIp");
//        Lobby instance = new Lobby();
//        String expResult = InetAddress.getLocalHost().getHostAddress();
//        
//        String result = instance.getUsedIp();
//        System.out.println(result);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of setController method, of class Lobby.
//     */
//    @Test
//    public void testSetController() {
//        System.out.println("setController");
//        Controller c = null;
//        Lobby instance = new Lobby();
//        instance.setController(c);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setHost method, of class Lobby.
//     */
//    @Test
//    public void testSetHost() {
//        System.out.println("setHost");
//        String ip = "";
//        Lobby instance = new Lobby();
//        instance.setHost(ip);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRemoteLobby method, of class Lobby.
//     */
//    @Test
//    public void testGetRemoteLobby() {
//        System.out.println("getRemoteLobby");
//        String ip = "";
//        int port = 0;
//        Lobby instance = new Lobby();
//        instance.getRemoteLobby(ip, port);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of starMigration method, of class Lobby.
//     */
//    @Test
//    public void testStarMigration() {
//        System.out.println("starMigration");
//        Lobby instance = new Lobby();
//        instance.starMigration();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of migrate method, of class Lobby.
//     */
//    @Test
//    public void testMigrate() {
//        System.out.println("migrate");
//        Lobby instance = new Lobby();
//        instance.migrate();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setSelfServer method, of class Lobby.
//     */
//    @Test
//    public void testSetSelfServer() {
//        System.out.println("setSelfServer");
//        Lobby instance = new Lobby();
//        instance.setSelfServer();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of openServer method, of class Lobby.
//     */
//    @Test
//    public void testOpenServer() {
//        System.out.println("openServer");
//        Lobby instance = new Lobby();
//        instance.openServer();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of launchGame method, of class Lobby.
//     */
//    @Test
//    public void testLaunchGame() {
//        System.out.println("launchGame");
//        Lobby instance = new Lobby();
//        instance.launchGame();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addConnection method, of class Lobby.
//     */
//    @Test
//    public void testAddConnection() {
//        System.out.println("addConnection");
//        Socket client = null;
//        Lobby instance = new Lobby();
//        instance.addConnection(client);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of serializeModel method, of class Lobby.
//     * @throws java.lang.Exception
//     */
//
//
//
    @Test
    public void testSerializeModel() throws Exception {
        System.out.println("serializeModel");
        FieldModel m = new FieldModel(5);
        Controller c = new Controller();
        c.model = m;
        Lobby l = new Lobby();
        l.setController((OnlineController) c);
        System.out.println(l.serializeModel());

        fail("The test case is a prototype.");
    }

    /**
     * Test of serializeLobby method, of class Lobby.
     */
    @Test
    public void testSerializeLobby() throws Exception {
        System.out.println("serializeLobby");
        Lobby instance = new Lobby();
        instance.setSelfServer();
        String expResult = "";
        System.out.println(instance.serializeLobby());
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLobby method, of class Lobby.
     */
    @Test
    public void testGetLobby() {
        System.out.println("getLobby");
        FieldModel m = new FieldModel(5);
        Controller c = new Controller();
        c.model = m;
        Lobby l = new Lobby();
        l.setController((OnlineController) c);
        String json = "";
        try {
            json = l.serializeLobby();
        } catch (JsonProcessingException ex) {
            Logger.getLogger(LobbyTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Lobby instance = new Lobby();
        instance.getLobby(json);
        String toTest = "";
        try {
            toTest = instance.serializeLobby();
        } catch (JsonProcessingException ex) {
            Logger.getLogger(LobbyTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(json, toTest);
    }

//    /**
//     * Test of getModel method, of class Lobby.
//     */
//    @Test
//    public void testGetModel() {
//        System.out.println("getModel");
//        ObjectMapper mapper = new ObjectMapper();
//        FieldModel m1 = new FieldModel(5);
//        Controller c1 = new Controller();
//        c1.model = m1;
//        Lobby l1 = new Lobby();
//        l1.setController(c1);
//        String json ="";
//        try {
//            json = l1.serializeModel();
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(LobbyTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        FieldModel m2 = new FieldModel(3);
//        Controller c2 = new Controller();
//        c2.model = m2;
//        Lobby instance = new Lobby();
//        instance.setController(c2);
//        instance.getModel(json);
//        
//        String toTest = "";
//        try {
//            toTest = instance.serializeModel();
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(LobbyTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("json :");
//        System.out.println(json);
//        System.out.println("toTest :");
//        System.out.println(toTest);
//        assertEquals(json,toTest);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    @Test
    public void testSerializeModelBinary() throws IOException {
        System.out.println("serializeModelBinary");
        FieldModel m = new FieldModel(5);
        Controller c = new Controller();
        c.model = m;
        Lobby l = new Lobby();
        l.setController((OnlineController)c);
        l.serializeModelBinary();

        fail("The test case is a prototype.");
    }

    @Test
    public void testGetLobbyBinary() throws IOException {
        System.out.println("serializeModelBinary");
        FieldModel m = new FieldModel(5);
        Controller c = new Controller();
        c.model = m;
        Lobby l = new Lobby();
        l.setController((OnlineController) c);
        byte[] b1 = l.serializeModelBinary();

        FieldModel m2 = new FieldModel(6);
        Controller c2 = new Controller();
        c2.model = m2;
        Lobby l2 = new Lobby();
        l2.setController((OnlineController) c2);
        l2.getModelBinary(b1);
        System.out.println("taille d'un model en binaire : " + b1.length + " octets");
        byte[] b2 = l2.serializeModelBinary();
        Assert.assertArrayEquals(b2, b1);
    }
}
