import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Powerup that makes all 3D printers for its respective player 1.5x more efficient.
 * 
 * @author Jonathan Zhao
 * @version November 2022
 */
public class ExtraExpensiveFilament extends Powerup
{
    private ArrayList<Printer3D> myPrinters;
    private BuildingRow printerRow;
    public ExtraExpensiveFilament(Player origin) {
        super(origin);
        duration = (int)(60 * 3.5); // 3.5 seconds in acts
        myPrinters = new ArrayList<Printer3D>();
        printerRow = origin.getBuildingRows().get(Printer3D.class);
        getImage().clear(); // empty image
    }
    
    public void addedToWorld(World w) {
        // grab all 3d printers that belong to player and upgrade them
        ArrayList<Printer3D> allPrinters = (ArrayList<Printer3D>)getWorld().getObjects(Printer3D.class);
        for (Printer3D printer : allPrinters) {
            if (printer.getPlayer() == origin) {
                myPrinters.add(printer);
                printer.upgradeFilament();
            }
        }
        actCount = duration;
    }
    
    public void act() {
        actCount--;
        if(actCount%20 == 0) {  // add sparkle every 20 acts
            addSparkle();
        }
        if (actCount == 0) {
            for (Printer3D printer : myPrinters) {
                printer.removeUpgradedFilament();
            }
            getWorld().removeObject(this);
        }
    }
    /**
     * Add one sparkle
     */
    public void addSparkle() {  // sparkle effect on top of building row
        Sparkle sparkle = new Sparkle(Greenfoot.getRandomNumber(60) + 90, printerRow, ExtraExpensiveFilament.class);
        getWorld().addObject(sparkle, 0, 0);
    }
}
