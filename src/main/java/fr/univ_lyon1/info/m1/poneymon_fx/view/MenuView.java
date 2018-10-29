package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import java.util.Arrays;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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

import static javafx.scene.layout.Region.USE_PREF_SIZE;

/**
 * Vue du menu.
 *
 */
public class MenuView extends StackPane {
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
    new Color[] { LIGHTBLUE, LIGHTGREEN, LIGHTORANGE, LIGHTPURPLE, LIGHTYELLOW };
    
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

        menuItems = Arrays.asList(
                startGameItem,
                new MenuItem("Parameters"),
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
}
