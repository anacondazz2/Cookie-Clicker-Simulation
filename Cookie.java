import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Each player gets their own Cookie. It is clicked by Clicker objects to gain cookies.
 * Increasing the Cookie's level will increase the number of cookies gained per click!
 * 
 * @author Caden Chan
 * @version 2022.11.22
 */
public class Cookie extends Clickable
{
    private int level, cookiesPerClick, animateCount, originalSize;
    private final int maxLevel = 4;
    private final int ANIM_DURATION = 5; // how many acts the cookie is enlargened for
    private final int ANIM_FACTOR = 30; // increase image by how many pixels on click
    private GreenfootImage image;
    private Player player;
    private BuyButton myUpgradeBtn;
    // Array of file paths, one for each of the Cookie's different levels
    public final String[] COOKIE_FILES = {"images/cookie-levels/level1.png", "images/cookie-levels/level2.png", "images/cookie-levels/level3.png", "images/cookie-levels/level4.png"};
    public final GreenfootImage[] COOKIE_SPRITES = getCookieSpriteArr();  // use level to index through array, and retrieve the corresponding image.
    
    /**
     * @param player            Player to whom this cookie belongs
     */
    public Cookie(Player player) {
        this.player = player;
        image = COOKIE_SPRITES[0];  // starting image
        level = 1;
        originalSize = image.getWidth(); // save original size
        animateCount = 0;
        cookiesPerClick = calculateCookieOutput();  // get initial cookiesPerClick
        setImage(image);
    }
    /**
     * Set the cost for next upgrade
     */
    public void addedToWorld(World w) {
        myUpgradeBtn = ((CookieWorld)getWorld()).getPlayerUpgradeButton(player);
        myUpgradeBtn.setCost(getUpgradeCost()); // initialize cookie cost
    }
    public void act()
    {
        // Imitate clicking animation
        if(animateCount > 0) {
            animateCount --;
            if(animateCount == 0) {
                image.scale(originalSize, originalSize); // bring cookie back to original size
                setImage(image);
            }
        }
    }
    /**
     * Make sprite bigger when `Cookie` is "clicked" by a `Clicker`, to
     * make it seem like it's being "clicked"
     * @param player            The player that has clicked this cookie
     */
    public void click(Player player) {
        animateCount = ANIM_DURATION;
        image.scale(originalSize + ANIM_FACTOR, originalSize + ANIM_FACTOR);
        setImage(image);
        // give cookies to player
        player.changeCookieCount(cookiesPerClick);
    }
    /**
     * Increase the `Cookie`'s level and calculate `cookiesPerClick` accordingly
     */
    public void levelUp() {
        if(level == maxLevel) {  // if level is at max, do nothing
            return;
        }
        level += 1;
        cookiesPerClick = calculateCookieOutput();
        updateSprite();  // change sprite based on new level
        myUpgradeBtn.setCost(getUpgradeCost());  // get cost of next upgrade
        if(level == maxLevel) {  // if level is now max, reflect this on the Cookie's upgrade button
            myUpgradeBtn.setMaxedOut(true);
        }
    }
    
    /**
     * @return int          Number of cookies per click, dependant on the Cookie's level
     */
    private int calculateCookieOutput() {
        return 50 + (int)Math.pow(4, level)*2; // **TEMPORARY algorithm placeholder; must create balanced algorithm for deciding how many cookies each click will award the player, based on level.
    }
    
    /**
     * Update the Cookie's sprite, depending on its `level`
     */
    private void updateSprite() {
        image = COOKIE_SPRITES[level-1];
        setImage(image);
    }
    // - - - - - GETTERS - - - - - 
    /**
     * @return int          Cookie's current level
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * @return int          Number of cookies per click
     */
    public int getCookieOutput() {
        return cookiesPerClick;
    }
    /**
     * 1000 * 5^currentlevel
     * - level 2 = 5000
     * - level 3 = 25000
     * - level 4 = 125000
     * @return int          Number of cookies required to purchase the next Cookie level
     */
    public int getUpgradeCost() {
        return 1000*(int)Math.pow(5, level);
    }
    
    /**
     * @return GreenfootImage[]     Initialize an array of Cookie sprites
     */
    private GreenfootImage[] getCookieSpriteArr() {
        GreenfootImage[] images = new GreenfootImage[COOKIE_FILES.length];
        for(int i=0;i<images.length;i++) {
            images[i] = new GreenfootImage(COOKIE_FILES[i]);
        }
        return images;
    }
    
    /**
     * Returns the cookie's max level
     * 
     * @return int The cookie's max level
     */
    public int getMaxLevel() {
        return maxLevel;
    }
}
