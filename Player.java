import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A player in this simulation, of which there are two.
 * <h4>Adjustable properties:</h4>
 * <ul>
 *   <li>Clicks per second</li>
 *   <li>Number of clickers</li>
 *   <li>Number of starting grandmas</li>
 * </ul>
 * <h4>"AI" behaviour:</h4>
 * <ul>
 *   <li>If it has enough cookies to buy the cookie rocket, it shall do so immediately</li>
 *   <li>Default state: choose a random point on the cookie and click there</li>
 *   <li>Every 2-5 clicks, it shall attempt to perform a random action:
 *      <ul>
 *       <li>Click on a random clickable building</li>
 *       <li>Buy a random building</li>
 *       <li>Buy a random powerup</li>
 *       <li>Buy a random sabotage</li>
 *      </ul>
 *   </li>
 * <ul>
 * @author Eddie Zhuang
 * @version November 2022
 */
public class Player extends Clickable
{
    private Cookie cookie;
    private String colour;
    private int width, height;
    private int numCookies = 0;
    private int clickers;
    private int speed;
    private int grandmas;
    private String name;
    private HashMap<Class, BuildingRow> buildingRows;
    private Label scoreText;
    private Clicker clicker;
    private Clicker[] clickerBuildings;
    private int actionTime = 2; // number of clicks where the player will perform an action
    private boolean start = false;
    
    /**
     * @param width The width all the player's stuff will take up (rows, cookie, counter text, etc.)
     * @param height The width all the player's stuff will take up (rows, cookie, counter text, etc.)
     * @param clickers How many clickers the player will start off with
     * @param speed The speed of the clicker
     * @param grandmas  Number of starting grandmas
     * @param name The name of the player, for text displays. Example: "Player 1"
     * @param isRed Whether the player is red or not (blue)
     */
    public Player(int width, int height, int clickers, int speed, int grandmas, String name, String colour) {
        this.width = width;
        this.height = height;
        this.clickers = clickers;
        this.speed = speed;
        this.grandmas = grandmas;
        this.name = name;
        this.colour = colour;
        
        // Set image to none
        setImage((GreenfootImage)null);
    }
    
    /**
     * Let the player start moving
     */
    public void start() {
        start = true;
    }
    
    public void addedToWorld(World w) {
        CookieWorld cw = (CookieWorld)w;
        
        // Add cookie
        cookie = new Cookie(this);
        cw.addObject(cookie, getX(), getY() - 170);
        
        // Add stationary clickers
        clickerBuildings = new Clicker[clickers];
        for (int i = 0; i < clickers; i++) {
            clickerBuildings[i] = new Clicker(this, "white", speed, false);
            cw.addObject(clickerBuildings[i], getX(), 200);
        }
        
        // Add sentient clicker
        clicker = new Clicker(this, colour, speed, true);
        cw.addObject(clicker, getX(), 200);
        
        // Add building rows
        buildingRows = new HashMap<Class, BuildingRow>();
        int rowHeight = (height - 10) / 2 / cw.getBuildingClasses().size();
        
        for (int i = 0; i < cw.getBuildingClasses().size(); i++) {
            BuildingRow buildingRow = new BuildingRow(this, cw.getBuildingClasses().get(i), width, rowHeight, 20);
            buildingRows.put(cw.getBuildingClasses().get(i), buildingRow);
            cw.addObject(buildingRows.get(cw.getBuildingClasses().get(i)), getX(), getY() + 10 + (int)((i + 0.5) * rowHeight));
        }
        
        // Add starting grandmas
        for (int i = 0; i < grandmas; i++) {
            buildingRows.get(Grandma.class).addBuilding(600, 400);
        }
        
        // Add score text
        scoreText = new Label(name + "'s Cookies:\n" + numCookies, 35);
        cw.addObject(scoreText, getX(), getY() - 360);
    }
    
    public void act() {
        CookieWorld cw = (CookieWorld)getWorld();
        if(!start || cw.gameIsWon())
            return;
        
        // Control main clicker
        if (!clicker.glidingOrClicking()) {
            // If it has enough cookies to buy the cookie rocket, it shall do so immediately
            if (numCookies >= CookieWorld.getWinAmount()) {
                clicker.glideAndClick(cw.getWinButton());
            } else if (clicker.getClickCount() != actionTime) {
                // Default state: choose a random point on the cookie and click there
                clicker.glideAndClick(cookie);
            } else {
                // Attempt to perform a random action:
                int randomAction = getRandomNumberInRange(1, 100);
                if (randomAction <= 20) {
                    // Click on a random clickable building
                    ArrayList<Building> clickableBuildings = getClickableBuildings();
                    if (!clickableBuildings.isEmpty()) {
                        Building toClick = clickableBuildings.get(getRandomNumberInRange(0, clickableBuildings.size() - 1));
                        clicker.glideAndClick(toClick);
                    }
                } else {
                    ArrayList<BuyButton> affordableButtons = new ArrayList<BuyButton>();
                    
                    if (randomAction <= 60) {
                        // Buy a random building
                        affordableButtons = cw.getAffordableBuildingButtons(numCookies, this);
                        
                    } else if (randomAction <= 80) {
                        // Buy a random powerup
                        boolean cookieMaxed = (cookie.getLevel() == cookie.getMaxLevel());
                        affordableButtons = cw.getAffordablePowerupButtons(numCookies, name, cookieMaxed);
                        
                    } else {
                        // Buy a random sabotage
                        affordableButtons = cw.getAffordableSabotageButtons(numCookies, this);
                    }
                    
                    if (!affordableButtons.isEmpty()) {
                        BuyButton toClick = affordableButtons.get(getRandomNumberInRange(0, affordableButtons.size() - 1));
                        clicker.glideAndClick(toClick);
                    }
                }
                
                actionTime += getRandomNumberInRange(2, 5);
            }
        }
        
        // Control non-sentient clickers
        for (int i = 0; i < clickers; i++) {
            if (!clickerBuildings[i].glidingOrClicking()) {
                clickerBuildings[i].glideAndClick(cookie);
            }
        }
    }
    
    /**
     * Returns an arraylist of buildings that need to be clicked (e.g. a ready grandma)
     */
    private ArrayList<Building> getClickableBuildings() {
        ArrayList<Building> ret = new ArrayList<Building>();
        CookieWorld cw = (CookieWorld)getWorld();
        for (Class buildingType : cw.getBuildingClasses()) {
            BuildingRow buildingRow = buildingRows.get(buildingType);
            for (Building building : buildingRow.getBuildings()) {
                if (building.isReadyToClick()) {
                    ret.add(building);
                }
            }
        }
        
        return ret;
    }
    
    /**
     * Change the amount of cookies the player has.
     * 
     * @param x The amount to change by. If negative, it takes away cookies.
     */
    public void changeCookieCount(int x) {
        numCookies += x;
        // Make sure doesn't go negative
        numCookies = Math.max(numCookies, 0);
        scoreText.setValue(name + "'s Cookies:\n" + numCookies);
    }
    
    /**
     * Returns the amount of cookies that the player has.
     * 
     * @return int The number of cookies
     */
    public int getCookieCount() {
        return numCookies;
    }
    
    /**
     * Returns the name of the player
     * 
     * @return String The name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the colour of the player
     * 
     * @return String The colour
     */
    public String getColour() {
        return colour;
    }
    
    /**
     * Adds a building to the player's building rows
     * 
     * @param x The starting x position of the building (will move towards appropriate place)
     * @param y The starting y position of the building
     * @param buildingClass The class of the building
     */
    public void addBuilding(int x, int y, Class buildingClass) {
        buildingRows.get(buildingClass).addBuilding(x, y);
    }
    
    /**
     * Causes the player's cursors to start lagging
     * 
     * @param seconds The number of seconds to make them lag
     */
    public void lagClickers(int seconds) {
        clicker.lagOut(seconds);
        for (int i = 0; i < clickerBuildings.length; i++) {
            clickerBuildings[i].lagOut(seconds);
        }
    }
    
    /**
     * Returns the player's cookie.
     */
    public Cookie getCookie() {
        return cookie;
    }
    
    /**
     * Returns the hashmap of building rows that the player has
     */
    public HashMap<Class, BuildingRow> getBuildingRows() {
        return buildingRows;
    }
    
    /**
     * Returns the player's clickers
     */
    public Clicker[] getClickers() {
        return clickerBuildings;
    }
}
