import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Babies do not produce cookies for their player but instead take away 
 * cookies from the other player.
 * every second.
 * 
 * @author Patrick Hu
 * @version November 2022
 */
public class Baby extends Building
{
    private boolean isDrinkingMilk; // whether the baby was given a milk bottle
    private int drinkingCount, drinkingActs;
    private GreenfootImage drinkingSprite;
    /**
     * @param player            The player who has purchased a Baby
     */
    public Baby(Player player) {
        super(player);
        animationSize = 11;
        scale = 0.5;
        isDrinkingMilk = false;
        drinkingCount = 0;
        drinkingActs = 90;
    }
    
    public void act() {
        super.act();
        if(drinkingCount == 0 && isDrinkingMilk) {
            player.getBuildingRows().get(Baby.class).getBuildings().remove(this);
            getWorld().removeObject(this);
        } else if (drinkingCount <= drinkingActs && isDrinkingMilk) {
            Effect.fade(drinkingSprite, drinkingCount, drinkingActs);
        } else {
            if (actCount == actMark) {
                eat();
                actMark = getNextActMark(2, 3);
            }
        }
        if(isDrinkingMilk) {
            setImage(drinkingSprite);
            drinkingCount --;
        }
    }
    
    /**
     * Eats(removes) 5-10 cookies from the other player.
     */
    public void eat() {        
        int amountToEat;
        if (Building.LUCKY) {
            amountToEat = 10;
        }
        else {
            amountToEat = getRandomNumberInRange(5, 10);   
        }
        
        CookieWorld cw = (CookieWorld)getWorld();
        Player otherPlayer = cw.getOtherPlayer(player);
        otherPlayer.changeCookieCount(-amountToEat);
    }
    
    /**
     * Changes baby's image to it drinking a bottle of milk. This happens when the 
     * Milk Bottles powerup is activated.
     */
    public void drinkMilk() {
        drinkingSprite = new GreenfootImage("powerup-icns/baby-drinking-milk.png");
        drinkingSprite.scale(50, 50);
        setImage(drinkingSprite);
        isDrinkingMilk = true;
        drinkingCount = 120;
    }
}
