import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Player's wifi becomes destabilized, and their clickers become debilitated for 3-5 seconds
 * 
 * @author Caden Chan
 * @version 2022.11.19
 */
public class Lag extends Sabotage
{
    private int blinkCount, blinkDuration; // control the blinking of the no-wifi symbol
    private GreenfootImage image;
    /**
     * @param origin            The Player who activated the Lag Sabotage.
     */
    public Lag(Player origin) {
        super(origin);
        duration = (Greenfoot.getRandomNumber(3) + 3)*60; // lasts 3-5 seconds
        image = new GreenfootImage("effect/lag0.png");
        setImage(image);
        // Initialize image-blinking variables
        blinkCount = 0;
        blinkDuration = 20;
        actCount = blinkDuration;
    }
    /**
     * Show no-wifi symbol on top of [Player target]'s Cookie, and lag their clickers
     * @param w
     */
    public void addedToWorld(World w) {
        super.addedToWorld(w);
        target.lagClickers(duration/60);  // cause opponents' clickers to lag out. target = opponnent
        setLocation(target.getCookie().getX(), target.getCookie().getY()-20);
    }
    public void act() {
        actCount++;
        // Show for [50-blinkDuration] acts, hide for [blinkDuration] acts
        if(actCount % 50 == 0) { // Blink
            blinkCount = blinkDuration;
            image.setTransparency(0);
        }
        if(blinkCount > 0) {
            blinkCount --;
            if(blinkCount == 0) {  // Show
                image.setTransparency(255);
            }
        }
        if(actCount == duration + duration%50) { // add duration%50 to time removal of Lag object with blinking
            getWorld().removeObject(this);
        }
    }
}
