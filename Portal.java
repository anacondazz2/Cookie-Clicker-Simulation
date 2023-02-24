import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Transports 3000 - 4000 cookies from the Cookie-verse every 3 seconds.
 * 
 * @author Patrick Hu
 * @version November 2022
 */
public class Portal extends Building
{
    /**
     * @param player            The player who has purchased a Portal
     */
    public Portal(Player player) {
        super(player);
        animationSize = 7;
        scale = 0.6;
    }
    
    public void act() {
        super.act();
        if (actCount == actMark) {
            produce(3000, 4000);
            actMark = getNextActMark(3, 3);
        }
    }
}
