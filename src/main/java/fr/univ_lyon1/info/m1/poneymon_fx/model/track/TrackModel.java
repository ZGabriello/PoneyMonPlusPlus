package fr.univ_lyon1.info.m1.poneymon_fx.model.track;

import fr.univ_lyon1.info.m1.poneymon_fx.model.BoostItemModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ItemModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.Iterator;
import java.util.Locale;

/**
 * Classe gérant un terrain.
 *
 */
public class TrackModel {
    String name;
    
    Line beginLine = null;
    HashMap<Integer, Line> lines = new HashMap<>();
    HashSet<LanePart> laneParts = new LinkedHashSet<>();
    
    HashMap<Integer, ItemModel> items = new HashMap<>();
    
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
            Scanner scanner = new Scanner(f).useLocale(Locale.US); 


            String object = null;
            
            while (true) {
                try {
                    object = scanner.next();
                    if (object.equals("line") || object.equals("beginLine")) {
                        loadLine(object, scanner);
                    } else if (object.equals("lane")) {
                        loadLanePart(scanner);
                    } else if (object.equals("boostItem")) {
                        System.out.println("scanner dans boostItem");
                        loadItem(object, scanner);
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
        if (object.equals("beginLine")) {
            if (beginLine == null) {
                beginLine = line;
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
     * Charge un objet.
     */
    private void loadItem(String object, Scanner scanner) {
        
        int idLine = scanner.nextInt();      
        int idLane = scanner.nextInt();
        double position = scanner.nextDouble();
        
        ItemModel item;
        
        if (object.equals("boostItem")) {
            System.out.println("load boostItem");

            item = new BoostItemModel();        
        
            for (LanePart lane : laneParts) {
                if (lane.getBeginLaneId() == idLane) {
                    double distance = lane.getBeginLine().getLaneWidth() * position;
                    lane.setItems(distance, item);
                }
            }
        }

    }
    
    public HashMap<Integer, Line> getLines() {
        return lines;
    }
    
    public HashSet<LanePart> getLaneParts() {
        return laneParts;
    }
    
    public HashMap<Integer, ItemModel> getItems() {
        return items;
    }
    
    public Line getBeginLine() {
        return beginLine;
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