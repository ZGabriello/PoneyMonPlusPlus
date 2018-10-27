package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.notification.TrackInitializationNotification;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Classe gérant un terrain.
 *
 */
public class TrackModel extends Observable {
    String name;
    
    Line lineBegin = null;
    HashMap<Integer, Line> lines = new HashMap<>();
    HashSet<LanePart> laneParts = new LinkedHashSet<>();
    
    double minX;
    double minY;
    double maxX;
    double maxY;
    
    double width;
    double height;
    
    /**
     * Constructeur de TrackModel.
     */
    public TrackModel(String filename) {
        minX = Double.POSITIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
        maxX = Double.NEGATIVE_INFINITY;
        maxY = Double.NEGATIVE_INFINITY;
        
        importTrack(filename);
    }
    
    /**
     * Charge le Track présent dans le fichier filename.
     */
    public void importTrack(String filename) {
        try {
            File f = new File("src/main/resources/tracks/" + filename);
            Scanner scanner = new Scanner(f);

            String object = null;
            
            while (true) {
                try {
                    object = scanner.next();
                    if (object.equals("line") || object.equals("lineBegin")) {
                        loadLine(object, scanner);
                    } else if (object.equals("lanePart")) {
                        loadLanePart(scanner);
                    }
                } catch (NoSuchElementException exception) {
                    break;
                }
            }

            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Le fichier n'a pas été trouvé");
        }
        
        width = maxX - minX;
        height = maxY - minY;
        constructTrack();
    }
    
    /**
     * Charge une Line.
     */
    private void loadLine(String object, Scanner scanner) {
        int id = scanner.nextInt();
        double x = scanner.nextDouble();
        double y = scanner.nextDouble();
        int multiple = scanner.nextInt();
        int zIndex = scanner.nextInt();
        int nbLanes = scanner.nextInt();

        Line line = new Line(id, x, y, multiple, zIndex, nbLanes);
        if (object.equals("lineBegin")) {
            if (lineBegin == null) {
                lineBegin = line;
            } else {
                System.err.println("Ce circuit comporte plus d'une ligne de départ");
            }
        }

        lines.put(id, line);
    }
    
    /**
     * Charge une LanePart.
     */
    private void loadLanePart(Scanner scanner) {
        int beginLineId = scanner.nextInt();
        Line beginLine = lines.get(beginLineId);
        int beginLaneId = scanner.nextInt();

        int endLineId = scanner.nextInt();
        Line endLine = lines.get(endLineId);
        int endLaneId = scanner.nextInt();
        
        LanePartBuilder lpb = new LanePartBuilder();
        LanePart lp = lpb.buildLanePart(beginLine, beginLaneId, endLine, endLaneId);

        minX = min(minX, lp.getMinX());
        minY = min(minY, lp.getMinY());
        maxX = max(maxX, lp.getMaxX());
        maxY = max(maxY, lp.getMaxY());

        laneParts.add(lp);
    }
    
    /**
     * Classe chargée de calculer les tailles des voies, les lignes voisines et
     * autres données après importation des lignes et des voies.
     *
     */
    private void constructTrack() {
        Line lineBegin = null;
        Line lineEnd = null;
        
        for (LanePart lanePart : laneParts) {
            //lineBegin = lines.get()
        }
    }
    
    /**
     * Initialisation des observeurs du modèle du poney.
     */
    @Override
    public void addObserver(Observer obs) {
        super.addObserver(obs);
        
        setChanged();
        notifyObservers(new TrackInitializationNotification(this));
    }
    
    public HashMap<Integer, Line> getLines() {
        return lines;
    }
    
    public HashSet<LanePart> getLaneParts() {
        return laneParts;
    }
    
    public double getMinX() {
        return minX;
    }
    
    public double getMinY() {
        return minY;
    }
    
    public double getMaxX() {
        return maxX;
    }
    
    public double getMaxY() {
        return maxY;
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
}