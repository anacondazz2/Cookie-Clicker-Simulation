import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Upgrade the Player's Cookie's level, to gain more cookies per click!
 * Plays a sparkling animation and changes the Cookie's appearance to represent an upgrade.
 * 
 * @author Caden Chan
 * @version 2022.11.19
 */
public class CookieUpgrade extends Powerup
{
    /**
     * @param origin            The Player that activated the CookieUpgrade
     */
    public CookieUpgrade(Player origin) {  
        super(origin);
        setImage(new GreenfootImage(10, 10)); // empty image
    }
    /**
     * Upgrade [Player origin]'s cookie
     * @param w
     */
    public void addedToWorld(World w) {
        upgrade(origin.getCookie());
    }
    public void act() {
        actCount ++;
        if(actCount %3 == 0) {  // add a sparkle every 3 acts
            addSparkle();
        }
        if(actCount == 120) {  // sparkling lasts for 120 acts
            getWorld().removeObject(this);
        }
    }
    /**
     * @param cookie            The cookie that is being levelled-up
     */
    private void upgrade(Cookie cookie) {
        cookie.levelUp();
    }
    /**
     * Add one sparkle
     */
    private void addSparkle() {  // add a sparkle effect on top of cookie
        Sparkle sparkle = new Sparkle(Greenfoot.getRandomNumber(60) + 90, origin.getCookie());
        getWorld().addObject(sparkle, 0, 0);
    }
}
