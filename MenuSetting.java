import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Create a group of actors which work together to manage a setting. 
 * <h4>Includes:</h4>
 * <ul>
 *  <li>Arrow buttons (increase/decrease value of simulation setting)</li>
 *  <li>Counter (Text showing the value of the simulation setting)</li>
 *  <li>Title (What is the name of the setting?)</li>
 * </ul>
 * @author Caden Chan
 * @version 2022.11.22
 */
public class MenuSetting extends Actor
{
    private ArrowButton leftArrow, rightArrow;
    private Label counter, titleLabel;
    private int count, minVal, maxVal, x, y, player;
    private final int fontSize = 60;
    private final int spacing = 20;
    private int settingID;  // 0 = # of grandmas, 1 = # of clickers, 2 = cps
    /**
     * Create all the components of a setting in MenuWorld, allowing the user to
     * set certain values before starting the simulation.
     * Note: The overall MenuSetting is centered on the <code>Label counter</code>.
     * 
     * @param minVal                The counter's min possible value
     * @param maxVal                The counter's max possible value
     * @param x                     The counter's x-location
     * @param y                     The counter's y-location
     * @param title                 The title of this setting
     * @param settingID             The ID of this setting
     * @param player                The player affected by this setting. Value of 1 or 2.
     */
    public MenuSetting(int minVal, int maxVal, int x, int y, String title, int settingID, int player) {
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.x = x;
        this.y = y;
        this.settingID = settingID;
        this.player = player;
        count = minVal;
        leftArrow = new ArrowButton(true, this);
        leftArrow.setActive(false); // start as inactive, since initialized with count = minVal
        rightArrow = new ArrowButton(false, this);
        counter = new Label(count, fontSize);
        titleLabel = new Label(title, 40);
        setImage(new GreenfootImage(10,10));
    }
    /**
     * Add all components to their respective locations, relative to the counter's position,
     * defined by (int x, int y).
     * @param w
     */
    public void addedToWorld(World w) {
        w.addObject(this, x, y);
        w.addObject(counter, x, y);
        w.addObject(titleLabel, x, y-100);
        w.addObject(leftArrow, x-leftArrow.getImage().getWidth() - spacing, y);
        w.addObject(rightArrow, x+rightArrow.getImage().getWidth() + spacing, y);
    }
    /**
     * @return int          The counter's value
     */
    public int getCount() {
        return count;
    }
    /**
     * Increase counter by 1
     */
    public void incrCount() {
        count ++;
        // Disable right arrow if reached maxVal
        if(count == maxVal) {
            rightArrow.setActive(false);
        }
        // Re-enable left arrow if previous value was minVal
        if(count == minVal+1) {
            leftArrow.setActive(true);
        }
        // Add associated preview object, or edit cursor speed
        if(settingID == 0) {
            ((MenuWorld)getWorld()).addPreviewGrandma(this.player);
        } else if(settingID == 1) {
            ((MenuWorld)getWorld()).addPreviewClicker(this.player);
        } else if(settingID == 2) {
            ((MenuWorld)getWorld()).editSpeed(this.player, count);
        }
        // Set counter Label value
        counter.setValue(Math.min(count, maxVal));  // Math.min just to be safe, cant be greater than maxVal :b
    }
    /**
     * Decrease counter by 1
     */
    public void decrCount() {
        count--;
        // Disable left arrow if reached minVal
        if(count == minVal) {
            leftArrow.setActive(false);
        }
        // Enable right arrow if previous value was maxVal
        if(count == maxVal-1) {
            rightArrow.setActive(true);
        }
        // Remove associated preview object, or edit cursor speed
        if(settingID == 0) {
            ((MenuWorld)getWorld()).removePreviewGrandma(this.player);
        } else if(settingID == 1) {
            ((MenuWorld)getWorld()).removePreviewClicker(this.player);
        } else if(settingID == 2) {
            ((MenuWorld)getWorld()).editSpeed(this.player, count);
        }
        // Set counter Label value
        counter.setValue(Math.max(count, minVal));  // Math.max just to be safe, cant be less than minVal :b
    }
}
