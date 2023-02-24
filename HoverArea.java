import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Tracks the user's cursor. When the user touches this Actor, it will show the corresponding Description.
 * Used to show the Description of a BuyButton.
 * 
 * @author Patrick Hu 
 * @version November 2022
 */
public class HoverArea extends Actor
{
    // Buy Button's description of item
    private Description desc;
    private int x, y;
    private boolean isBeingHovered;
    private Class itemClass;
    private BuyButton btn;
    private GreenfootImage image;
    /**
     * @param btn           The BuyButton that owns this HoverArea
     */
    public HoverArea(BuyButton btn) {
        this.btn = btn;
        this.itemClass = btn.getMySubclass();
        isBeingHovered = false;
    }
    public void addedToWorld(World w) {
        x = btn.getX();
        y = btn.getY();
        
        if(itemClass != CookieRocket.class) {
            image = new GreenfootImage(btn.getImage().getWidth(), btn.getImage().getHeight());  // empty image
        } else {
            image = new GreenfootImage(btn.getImage().getWidth()-20, btn.getImage().getHeight()-50);  // empty image
            x-=10;
            y += 25;
        }
        setLocation(x, y);
        setImage(image);
    }

    public void act()
    {
        checkHoverBuyButton();
    }
    /**
     * Show button description when user hovers their cursor over the button
     */
    public void checkHoverBuyButton() {
        if (Greenfoot.mouseMoved(this) && !isBeingHovered) {
            desc = new Description(itemClass);
            int descX = x < 1200 - desc.getImage().getWidth()/2 ? x : 1200-desc.getImage().getWidth()/2;   // check if desc is too far right
            int descY = y < 600 ? y + getImage().getHeight() + 32 : y - getImage().getHeight() - 32; // check if desc is too far down
            getWorld().addObject(desc, descX, descY);
            isBeingHovered = true;
        }            
        if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this)) {
            isBeingHovered = false;
            getWorld().removeObject(desc);            
        }
    }
}
