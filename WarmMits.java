import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Warm mittens make clickers click faster (increases their production).
 * 
 * @author Jonathan Zhao
 * @version November 2022
 */
public class WarmMits extends Powerup
{
    private Clicker[] myClickers;
    
    /**
     * @param origin            The player who activated WarmMits
     */
    public WarmMits(Player origin) {
        super(origin);
        duration = (int)(60 * 3.5); // 3.5 seconds in acts
        myClickers = origin.getClickers();
        getImage().clear();
    }
    
    public void addedToWorld(World w) {
        for (Clicker clicker : myClickers) {
            clicker.wearMitten();
        }
    }
    
    public void act() {
        actCount++;
        if (actCount == duration) {
            for (Clicker clicker : myClickers) {
                clicker.takeOffMitten();
            }
            getWorld().removeObject(this);
        }
    }
}
