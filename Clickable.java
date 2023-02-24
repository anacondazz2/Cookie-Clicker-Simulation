import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An object that can be clicked
 * 
 * @author Eddie Zhuang
 * @version November 2022
 */
public abstract class Clickable extends Actor
{
    /**
     * Method called when a clicker clicks on this object
     * 
     * @param player The player clicking this object
     */
    public void click(Player player) {}
    
    /**
     * Gets a random number in the range from `start` to `end` inclusive.
     */
    protected int getRandomNumberInRange(int start, int end) {
       int a = Greenfoot.getRandomNumber(end - start + 1);
       return start + a;
    }
}
