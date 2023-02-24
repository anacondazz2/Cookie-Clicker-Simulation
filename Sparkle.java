import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Visual effect for the CookieUpgrade and LuckyClover Powerups
 * 
 * @author Caden Chan
 * @version 2022.11.19
 */
public class Sparkle extends Effect
{
    private Actor target;
    /**
     * Constructor used for default-looking sparkles (yellow)
     * @param duration          How long the sparkle stays on the screen
     * @param target            Actor on which the sparkle will choose a random location to spawn
     */
    public Sparkle(int duration, Actor target) {
        this(duration, target, CookieUpgrade.class);
    }
    /**
     * Constructor allowing for different styles of sparkles, depending on isLucky
     * @param duration          How long the sparkle stays on the screen
     * @param target            Actor on which the sparkle will choose a random location to spawn
     * @param powerupClass      Choose sparkles sprites based on the powerup.
     */
    public Sparkle(int duration, Actor target, Class powerupClass) {  // if isLucky, use green sparkles
        this.duration = duration;
        this.target = target;
        fadeLen = duration > 90 ? 90 : duration/2;  // i.e. fade after 90 acts OR [duration/2] acts
        // green if luckyclover, blue if extraexpensivefilament, otherwise yellow.
        String filePath = powerupClass == LuckyClover.class ? "effect/sparkles/luckysparkles" : powerupClass == ExtraExpensiveFilament.class ? "effect/sparkles/bluesparkles" : "effect/sparkles/sparkles"; // choose the type of sparkles
        image = new GreenfootImage(filePath + Greenfoot.getRandomNumber(4) + ".png");  // choose random sprite in sparkles collection
        setImage(image);
    }
    /**
     * Add sparkle to random position on [Actor target]
     * @param w
     */
    public void addedToWorld(World w) {
        int[] pos = getRandomPos();
        setLocation(pos[0], pos[1]);
    }
    public void act()
    {
        if (duration == 0){
            getWorld().removeObject(this);
        } else if (duration <= fadeLen){  // if duration <= fadeLen, start/continue fading the image until duration == 0.
            fade (image, duration, fadeLen);
        }
        duration--;
    }
    /**
     * Generate a random (x,y) coordinate, such that (x,y) touches [Actor target].
     * @return int[]            Randomly chosen (x, y) coordinates, located on [Actor target]
     */
    public int[] getRandomPos() {
        int[] pos = new int[2];
        int w = target.getImage().getWidth();  // target actor's width
        int h = target.getImage().getHeight(); // target actor's height
        pos[0] = target.getX() - w/2 + Greenfoot.getRandomNumber(w);  // random x-val
        pos[1] = target.getY() - h/2 + Greenfoot.getRandomNumber(h);  // random y-val
        return pos;
    }
}
