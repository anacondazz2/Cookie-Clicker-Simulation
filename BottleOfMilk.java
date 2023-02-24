import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Produced by the <code>MilkBottles Powerup </code>. Triggers <code>drinkMilk()</code> method in <code>Baby</code>
 * 
 * @author Caden Chan
 * @version 2022.11.19
 */
public class BottleOfMilk extends Effect
{
    private int startX, startY, endX, endY;  // BottleOfMilk starts at BuyButton (startX, startY), lands on Baby (endX, endY)
    private Baby baby;
    private GreenfootImage image;
    // Variables for movement calculations
    private double dx, dy, vix, viy, ay, t;
    private double x, y;
    /**
     * @param startX                The x-value of the bottle's starting location (The BuyButton it came from)
     * @param startY                The y-value of the bottle's starting location (The BuyButton it came from)
     * @param baby                  The baby that this bottle is going towards
     */
    public BottleOfMilk(int startX, int startY, Baby baby) {
        this.startX = startX;
        this.startY = startY;
        this.baby = baby;
        if(baby.getWorld() != null) {
            this.endX = baby.getX();
            this.endY = baby.getY();
        }
        x = startX;
        y = startY;
        dx = endX - startX;
        dy = endY - startY;
        ay = 30.0/60;  // gravity
        t = 1.2*60;  // how long it takes for the bottle to reach the baby, in acts.
        /**
         * projectile motion equations 
         * dy = viy * t + (1/2)a * t^2
         * viy = (dy-(1/2a)*t^2)/t
         * 
         * dx = vix * t
         * vix = dx/t
         */
        viy = (dy - 0.5*ay*t*t)/t;
        vix = dx/t;
        image = new GreenfootImage("powerup-icns/bottleofmilk.png");
        setImage(image);
    }
    /**
     * BottleOfMilk starts at the MilkBottles BuyButton.
     * @param w
     */
    public void addedToWorld(World w) {
        setLocation(startX,startY);
    }
    public void act()
    {
        setLocation((int)x, (int)y);
        y += viy;
        x += vix;
        viy += ay;
        if(baby.getWorld() == null || intersects(baby)) {
            baby.drinkMilk();
            getWorld().removeObject(this);
            return;
        }
    }
}
