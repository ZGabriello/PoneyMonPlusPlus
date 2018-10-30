package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import java.util.Arrays;
import java.util.List;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;



/**
 * Vue du menu choix poney.
 *
 */
public class MenuChoixPoneyView extends View {

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
    FieldModel model;

    private List<MenuItemImage> menuItems;
    int currentItem = 0;
    String typePoney;

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

        createContent();
        setOnKeyPressedEvent();
    }

    private void createContent() {
        MenuItemImage poneyOrangeItem = new MenuItemImage("Poney normal orange", "orange");
        poneyOrangeItem.setOnActivate(() -> setTypePoney(poneyOrangeItem.getTextTypePoney()));

        MenuItemImage poneyBlueItem = new MenuItemImage("Poney normal bleu", "blue");
        poneyBlueItem.setOnActivate(() -> setTypePoney(poneyBlueItem.getTextTypePoney()));

        MenuItemImage poneyYellowItem = new MenuItemImage("Poney normal jaune", "yellow");
        poneyYellowItem.setOnActivate(() -> setTypePoney(poneyYellowItem.getTextTypePoney()));

        MenuItemImage poneyGreenItem = new MenuItemImage("Poney normal vert", "green");
        poneyGreenItem.setOnActivate(() -> setTypePoney(poneyGreenItem.getTextTypePoney()));

        MenuItemImage poneyPurpleItem = new MenuItemImage("Poney normal violet", "purple");
        poneyPurpleItem.setOnActivate(() -> setTypePoney(poneyPurpleItem.getTextTypePoney()));

        MenuItemImage retourItem = new MenuItemImage("Back", "orange");
        retourItem.setOnActivate(() -> controller.menuFromGame());

        menuItems = Arrays.asList(
                poneyOrangeItem,
                poneyBlueItem,
                poneyYellowItem,
                poneyGreenItem,
                poneyPurpleItem,
                retourItem);

        Node title = createTitle("Poneymon");

        VBox container = new VBox(0, title);

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

    private MenuItemImage getMenuItem(int index) {
        return menuItems.get(index);
    }

    private void setOnKeyPressedEvent() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
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
        });
    }

    public String getTypePoney() {
        return this.typePoney;
    }

    public void setTypePoney(String typePoney) {
        this.typePoney = typePoney;
    }
}
