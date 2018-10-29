package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public abstract class Processor implements Runnable {

    PrintWriter writer;
    BufferedInputStream reader;
    //HEADERS 
    static final char H_COMMAND = 'c';
    static final char H_DATA = 'd';
    static final char H_INPUT = 'i';
    //---
    boolean connexionFermeeDemande = false;
    boolean connexionFermee = false;

    String read() throws IOException {
        String reponse = "";
        int stream;
        byte[] b;
        b = new byte[4096];
        stream = reader.read(b);
        if (stream>0){
            reponse = new String(Arrays.copyOf(b, stream), "ISO-8859-1");
        }
        
        
        return reponse;
    }

    void sendCommand(String s) {
        System.out.println("sending command...");
        String toSend = H_COMMAND + s;
        writer.write(toSend);
        writer.flush();
    }

    void sendInput(String s) {
        System.out.println("sending input...");
        String toSend = H_INPUT + s;
        writer.write(toSend);
        writer.flush();
    }

    void sendData(String s) {

        String toSend = H_DATA + s;
        writer.write(toSend);
        writer.flush();
    }

    void parseMessage(String message) {
        //System.out.println("parsing message");
        switch (message.charAt(0)) {
            case H_COMMAND:
                parseCommand(message.substring(1));
                break;
            case H_INPUT:
                System.out.println("tis input");
                parseInput(message.substring(1));
                break;
            case H_DATA:
                parseData(message.substring(1));
                break;

        }
    }

    void parseCommand(String substring) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void parseInput(String substring) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void parseData(String substring) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
