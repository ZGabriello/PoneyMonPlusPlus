package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

/**
 * Vue du menu.
 *
 */
public class MenuView extends View {
    Controller controller;

    private List<MenuItem> menuItems;
    int currentItem = 0;

    /**
     * Constructeur du Menu.
     * @param c Contrôleur
     * @param w largeur de la vue
     * @param h hauteur de la vue
     */
    public MenuView(Controller c, int w, int h) {
        setPrefSize(w, h);

        controller = c;

        createContent();
        setOnKeyPressedEvent();
    }

    private void createContent() {
        // On démarre par défaut une partie avec 5 poneys
        MenuItem startGameItem = new MenuItem("Start a game");
        startGameItem.setOnActivate(() -> controller.startGame("test", 3));

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnActivate(() -> Platform.exit());

        MenuItem parameters = new MenuItem("Parameters");
        parameters.setOnActivate(() ->
                controller.menuParameters());

        menuItems = Arrays.asList(
                startGameItem,
                parameters,
                exitItem);

        Node title = createTitle("Poneymon");
        VBox container = new VBox(10, title);

        VBox.setMargin(title, new Insets(0, 0, 110, 0));

        container.getChildren().addAll(menuItems);
        container.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);

        getMenuItem(0).setActive(true);

        setBackground(new Background(
                      new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        getChildren().add(container);
    }

    private MenuItem getMenuItem(int index) {
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
}
