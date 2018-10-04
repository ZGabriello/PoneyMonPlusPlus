package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class Lobby {
    List<String> ips = new ArrayList<String>();
    String usedIp;
    String hostIp;
    Server server;
    Controller controller;
    public Lobby() throws UnknownHostException{
        usedIp = InetAddress.getLocalHost().getHostAddress();
        ips.add(usedIp);
    }
    
    public void setController(Controller c){
        this.controller = c;
    }
    
    public String getUsedIp(){
        return usedIp;
    }
    
    public void setHost(String ip){
        this.hostIp=  ip;
    }
    
    public void starMigration(){
        //TODO
    }
    
    void migrate(){
        //TODO
    }

    
    void setSelfServer(){
        hostIp = this.usedIp;
        server = new Server(this.usedIp,9000);
    }
    
    
    
    public void launchGame(){
        if (hostIp == null ? usedIp == null : hostIp.equals(usedIp)){
           //TODO lancer la partie. 
        }
    }
}
