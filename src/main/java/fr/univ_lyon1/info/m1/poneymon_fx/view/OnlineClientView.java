/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import java.util.Arrays;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Alex
 */
class OnlineClientView extends View{
    static final Font FONT = Font.font("", FontWeight.BOLD, 50);

    /**
     * Couleurs récupérées de la crinière des poneys.
     */
    static final Color BLUE = Color.web("#3121B8");
    static final Color LIGHTBLUE = Color.web("#5F40DA");
    static final Color GREEN = Color.web("#2EC268");
    static final Color LIGHTGREEN = Color.web("#4CE58A");
    static final Color ORANGE = Color.web("#E62917");
    static final Color LIGHTORANGE = Color.web("#F46A39");
    static final Color PURPLE = Color.web("#A12BC8");
    static final Color LIGHTPURPLE = Color.web("#CB44EC");
    static final Color YELLOW = Color.web("#E47702");
    static final Color LIGHTYELLOW = Color.web("#FCB31F");

    Color[] titleColors =
    new Color[] { LIGHTBLUE,
        LIGHTGREEN,
        LIGHTORANGE,
        LIGHTPURPLE,
        LIGHTYELLOW };
    
    
    
    
    
    
    
    
    
    
    
    Controller controller;
    private List<MenuItem> menuItems;
    private int currentItem=0;
    
    private TextField ip,port;
    public OnlineClientView(Controller c, int w, int h) {
        setPrefSize(w,h);
        this.controller=c;
        createContent();
        setOnKeyPressedEvent();
    }
    
    private void createContent(){
        Label label1 = new Label("IP:");
        ip = new TextField ();
        HBox hb1 = new HBox();
        hb1.getChildren().addAll(label1, ip);
        hb1.setSpacing(10);
        Label label2 = new Label("Port:");
        port = new TextField ();
        HBox hb2 = new HBox();
        hb2.getChildren().addAll(label2, port);
        hb2.setSpacing(10);
        label1.setFont(FONT);
        label1.setEffect(new GaussianBlur(2));
        label2.setFont(FONT);
        label2.setEffect(new GaussianBlur(2));
        
        hb1.setAlignment(Pos.CENTER);
        hb2.setAlignment(Pos.CENTER);
        MenuItem validate = new MenuItem("join this lobby");
        validate.setOnActivate(() -> controller.LobbyViewFromClient(ip.getText(),port.getText()));
        MenuItem retourItem = new MenuItem("Back");
        retourItem.setOnActivate(() -> controller.menuFromGame());
        
        menuItems = Arrays.asList(
                validate,
                retourItem);
        validate.setActive(true);
        
        VBox container = new VBox(10);

        container.getChildren().add(hb1);
        container.getChildren().add(hb2);

        container.getChildren().addAll(menuItems);
        container.setAlignment(Pos.CENTER);
        getChildren().add(container);

        setBackground(new Background(
                new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
    }
    
    
    
    
    
    
    
    
    
    
    private Node createTitle(String title) {
        HBox letters = new HBox(0);
        letters.setAlignment(Pos.CENTER);

        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + "");
            letter.setFont(FONT);
            letter.setFill(titleColors[i % titleColors.length]);
            letters.getChildren().add(letter);

            TranslateTransition tt =
                    new TranslateTransition(Duration.seconds(2), letter);
            tt.setDelay(Duration.millis(i * 50));
            tt.setToY(-25);
            tt.setAutoReverse(true);
            tt.setCycleCount(TranslateTransition.INDEFINITE);
            tt.play();
        }

        letters.setEffect(new GaussianBlur(2));

        return letters;
    }

    private MenuItem getMenuItem(int index) {
        return menuItems.get(index);
    }
    
    private void setOnKeyPressedEvent() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.UP) {
                    
                    
                    if (currentItem > 0) {
                        getMenuItem(currentItem-1).requestFocus();
                        getMenuItem(currentItem).setActive(false);
                        getMenuItem(--currentItem).setActive(true);
                    }
                }

                if (e.getCode() == KeyCode.DOWN) {
                    
                    if (currentItem < menuItems.size() - 1) {
                        getMenuItem(currentItem+1).requestFocus();
                        getMenuItem(currentItem).setActive(false);
                        getMenuItem(++currentItem).setActive(true);
                    }
                }

                if (e.getCode() == KeyCode.ENTER) {
                    getMenuItem(currentItem).requestFocus();
                    getMenuItem(currentItem).activate();
                }
            }
        });
    }
    
}
