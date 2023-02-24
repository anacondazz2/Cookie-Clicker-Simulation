import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A rectangular bar which slowly shrinks vertically over time.
 * <h4>Used exclusively by BuyButton to:</h4>
 * <ul>
 *  <li>Display which Player clicked which button, based on colour</li>
 *  <li>Display how long a Powerup lasts / how much time it has left</li>
 * </ul>
 * @author Caden Chan 
 * @version 2022.11.15
 */
public class CooldownBar extends Actor
{
    private int width, height;
    private double percent, rate;  // percent = height of the bar, rate = how fast should the bar's height change
    private final int TRNP = 100; // transparency
    private GreenfootImage image;
    private Color colour;
    /**
     * @param width         width of the cooldown bar
     * @param height        height of the cooldown bar 
     * @param colour        cooldown bar colour overlay
     * @param duration      how long the cooldown effect is, in seconds
     */
    public CooldownBar(int width, int height, Color colour, double duration) {
        this.width = width;
        this.height = height;
        rate = 100.0/(duration); // by how much the `percent` variable should change per act
        this.colour = colour;
        image = new GreenfootImage(width, height);
    }
    public void addedToWorld(World w) {
        percent = 100.0;
        drawBar();
    }
    public void act()
    {
        if(percent <= 0) {  // if percent has reached 0, cooldownbar is removed
            getWorld().removeObject(this);
            return;
        }
        drawBar();  // draw the bar with new percent value
        percent -= rate;
    }
    /**
     * Draw bar based on value of <code>percent</code>
     */
    public void drawBar() {
        image = new GreenfootImage(width, height);
        image.setTransparency(TRNP);
        image.setColor(colour);  // set to colour of the player that clicked this
        int newH = height * (int)percent / 100;
        image.fillRect(0, 0+ height-newH, width, newH);
        setImage(image);
    }
    /**
     * Set duration of this CooldownBar
     * @param d         Set duration to `d` acts
     */
    public void setDuration(int d) {
        rate = 100.0/d;
    }
}
