package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Vue du menu.
 *
 */
public class MenuControlesView extends StackPane {

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

    Color[] titleColors
            = new Color[]{LIGHTBLUE, LIGHTGREEN, LIGHTORANGE, LIGHTPURPLE, LIGHTYELLOW};

    Controller controller;
    GameView gv;

    Map<String, KeyCode> hmControles = new LinkedHashMap<>();

    private List<MenuItem> menuItems = new ArrayList<MenuItem>();
    int currentItem = 0;
    boolean waitingForKey = false;

    /**
     * Constructeur du Menu de controles.
     *
     * @param c Contrôleur
     * @param w largeur de la vue
     * @param h hauteur de la vue
     */
    public MenuControlesView(Controller c, int w, int h) {
        setPrefSize(w, h);

        controller = c;

        createContent();
        setOnKeyPressedEvent();
    }

    /**
     * Récupère le Menu de controles.
     *
     * @return this le menu controles
     */
    public MenuControlesView getMenuControles() {
        return this;
    }

    private void createContent() {

        String[] defaultControlName = {"pouvoirNian1",
            "pouvoirNian2",
            "pouvoirNian3",
            "pouvoirNian4",
            "pouvoirNian5"};

        KeyCode[] defaultKeyCode = {KeyCode.NUMPAD1,
            KeyCode.NUMPAD2,
            KeyCode.NUMPAD3,
            KeyCode.NUMPAD4,
            KeyCode.NUMPAD5};

        //liste des controles
        for (int i = 0; i < defaultControlName.length; i++) {
            hmControles.put(defaultControlName[i], defaultKeyCode[i]);
            MenuItem temp = new MenuItem(hmControles.keySet().toArray()[i].toString()
                    + " : "
                    + hmControles.values().toArray()[i]);
            temp.setOnActivate(() -> waitKeyCode(temp));
            menuItems.add(temp);
        }

        MenuItem retourItem = new MenuItem("Retour");
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

    private Node createTitle(String title) {
        HBox letters = new HBox(0);
        letters.setAlignment(Pos.CENTER);

        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + "");
            letter.setFont(FONT);
            letter.setFill(titleColors[i % titleColors.length]);
            letters.getChildren().add(letter);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(2), letter);
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
                if (!waitingForKey) {
                    if (e.getCode() == KeyCode.UP) {
                        if (currentItem > 0) {
                            getMenuItem(currentItem).setActive(false);
                            getMenuItem(--currentItem).setActive(true);
                        }
                    }

                    if (e.getCode() == KeyCode.DOWN) {
                        if (currentItem < menuItems.size() - 1) {
                            getMenuItem(currentItem).setActive(false);
                            getMenuItem(++currentItem).setActive(true);
                        }
                    }

                    if (e.getCode() == KeyCode.ENTER) {
                        getMenuItem(currentItem).activate();
                    }
                }
            }
        });
    }

    /**
     * Attend une nouvelle touche et met à jour le texte de l'item.
     *
     */
    public void waitKeyCode(MenuItem m) {
        waitingForKey = true;
        final String controlName = m.getText().substring(0, 12);
        m.setText(controlName + " : " + "Appuyer sur une touche");
        MenuControlesView parent = this;
        this.addEventHandler(KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        changeKeyCode(m, e.getCode());
                        parent.removeEventHandler(KeyEvent.KEY_PRESSED, this);
                        waitingForKey = false;
                    }
                });
    }

    /**
     * Chenge la touche par une nouvelle touche.
     */
    public final void changeKeyCode(final MenuItem mi, KeyCode newKeyCode) {
        final String controlName = mi.getText().substring(0, 12);
        hmControles.put(controlName, newKeyCode);

        /* affichage des keyCode contenu dans le hashmap */
        System.out.println(hmControles.values().toArray()[0]);
        System.out.println(hmControles.values().toArray()[1]);
        System.out.println(hmControles.values().toArray()[2]);
        System.out.println(hmControles.values().toArray()[3]);
        System.out.println(hmControles.values().toArray()[4]);

        mi.setText(controlName + " : " + newKeyCode.toString());
    }

}
