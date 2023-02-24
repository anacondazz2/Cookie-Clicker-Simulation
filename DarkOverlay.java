import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A black background with its opacity reduced to act as a dark overlay.
 * 
 * @author Patrick Hu
 * @version November 2022
 */
public class DarkOverlay extends Actor
{
    public DarkOverlay() {
        setImage("./black.png");
        getImage().scale(1200, 800);
        getImage().setTransparency(210);
    }
}
