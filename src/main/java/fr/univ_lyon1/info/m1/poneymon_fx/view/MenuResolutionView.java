package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
 * Vue du menu resolution.
 *
 */
public class MenuResolutionView extends View {

    int idMainView;

    Controller controller;

    Map<Integer, Integer> hmResolution = new LinkedHashMap<>();
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();

    int currentItem = 0;

    final int widthSize;
    final int heightSize;

    final int[] widthHeight = {1024,
        768,
        1200,
        900,
        1280,
        960,
        1400,
        1050,
        1600,
        1200,
        1920,
        1080
    };

    /**
     * Constructeur du Menu des resolution.
     *
     * @param c Contrôleur
     * @param w largeur de la vue
     * @param h hauteur de la vue
     */
    public MenuResolutionView(int idMainView, Controller c, int w, int h) {
        this.idMainView = idMainView;
        setPrefSize(w, h);

        widthSize = w;
        heightSize = h;

        controller = c;

        createContent();
        setOnKeyPressedEvent();
    }

    private void createContent() {
        for (int i = 0; i < widthHeight.length; i = i + 2) {
            MenuItem item = new MenuItem("Resolution : "
                    + widthHeight[i]
                    + "x"
                    + widthHeight[i + 1]);
            final int newWidth = widthHeight[i];
            final int newHeight = widthHeight[i + 1];
            item.setOnActivate(() -> newResolution(newWidth, newHeight));
            menuItems.add(item);

        }

        MenuItem resolutionItem = new MenuItem("Resolution par default : "
                + widthSize
                + "x"
                + heightSize);
        resolutionItem.setOnActivate(() -> newResolution(widthSize, heightSize
        ));

        menuItems.add(resolutionItem);

        MenuItem retourItem = new MenuItem("Back");
        retourItem.setOnActivate(() -> controller.menuParameters());

        menuItems.add(retourItem);

        Node title = createTitle("Poneymon");

        VBox container = new VBox(10, title);

        VBox.setMargin(title, new Insets(0, 0, 110, 0));

        container.getChildren().addAll(menuItems);
        container.setAlignment(Pos.CENTER);
        getChildren().add(container);

        getMenuItem(0).setActive(true);

        setBackground(new Background(
                new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
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

    /**
     * Change la resolution par une nouvelle.
     */
    public final void newResolution(final int newWidth, final int newHeight) {
        setPrefSize(newWidth, newHeight);
        controller.changeResolution(idMainView, newWidth, newHeight);
    }
    
}
