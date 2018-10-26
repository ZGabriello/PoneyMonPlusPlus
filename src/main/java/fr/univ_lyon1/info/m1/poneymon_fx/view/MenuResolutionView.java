package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import java.util.Arrays;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Vue du menu resolution.
 *
 */
public class MenuResolutionView extends View {
    int idMainView;

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

    Map<Integer, Integer> hmResolution = new LinkedHashMap<>();
    private List<MenuItem> menuItems;

    int currentItem = 0;

    final int widthSize;
    final int heightSize;

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
        int[] width = {1024,
        1200,
        1280,
        1400,
        1600,
        1920};

        int[] height = {768,
        900,
        960,
        1050,
        1200,
        1440};
        for (int i = 0; i < width.length; i++) {
            final int newWidth = width[i];
            final int newHeight = height[i];
            hmResolution.put(width[i], height[i]);
        }

        MenuItem resolutionItem = new MenuItem("Resolution par default : "
                + widthSize
                + "x"
                + heightSize);
        resolutionItem.setOnActivate(() -> newResolution(widthSize,heightSize
        ));

        MenuItem resolution1 = new MenuItem("Resolution : "
                + width[0]
                + " x "
                + height[0]);
        resolution1.setOnActivate(() -> newResolution(
                width[0],
                height[0]));

        MenuItem resolution2 = new MenuItem("Resolution : "
                    + width[1]
                    + " x "
                    + height[1]);
        resolution2.setOnActivate(() -> newResolution(
                width[1],
                height[1]));

        MenuItem resolution3 = new MenuItem("Resolution : "
                + width[2]
                + " x "
                + height[2]);
        resolution3.setOnActivate(() -> newResolution(
                width[2],
                height[2]));

        MenuItem resolution4 = new MenuItem("Resolution : "
                + width[3]
                + " x "
                + height[3]);
        resolution4.setOnActivate(() -> newResolution(
                width[3],
                height[3]));

        MenuItem resolution5 = new MenuItem("Resolution : "
                + width[4]
                + " x "
                + height[4]);
        resolution5.setOnActivate(() -> newResolution(
                width[4],
                height[4]));

        MenuItem resolution6 = new MenuItem("Resolution : "
                + width[5]
                + " x "
                + height[5]);
        resolution5.setOnActivate(() -> newResolution(
                width[5],
                height[5]));

        MenuItem retourItem = new MenuItem("Back");
        retourItem.setOnActivate(() -> controller.menuParameters());

        menuItems = Arrays.asList(
                resolutionItem,
                resolution1,
                resolution2,
                resolution3,
                resolution4,
                resolution5,
                resolution6,
                retourItem);
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

    /**
     * Change la resolution par une nouvelle.
     */
    public final void newResolution(final int newWidth, final int newHeight) {
        setPrefSize(newWidth, newHeight);
        controller.changeResolution(idMainView, newWidth, newHeight);

        System.out.println(getWidthNew());
        System.out.println(getHeightNew());
    }

    public int getWidthNew() {
        return (int) getPrefWidth();
    }

    public int getHeightNew() {
        return (int) getPrefHeight();
    }

}
