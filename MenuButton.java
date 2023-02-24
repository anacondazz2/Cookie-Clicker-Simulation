import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Superclass for buttons used in the menu screen, clickable by the user. (I/O buttons).
 * 
 * @author Caden Chan 
 * @version 2022.11.21
 */
public abstract class MenuButton extends Actor
{
    protected GreenfootImage image, hoverImage, clickedImage, inactiveImage;
    protected int clickCount;
    protected boolean active = true;
    public abstract void clicked();
    public void act()
    {
        if (!active) {
            return;
        }
        // Show clickedImage for 15 acts, then set back to regular image
        if(clickCount > 0) {
            clickCount --;
            if(clickCount == 0) {
                setImage(image);
            }
        }
        // When mouse is pressed, show clickedImage
        if(Greenfoot.mousePressed(this)){
            clickCount=15;
            setImage(clickedImage);
        }
        // When mouse is released, activate clicked() functionality
        if(Greenfoot.mouseClicked(this)) {
            clicked();
        }
        // When button has not just been clicked, check for hovering cursor
        if(clickCount == 0) {
            checkHover();
        }
    }
    /**
     * If user cursor is hovering over the button, change image to hoverImage
     */
    public void checkHover() {
        if(Greenfoot.mouseMoved(this)) {
            setImage(hoverImage);
        } else if(Greenfoot.mouseMoved(null)){
            setImage(image);
        }
    }
    /**
     * @return boolean          Button's <code>active</code> state
     */
    public boolean isActive() {
        return active;
    }
    /**
     * If inactive, set image to inactiveImage
     * @param a             Set <code>active</code> state to boolean value of a
     */
    public void setActive(boolean a) {
        active = a;
        if(a) {
            setImage(image);
        } else {
            setImage(inactiveImage);
        }
    }
}
