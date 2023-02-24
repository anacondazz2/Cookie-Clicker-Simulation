import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Players can gain an edge on one another by purchasing subclasses of Powerup.
 * - Normal Powerups will help boost the Player's cookie output
 * - Powerups classified as "Sabotages" are activated to crumble each-others' cookie-making rates 
 * 
 * @author Caden Chan, Jonathan Zhao
 * @version 2022.11.02
 */
public abstract class Powerup extends SuperSmoothMover
{
    protected int duration;  //duration, in acts
    protected int actCount;
    protected Player origin;  // Player that activated the Powerup
    
    public Powerup(Player origin) {
        this.origin = origin;
        actCount = 0;
    }
    
    /**
     * @return int          How long the powerup lasts, in acts
     */
    public int getDuration() {
        return duration;
    }
    
    /**
     * @return Player       The player that activated this powerup
     */
    public Player getOriginPlayer() {
        return origin;
    }
}
