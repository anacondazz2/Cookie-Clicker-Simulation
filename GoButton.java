import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Clicked by user to transition from the welcome screen to the StoryWorld.
 * 
 * @author Caden Chan 
 * @version 2022.11.22
 */
public class GoButton extends MenuButton
{
    public GoButton() {
        // Preset images
        image = new GreenfootImage("menu/button/gobutton.png");
        hoverImage = new GreenfootImage("menu/button/gobutton-hover.png");
        clickedImage = new GreenfootImage("menu/button/gobutton-clicked.png");
        setImage(image);
    }
    public void act()
    {
        super.act();
    }
    /**
     * Go to StoryWorld
     */
    public void clicked() {
        ((WelcomeWorld)getWorld()).goToStory();
    }
    public void checkHover() {
        super.checkHover();
    }
}
