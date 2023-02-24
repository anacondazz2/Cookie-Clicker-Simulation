import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Start the simulation!
 * 
 * @author Caden Chan 
 * @version 2022.11.21
 */
public class StartButton extends MenuButton
{
    /**
     * Button used to start the simulation
     */
    public StartButton() {
        // Preset images
        image = new GreenfootImage("menu/button/startbutton.png");
        hoverImage = new GreenfootImage("menu/button/startbutton-hover.png");
        clickedImage = new GreenfootImage("menu/button/startbutton-clicked.png");
        setImage(image);
    }
    public void act()
    {
        super.act();
    }
    /**
     * When startButton is clicked, start the simulation.
     */
    public void clicked() {
        // setImage();
        ((MenuWorld)getWorld()).start();
    }
    public void checkHover() {
        super.checkHover();
    }
}
