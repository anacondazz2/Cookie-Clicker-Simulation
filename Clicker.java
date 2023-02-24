import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Simulates the cursor of the players, which clicks the cookies, buy buttons, and buildings. Also available as a building, which is a copy of the main cursor that just stays in one place and clicks the cookie.
 * 
 * @author Eddie Zhuang
 * @version November 2022
 */ 
public class Clicker extends SuperSmoothMover
{
    private Player player;
    private int lagTimer = 0;
    private GreenfootImage image;
    private int[] targetPoint;
    private Clickable targetObject;
    private boolean glidingOrClicking = false;
    private int clickCount = 0;
    private int speed;
    private int clickingAnimationTimer = 0;
    private int animationIndex;
    private GreenfootSound clickSound = new GreenfootSound("click.mp3");
    private GreenfootSound buildingPurchaseSound = new GreenfootSound("building-purchase.wav");
    private GreenfootSound powerupPurchaseSound = new GreenfootSound("powerup-purchase.wav");
    private GreenfootSound sabotagePurchaseSound = new GreenfootSound("sabotage-purchase.wav");
    private boolean sentient;
    private String colour;

    /**
     * Clicker constructor
     * 
     * @param player The player that the clicker belongs to
     * @param colour The colour of the clicker
     * @param isRed Whether the player is red or not
     * @param sentient Whether the clicker is the main one owned by the player
     */
    public Clicker(Player player, String colour, int speed, boolean sentient) {
        this.player = player;
        this.colour = colour;
        this.speed = 7 + speed * 2;
        this.sentient = sentient;
        
        // Assign image
        if (colour == "red") {
            image = new GreenfootImage("red_cursor.png");
        } else if (colour == "blue") {
            image = new GreenfootImage("blue_cursor.png");
        } else if (colour == "white") {
            image = new GreenfootImage("cursor.png");
        }
        image.scale(30, 40);
        setImage(image);
        
        // Setup sound
        clickSound.setVolume(30);
        buildingPurchaseSound.setVolume(80);
        powerupPurchaseSound.setVolume(75);
        sabotagePurchaseSound.setVolume(80);
    }

    public void act()
    {
        super.act();
        
        // Change image and freeze if lagging out
        if (lagTimer > 0) {
            animateLag();
            if (lagTimer == 1) {
                setImage(image);
            }
            lagTimer--;
        // Animation when doing the actual clicking
        } else if (glidingOrClicking && !gliding) {
            clickingAnimationTimer++;
            
            if (clickingAnimationTimer == 8) { // Shrink cursor, and click
                image.scale(20, 30);
                setImage(image);
                
                clickCount++;
                if (sentient) {
                    clickSound.play();
                    if (BuyButton.class.isAssignableFrom(targetObject.getClass())) { // if target object was a BuyButton
                        BuyButton bb = (BuyButton)targetObject;
                        if (Building.class.isAssignableFrom(bb.getMySubclass())) {
                            buildingPurchaseSound.play();
                        }
                        else if (Powerup.class.isAssignableFrom(bb.getMySubclass()) && !Sabotage.class.isAssignableFrom(bb.getMySubclass())) {
                            powerupPurchaseSound.play();       
                        }
                        else {
                            sabotagePurchaseSound.play();
                        }
                    }
                }
                
                // Click target
                if (targetObject != null) {
                    targetObject.click(player);
                }
            } else if (clickingAnimationTimer == 16) { // Unshrink cursor
                image.scale(30, 40);
                setImage(image);
                clickingAnimationTimer = 0;
                glidingOrClicking = false;
            }
        }
    }

    /**
     * Causes the cursor to become a loading symbol and not click for a number of seconds.
     * 
     * @param seconds Number of seconds the clicker will lag out for
     */
    public void lagOut(int seconds) {
        lagTimer = seconds * 60;
    }
    
    /**
     * Makes cursor glide to a actor and then click
     */
    public void glideAndClick(Clickable actor) {
        int[] position = getRandomPointOnActor(actor);
        targetObject = actor;
        startGlidingTo(position[0], position[1], speed);
        glidingOrClicking = true;
    }
    
    /**
     * Returns a random point on an an actor in a circular region
     */
    private int[] getRandomPointOnActor(Actor actor) {
        GreenfootImage image = actor.getImage();
        int lesserDimension = Math.min(image.getWidth(), image.getHeight());
        int radius = lesserDimension / 2;
        
        double direction = Math.random() * (2 * Math.PI);
        double magnitude = Math.random() * radius;
        int x = (int)(actor.getX() + Math.cos(direction) * magnitude);
        int y = (int)(actor.getY() + Math.sin(direction) * magnitude);
        int[] ret = {x, y};
        
        return ret;
    }
    
    /**
     * Returns whether the cursor is currently moving towards a position or clicking it
     */
    public boolean glidingOrClicking() {
        return glidingOrClicking;
    }
    
    /**
     * Returns the number of times that the cursor has clicked
     */
    public int getClickCount() {
        return clickCount;
    }
    public void animateLag() {
        if (lagTimer % 3 == 0) {
            setImage("./gifs/lagclicker/" + animationIndex + ".png");
            animationIndex++;
            animationIndex %= 12;
        }
    }
    
    /**
     * Make clicker go into mitten state
     */
    public void wearMitten() {
        image = new GreenfootImage("./images/mitten.png");
        image.scale(30, 40);
        setImage(image);
    }
    
    /**
     * Make clicker go back into orginal cursor state
     */
    public void takeOffMitten() {
        if (colour == "red") {
            image = new GreenfootImage("red_cursor.png");
        } else if (colour == "blue") {
            image = new GreenfootImage("blue_cursor.png");
        } else if (colour == "white") {
            image = new GreenfootImage("cursor.png");
        }
        
        image.scale(30, 40);
        setImage(image);
    }
}
