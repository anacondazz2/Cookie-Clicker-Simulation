import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A building that produces cookies for its respective player.
 * 
 * @author Patrick Hu, Jonathan Zhao
 * @version November 2022
 */
public abstract class Building extends SuperSmoothMover
{
    protected static boolean LUCKY = false;
    
    protected int animationSize;
    protected int animationIndex;
    protected double scale; // scale of each image in the building's gif animation
    protected Player player;
    protected int actCount = 0;
    protected int actMark = 100;
    protected boolean readyToClick = false;
    
    public Building(Player player) {
        this.player = player;
        scale = 1;
    }
    
    public void addedToWorld(World w) {
        // set inital image for building immediately so its dimensions are known from the start
        String className = this.getClass().getSimpleName();
        setImage("./gifs/" + className + "/0" + ".png");
        getImage().scale((int)(getImage().getWidth() * scale), (int)(getImage().getHeight() * scale));
    }
    
    public void act() {
        super.act();
        actCount++;
        animate();
    }
    
    /**
     * Changes player's cookie count by amount this building will produce.
     * If lucky clover powerup is active, will produce upper bound of range specified.
     * 
     * @param start     lower bound of the range of production
     * @param end       upper bound of the range of production
     */
    public void produce(int start, int end) {
        int amount;
        if (Building.LUCKY) {
            amount = end;
        }
        else {
            amount = getRandomNumberInRange(start, end);
        }
        
        player.changeCookieCount(amount);
    }
    
    /**
     * Gets the next act mark for when a building performs its action by calculating a random amount of seconds 
     * between a `start` and `end`.
     * 
     * @param start         The lower bound for the range of time.
     * @param end           The upper bound for the range of time.
     */
    public int getNextActMark(int start, int end) {
        int t = getRandomNumberInRange(start * 60, end * 60);
        return actCount + t;
    }
    
    /**
     * Animates the building's action. For example, grandmas bake and babies eat cookies.
     */
    public void animate() {
        String className = this.getClass().getSimpleName();
        if (actCount % 20 == 0) {
            setImage("./gifs/" + className + "/" + animationIndex + ".png");
            getImage().scale((int)(getImage().getWidth() * scale), (int)(getImage().getHeight() * scale));
            animationIndex++;
            animationIndex %= animationSize;
        }
    }
    
    /**
     * Returns whether the building must be clicked right now
     */
    public boolean isReadyToClick() {
        return readyToClick;
    }
    
    public Player getPlayer() {
        return player;
    }
}
