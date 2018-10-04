/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alex
 */
public abstract class Processor implements Runnable {
    PrintWriter writer;
    BufferedInputStream reader;
    public static final Map<String, Byte> SERVER_HEADERS;
    static{
        Map<String, Byte> aMap = new HashMap<>();
        aMap.put("STRINGCOMMAND",(byte) 1);
        aMap.put("DATA", (byte) 2);
        // à compléter
        SERVER_HEADERS = Collections.unmodifiableMap(aMap);
    }        
            
            
            
    String read() throws IOException {
        String reponse;
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        reponse = new String(b, 0, stream);

        return reponse;
    }

    void sendCommand(String byteCode) {
        System.out.println("handling command...");
        writer.write(byteCode);
        writer.flush();
    }
}
