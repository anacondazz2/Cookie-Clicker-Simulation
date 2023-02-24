import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An image of a description for an item that will be displayed when the simulation 
 * viewer hovers over a buy button.
 * 
 * @author Patrick Hu
 * @version November 2022
 */
public class Description extends Clickable
{
    private double scale = 1; // can set to a smaller value to scale down image, however quality drastically reduces
    
    public Description(Class itemClass) {
        String s = itemClass.getSimpleName();
        switch (s) {
            case "Grandma":
                setImage("./images/descriptions/grandma.png");
                break;
            case "Baby":
                setImage("./images/descriptions/baby.png");
                break;
            case "AlchemyLab":
                setImage("./images/descriptions/alchemy-lab.png");
                break;
            case "Printer3D":
                setImage("./images/descriptions/printer3D.png");
                break;
            case "Portal":
                setImage("./images/descriptions/portal.png");
                break;
            case "CookieGod":
                setImage("./images/descriptions/cookie-god.png");
                break;
            case "WarmMits":
                setImage("./images/descriptions/warm-mits.png");
                break;
            case "ReverseDementia":
                setImage("./images/descriptions/reverse-dementia.png");
                break;
            case "ExtraExpensiveFilament":
                setImage("./images/descriptions/extra-expensive-filament.png");
                break;
            case "LuckyClover":
                setImage("./images/descriptions/lucky-clover.png");
                break;
            case "CookieMonster":
                setImage("./images/descriptions/cookie-monster.png");
                break;
            case "GrandmaRevolution":
                setImage("./images/descriptions/grandma-revolution.png");
                break;
            case "Lag":
                setImage("./images/descriptions/lag.png");
                break;
            case "MilkBottles":
                setImage("./images/descriptions/milk-bottles.png");
                break;
            case "CookieUpgrade":
                setImage("./images/descriptions/cookie-upgrade.png");
                break;
            case "CookieRocket":
                setImage("./images/descriptions/cookie-rocket.png");
                break;
        }
        
        getImage().scale((int)(getImage().getWidth() * scale), (int)(getImage().getHeight() * scale));
    }
}

/*
 * Colours used in the description:
 * Blue: #4086fa
 * Green: #1b9902
 * Pink: #f0129a
 * Purple: #9428ff    
 */
