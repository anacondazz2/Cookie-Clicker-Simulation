import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Superclass for all Effects.
 * @author Mr. Cohen, Caden Chan
 * @version November 2022
 */
public class Effect extends Actor
{
    protected GreenfootImage image;
    protected int duration, fadeLen;
    
    public static void fade (GreenfootImage image, int timeLeft, int fadeTime) {
        fade(image, timeLeft, fadeTime, 255);
    }
    public static void fade(GreenfootImage image, int timeLeft, int fadeTime, int startTransparency) {
        double percent = timeLeft / (double)fadeTime;
        // Transparency goes from [startTransparency to 0
        int newTransparency = (int)(percent * startTransparency);
        image.setTransparency(newTransparency);
    }
}