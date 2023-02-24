import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The most powerful building that produces 9000 cookies every 2-3 seconds.
 * 
 * @author Patrick Hu
 * @version November 2022
 */
public class CookieGod extends Building
{
    /**
     * @param player            The player who has purchased a Cookie God
     */
    public CookieGod(Player player) {
        super(player);
        animationSize = 7;
        scale = 0.7;
    }
    
    public void act() {
        super.act();
        if (actCount == actMark) {
            produce(9000, 9000);
            actMark = getNextActMark(2, 3);
        }
    }
}
