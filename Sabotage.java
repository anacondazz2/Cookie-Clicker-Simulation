import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Sabotages are powerups which affect the opponent (target) of its activator (origin)
 * 
 * @author Caden Chan
 * @version 2022.11.02
 */
public abstract class Sabotage extends Powerup
{
    protected Player target;
    
    public Sabotage(Player origin) {
        super(origin);
    }
    
    public void addedToWorld(World w) {
        CookieWorld cw = (CookieWorld)w;
        this.target = cw.getOtherPlayer(origin);
    }
}
