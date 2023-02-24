import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.reflect.Constructor;

/**
 * A button that the cursors will click in order to buy an item.
 * Items include the cookie upgrade, buildings, powerups, and sabotages.
 * 
 * @author Caden Chan
 * @version 2022.11.19
 */
public class BuyButton extends Clickable
{
    // BuyButton states
    protected boolean active;  // if neither player can afford the upgrade, set to inactive and grey-out the button
    protected boolean maxedOut; // if max number of purchases is acheived (ex: cookie is at max level), grey out the button.
    // Info about the BuyButton's subclass
    protected int cost;
    protected Class mySubclass;  // the subclass associated with the button (either a subclass of Builidng or a subclass of Powerup)
    protected String name;
    // Button Images
    GreenfootImage activeImage, inactiveImage;
    GreenfootImage activeImagePressed, inactiveImagePressed;
    // Clicked animation
    protected int clickedCount;
    protected int cooldown;  // if cooldown > 0, cannot activate button.
    protected int highlightClick;  // highlightClick tracks how many acts the button needs to be highlighted for, after being clicked by a player
    // Hover Area for the showing of Descriptions
    HoverArea hover;
    // Most recent player to use this button
    Player lastPlayer;
    /**
     * @param mySubclass            The subclass of Building or Powerup that is created or activated by this button
     * @param name                  The name of the Building or Powerup
     * @param cost                  The cost of the Building or Powerup
     */
    public BuyButton(Class mySubclass, String name, int cost) {
        this.mySubclass = mySubclass;
        this.name = name;
        this.cost = cost;
        clickedCount = 0;
        hover = new HoverArea(this);
        active = true;
        maxedOut = false;
        lastPlayer = null;
    }

    public void addedToWorld(World w) {
        // set up button images
        activeImage = new GreenfootImage("buybutton-icns/" + mySubclass.getSimpleName().toLowerCase() + ".png");
        inactiveImage = new GreenfootImage("buybutton-icns/" + mySubclass.getSimpleName().toLowerCase() + "-off.png");
        activeImagePressed = new GreenfootImage(activeImage);
        activeImagePressed.scale(70, 70);
        inactiveImagePressed = new GreenfootImage(inactiveImage);
        inactiveImagePressed.scale(70, 70);
        setImage(activeImage);
        // add HoverArea
        getWorld().addObject(hover, getX(), getY());
    }

    /**
     * Simulate button being clicked by a player. Create building or activate powerup on click
     * 
     * @param p        The Player instance that has activated this button
     */
    public void click(Player player) { 
        double highlightDuration;
        Color colour;
        CooldownBar highlight;
        // Handle variable changes
        clickedCount = 20;
        setImage(activeImagePressed);  // helps maintain button image quality
        active = true;

        // Handle Cookie Rocket
        if(mySubclass == CookieRocket.class) {
            CookieWorld cw = (CookieWorld)getWorld();
            if (!cw.gameIsWon()) {
                CookieRocket rocket = new CookieRocket(player);
                getWorld().addObject(rocket, getX(), getY() + 100);                
                cw.setGameWon(true);
            }

            return;
            //Handle Powerups
        } else if(Building.class.isAssignableFrom(mySubclass)) {
            player.addBuilding(getX(), getY(), mySubclass);
            highlightDuration = 30;
            // Handle Powerups
        } else {
            Powerup powerup = createPowerup(player);
            getWorld().addObject(powerup, 0, 0);
            highlightDuration = powerup.getDuration() == 0 ? 30 : powerup.getDuration();
        }
        // Handle BuyButton highlighting & visuals
        colour = player.getColour() == "red" ? Color.RED : Color.BLUE;
        highlight = new CooldownBar((int)(getImage().getWidth()*0.9 +1), getImage().getHeight(), colour, highlightDuration);
        getWorld().addObject(highlight, getX(), getY());

        // Charge player for purchase
        if(mySubclass == MilkBottles.class) {
            player.changeCookieCount(-MilkBottles.getCost(player));
        } else {
            player.changeCookieCount(-cost);
        }
        lastPlayer = player;
    }

    public void act() {
        if(cooldown > 0) {
            cooldown --;
        }
        if(clickedCount > 0) {
            clickedCount --;
            if(clickedCount == 0) {
                if(lastPlayer == null) {
                    return;
                }
                if(getCost() <= lastPlayer.getCookieCount()  && !maxedOut) {
                    setImage(activeImage);
                } else {
                    setImage(inactiveImage);
                }
            }
        }
        if(CookieWorld.DEMO_MODE && Greenfoot.mouseClicked(hover)) {
            click(((CookieWorld)getWorld()).getP1());        }
    }

    /**
     * @return Class        Subclass of Building or Powerup, purchased through this button.
     */
    public Class getMySubclass() {
        return mySubclass;
    }

    /**
     * Used by `click` method to create a new Powerup
     * @return Powerup          new instance of mySubclass, given mySubclass is a Powerup
     */
    private Powerup createPowerup(Player player) {
        try {
            Constructor<Powerup> c = mySubclass.getConstructor(Player.class);
            return c.newInstance(player);
        } catch(Exception e) {}
        return null;
    }

    /**
     * @return int      Cost, in cookies, to use button
     */
    public int getCost() {
        return cost;    
    }

    /**
     * @param cookies       The number of cookies clicking this BuyButton should cost
     */
    public void setCost(int cookies) {
        cost = cookies;
    }

    /**
     * @param activeState           Set <code>active</code> state to true or false
     */
    public void setActive(boolean activeState) {
        active = activeState;
        if(activeState) {
            if(clickedCount == 0) {
                setImage(activeImage);
            } else {
                setImage(activeImagePressed);
            }
        } else {
            if(clickedCount == 0) {
                setImage(inactiveImage);
            } else {
                setImage(inactiveImagePressed);
            }
        }
    }

    /**
     * @return boolean          Return the button's <code>active</code> state
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set true if the maximum number of purchases have been acheived. Otherwise, set false.
     * @param maxed         Set <code>maxedOut</code> state to true or false
     */
    public void setMaxedOut(boolean maxed) {
        if(maxedOut == maxed) { // nothing changes
            return;
        }
        maxedOut = maxed;
        // If maxed, add "Max" label and grey out the BuyButton. The "Max" icon is given to the Button's HoverArea
        if(maxed) {
            GreenfootImage maxIcon = new GreenfootImage("buybutton-icns/max.png");
            GreenfootImage maxImage = new GreenfootImage(maxIcon.getWidth(), maxIcon.getWidth());
            maxImage.drawImage(new GreenfootImage("buybutton-icns/max.png"), 0, 30);
            maxImage.setTransparency(230);
            hover.setImage(maxImage);
            setActive(false); // grey out BuyButton
        } else {
            // Remove "Max" label
            hover.setImage(new GreenfootImage(getImage().getWidth(), getImage().getHeight()));
        }
    }

    /**
     * @return boolean          Return the button's <code>maxedOut</code> state
     */
    public boolean isMaxedOut() {
        return maxedOut;
    }

    /**
     * BuyButton may only be clicked if the <code>active</code> state is true, and the <code>maxedOut</code> state is false
     * @return boolean          Return whether the button can be clicked or not
     */
    public boolean canClick() {
        return active && !maxedOut;
    }
}