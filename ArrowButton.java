import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button clicked by the user to modify MenuWorld settings, by increasing or decreasing its counter.
 * 
 * @author Caden Chan 
 * @version 2022.11.22
 */
public class ArrowButton extends MenuButton
{
    private boolean isLeft;
    private MenuSetting menuSetting;
    /**
     * @param isLeft                The direction of the arrow (left/right)
     * @param menuSetting           The setting that this arrow button controls
     */
    public ArrowButton(boolean isLeft, MenuSetting menuSetting) {
        this.isLeft = isLeft;
        this.menuSetting = menuSetting;
        // Preset button images
        String path = isLeft ? "leftarrow" : "rightarrow";
        image = new GreenfootImage("menu/button/" + path + ".png");
        hoverImage = new GreenfootImage("menu/button/" + path + "-hover.png");
        clickedImage = new GreenfootImage("menu/button/" + path + "-clicked.png");
        inactiveImage = new GreenfootImage("menu/button/" + path + "-off.png");
        setImage(image);
    }
    public void act()
    {
        super.act();
    }
    /**
     * If this arrow button points leftwards, decrease the menuSetting's value. <br>
     * If this arrow button points rightwards, increase the menuSetting's value.
     */
    public void clicked() {
        if(isLeft) {  // If this button is left-facing, decrease associated setting
            menuSetting.decrCount();
        } else {  // If this button is right-facing, increase associated setting
            menuSetting.incrCount();
        }
    }
    public void checkHover() {
        super.checkHover();
    }
    
}
