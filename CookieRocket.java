import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An image of a cookie rocket that will accelerate upwards, signifying
 * the victory of the winning player!
 * 
 * @author Patrick Hu 
 * @version November 2022
 */
public class CookieRocket extends SuperSmoothMover
{    
    private int actCount;
    private double scale;
    private double speed;
    private double acceleration;
    private Player player; // the player who won
    protected static GreenfootSound launchSound = new GreenfootSound("./sounds/rocket-launch.wav");
    
    /**
     * @param player            The player who has won
     */
    public CookieRocket(Player player) {
        this.player = player;
        actCount = 0;
        scale = 3;
        speed = 0.1;
        acceleration = 0.22;
        
        setImage("./cookie-rocket.png");
        getImage().scale((int)(getImage().getWidth() * scale), (int)(getImage().getHeight() * scale));
        setRotation(-90);
        launchSound.setVolume(60);
        launchSound.play();
    }
    
    public void addedToWorld(World w) {
        CookieWorld cw = (CookieWorld)w;
        // apply dark overlay to rest of world
        DarkOverlay overlay = new DarkOverlay();
        cw.addObject(overlay, cw.getWidth() / 2, cw.getHeight() / 2);
        cw.getBgMusic().stop();
    }
    
    public void act() {
        actCount++;
        if (actCount < 180) { // first 3 seconds
            move(speed); // move slowly
        }
        else {
            accelerate();   
        }
        checkEdges();
    }
    
    /**
     * Accelerates the rocket upwards.
     */
    public void accelerate() {
        speed += acceleration;
        move(speed);
    }
    /**
     * Check to see if the rocket has reached the top of the world. <br>
     * Show the end screen.
     */
    public void checkEdges() {
        // if at top of world
        if (getY() <= 0) {
            launchSound.stop();
            Greenfoot.setWorld(new EndWorld(player));
        }
    }
    
    public void stopped() {
        launchSound.pause();
    }
}
