/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Alex
 */
class OnlineClientView extends View{
    Controller controller;
    private List<HBox> menuItems;
    
    public OnlineClientView(Controller c, int w, int h) {
        setPrefSize(w,h);
        this.controller=c;
        createContent();
    }
    
    private void createContent(){
        Label label1 = new Label("IP:");
        TextField ip = new TextField ();
        HBox hb1 = new HBox();
        hb1.getChildren().addAll(label1, ip);
        hb1.setSpacing(10);
        Label label2 = new Label("Port:");
        TextField port = new TextField ();
        HBox hb2 = new HBox();
        hb2.getChildren().addAll(label2, port);
        hb2.setSpacing(10);
        
        hb1.setAlignment(Pos.CENTER);
        hb2.setAlignment(Pos.CENTER);
        MenuItem validate = new MenuItem("join this lobby");
        validate.setOnActivate(() -> controller.LobbyViewFromClient(ip.getText(),port.getText()));
        menuItems = Arrays.asList(
                hb1,
                hb2,
                validate);
        validate.setActive(true);
        
        VBox container = new VBox(10);

        container.getChildren().addAll(menuItems);
        container.setAlignment(Pos.CENTER);
        getChildren().add(container);

        setBackground(new Background(
                new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
    }
    
    
}
