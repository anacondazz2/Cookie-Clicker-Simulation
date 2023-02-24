import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Type of BuyButton which is used only for the win condition (i.e. the Cookie Rocket).
 * Has a different visual appearance than other `BuyButton`'s
 * 
 * @author Caden Chan
 * @version 2022.11.14
 */
public class WinButton extends BuyButton
{
    /**
     * @param mySubclass            Set to CookieRocket.class
     * @param name                  Button name
     * @param cost                  Cost to activate this button
     */
    public WinButton(Class mySubclass, String name, int cost) {
        super(mySubclass, name, cost);
    }
    public void addedToWorld(World w) {
        // set up button images
        activeImage = new GreenfootImage("buybutton-icns/win-btn.png");
        inactiveImage = new GreenfootImage("buybutton-icns/win-btn-off.png");
        activeImagePressed = new GreenfootImage(activeImage);
        activeImagePressed.scale(230, 124);
        inactiveImagePressed = new GreenfootImage(inactiveImage);
        inactiveImagePressed.scale(230, 124);
        setImage(activeImage);
        // add HoverArea object
        getWorld().addObject(hover, getX(), getY());
    }
}
