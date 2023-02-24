import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Show preview of clicker and its speed/cps
 * 
 * @author Eddie Zhuang, Caden Chan
 * @version November 2022
 */
public class PreviewClicker extends PreviewActor
{
    private int speed;
    private int[] targetPosition;
    private boolean glidingOrClicking;
    private int clickingAnimationTimer, actCount, startX, startY, radius;
    private GreenfootImage image;
    /**
     * @param radius                The radius that clicker can move within
     * @param speed                 The clicker's speed
     */
    public PreviewClicker(int radius, int speed) {
        this.radius = radius;
        image = new GreenfootImage("cursor.png");
        image.scale(30, 40);
        setImage(image);
        actCount = 0;
        this.speed = 2 + speed * 2;
    }
    public void addedToWorld(World w) {
        startX = getX();
        startY = getY();
    }
    public void act()
    {
        if (gliding) {
            moveTowards(targetPosition[0], targetPosition[1], glideSpeed);
        
            // If it's close enough to the target, stop gliding
            if (distanceTo(targetPosition[0], targetPosition[1]) <= glideSpeed) {
                setLocation(targetPosition[0], targetPosition[1]);
                gliding = false;
            }
        }
        else if (glidingOrClicking) {
            clickingAnimationTimer++;
            
            if (clickingAnimationTimer == 60/speed) { // Shrink cursor, and click
                image.scale(20, 30);
                setImage(image);
                
            } else if (clickingAnimationTimer >= 120/speed) { // Unshrink cursor
                image.scale(30, 40);
                setImage(image);
                clickingAnimationTimer = 0;
                glidingOrClicking = false;
            }
        } 
        else if (actCount %60 == 0)  {
            glideAndClick();
        }
    }
    /**
     * Makes cursor glide to a actor and then click
     */
    public void glideAndClick() {
        targetPosition = getRandomPoint();
        startGlidingTo(targetPosition[0], targetPosition[1], speed);
        glidingOrClicking = true;
    }
    /**
     * Get a random point within the PreviewClicker's <code>radius</code>
     * @return int[]            The coordinates of the clicker's next position 
     */
    private int[] getRandomPoint() {
        
        double direction = Math.random() * (2 * Math.PI);
        int x = (int)(startX + Math.cos(direction) * radius);
        int y = (int)(startY + Math.sin(direction) * radius);
        int[] ret = {x, y};
        
        return ret;
    }
    /**
     * Set the PreviewClicker's speed
     * @param x         Clicker speed value
     */
    public void setSpeed(int x) {
        speed = 2 + x * 2;
    }
}
