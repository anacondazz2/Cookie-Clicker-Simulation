import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * CookieMonster has entered the scene! Eats up a chunk of the opponent's cookies
 * The amount of cookies that the CookieMonster eats depends on the player's level.
 * 
 * @author Caden Chan, Jonathan Zhao
 * @version 2022.11.22
 */
public class CookieMonster extends Sabotage
{
    private int actCount;
    private double scale = 0.8;
    private int percentToTake;
    private int takenCookies;
    private int animationSize, animationIndex;
    
    public CookieMonster(Player origin) {
        super(origin);
        actCount = 0;
        duration = 60 * 4;
        animationSize = 2;
        animationIndex = 0;
        percentToTake = getRandomNumberInRange(10, 25);
    }
    
    public void addedToWorld(World w) {
        super.addedToWorld(w);
        takenCookies = target.getCookieCount() * (int)((double)percentToTake / 100);
        target.changeCookieCount(-takenCookies);
        
        // place it next to its player's cookie
        setLocation(target.getX() - 155, target.getY() - 150);
    }
    
    public void act() {
        if(actCount == duration) {
            getWorld().removeObject(this);
        }   
        actCount++;
        animate();
    }
    
    public void animate() {
        if (actCount % 10 == 0) {
            setImage("./gifs/cookie-monster/" + animationIndex + ".png");
            getImage().scale((int)(getImage().getWidth() * scale), (int)(getImage().getHeight() * scale));
            animationIndex++;
            animationIndex %= animationSize;
        }
    }
}
