import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Show preview of Grandma objects
 * 
 * @author Patrick Hu, Eddie Zhuang, Caden Chan
 * @version November 2022
 */
public class PreviewGrandma extends PreviewActor
{
    int animationIndex, animationSize, actCount;
    double scale;
    public PreviewGrandma() {
        animationSize = 5;
        scale = 1;
        actCount = 0;
        animate();
    }
    public void act() {
        animate();
        actCount++;
    }
    /**
     * Animate the grandma
     */
    public void animate() {
        if (actCount % 20 == 0) {
            setImage("./gifs/grandma/" + animationIndex + ".png");
            getImage().scale((int)(getImage().getWidth() * scale), (int)(getImage().getHeight() * scale));
            animationIndex++;
            animationIndex %= animationSize;
        }
    }
}
