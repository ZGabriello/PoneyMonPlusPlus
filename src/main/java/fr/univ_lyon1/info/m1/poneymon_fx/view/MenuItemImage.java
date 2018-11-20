package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Classe représentant un item dans le menu.
 *
 */
public class MenuItemImage extends HBox {

    static final Font FONT = Font.font("", FontWeight.BOLD, 30);

    private MiniPoney p1 = new MiniPoney("orange");
    private MiniPoney p2 = new MiniPoney("orange");
    private Text text;
    Text typePoney;
    private Runnable script;

    private static class MiniPoney extends ImageView {

        public MiniPoney(String color) {
            super(new Image("assets/pony-" + color + "-rainbow.gif", 100, 0, true, true));
        }
    }

    private static class ImagePoney extends ImageView {

        public ImagePoney(String color) {
            super(new Image("assets/pony-" + color + "-running.gif", 150, 0, true, true));
        }
    }

    /**
     * Constructeur du MenuItem.
     *
     * @param name texte affiché dans le MenuItem
     */
    public MenuItemImage(String name, String type) {
        super(15);
        final ImagePoney p3 = new ImagePoney(type);
        typePoney = new Text(type);
        setAlignment(Pos.CENTER);

        text = new Text(name);
        text.setFont(FONT);
        text.setEffect(new GaussianBlur(2));

        getChildren().addAll(p1, p3, text, p2);
        setActive(false);
    }

    /**
     * Donne un effet spécial à un item lorsqu'il a le focus.
     *
     * @param b booléan servant à activer ou désactiver l'effet
     */
    public void setActive(boolean b) {
        p1.setVisible(b);
        p2.setVisible(b);
        text.setFill(b ? Color.WHITE : Color.GREY);
    }

    public void setOnActivate(Runnable r) {
        script = r;
    }

    /**
     * On appelle le script quand l'item est activé.
     */
    public void activate() {
        if (script != null) {
            script.run();
        }
    }

    public void setText(String insideText) {
        text.setText(insideText);
    }

    public String getText() {
        return text.getText();
    }

    public String getTextTypePoney() {
        return typePoney.getText();
    }
}
