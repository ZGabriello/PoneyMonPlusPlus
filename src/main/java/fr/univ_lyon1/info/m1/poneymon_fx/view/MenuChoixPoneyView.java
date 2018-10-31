package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Vue du menu choix poney.
 *
 */
public class MenuChoixPoneyView extends View {
    Controller controller;
    FieldModel model;
    MenuView mv;

    private List<MenuItemImage> menuItems = new ArrayList<>();
    int currentItem = 0;
    String typePoney;


    final String[] descriptifPoney = {"Poney normal orange",
        "Poney normal bleu",
        "Poney normal jaune",
        "Poney normal vert",
        "Poney normal violet",
    };

    final String[] couleurPoney = {"orange",
        "blue",
        "yellow",
        "green",
        "purple",
    };


    /**
     * Constructeur du Menu des paramètres.
     *
     * @param c Contrôleur
     * @param w largeur de la vue
     * @param h hauteur de la vue
     */
    public MenuChoixPoneyView(Controller c, int w, int h) {
        setPrefSize(w, h);

        controller = c;
        mv = new MenuView(c, w, h);

        createContent();
        setOnKeyPressedEvent();
    }

    private void createContent() {
        for (int i = 0; i < descriptifPoney.length; i++) {
            final String myPoneyChoice = couleurPoney[i];
            MenuItemImage poneyItem = new MenuItemImage(descriptifPoney[i], couleurPoney[i]);
            poneyItem.setOnActivate(() -> setTypePoney(myPoneyChoice));
            menuItems.add(poneyItem);
        }

        MenuItemImage retourItem = new MenuItemImage("Back", "orange");
        retourItem.setOnActivate(() -> controller.menuFromGame());
        menuItems.add(retourItem);

        Node title = mv.createTitle("Poneymon");

        VBox container = new VBox(0, title);

        VBox.setMargin(title, new Insets(0, 0, 110, 0));

        container.getChildren().addAll(menuItems);
        container.setAlignment(Pos.CENTER);
        getChildren().add(container);

        getMenuItem(0).setActive(true);

        setBackground(new Background(
                new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    private MenuItemImage getMenuItem(int index) {
        return menuItems.get(index);
    }

    private void setOnKeyPressedEvent() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.UP && currentItem > 0) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true);
                }

                if (e.getCode() == KeyCode.DOWN && currentItem < menuItems.size() - 1) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(++currentItem).setActive(true);
                }

                if (e.getCode() == KeyCode.ENTER) {
                    getMenuItem(currentItem).activate();
                }
            }
        });
    }

    public String getTypePoney() {
        return this.typePoney;
    }

    public void setTypePoney(String typePoney) {
        this.typePoney = typePoney;
        System.out.println(getTypePoney());
    }
}
