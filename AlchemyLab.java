import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Alchemy labs produce between 400 to 500 cookies per 2 seconds.
 * 
 * @author Patrick Hu 
 * @version November 2022
 */
public class AlchemyLab extends Building
{
    /**
     * @param player            The player who has purchased an AlchemyLab
     */
    public AlchemyLab(Player player) {
        super(player);
        animationSize = 5;
        scale = 0.52;
    }
    
    public void act() {
        super.act();
        if (actCount == actMark) {
            produce(400, 500);    
            actMark = getNextActMark(2, 2);
        }
    }
}
