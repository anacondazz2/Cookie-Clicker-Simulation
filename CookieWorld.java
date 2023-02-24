import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/** 
 * <h2>Credits</h2>
 * <h3>Images</h3>
 * <ul>
 *  <li>Background image from the original Cookie Clicker by Orteil, provided by caveman at https://wallpapercave.com/cookie-clicker-wallpapers</li>
 *  <li>Cursor image provided by Tobias Ahlin Bjerrome at https://tobiasahlin.com/blog/common-mac-os-x-lion-cursors/</li>
 *  <li>Spinning wheel of death provided by howdytom at https://apple.stackexchange.com/questions/243675/location-of-the-resource-files-for-the-spinning-wait-cursor</li>
 *  <li>Button object backgrounds provided by https://www.freepik.com/free-vector/wooden-buttons-ui-game_12632833.htm>wooden buttons</li>
 *  <li>Remaining art created by Jonathan Zhao</li>
 * </ul>
 * <h3>Code</h3>
 * <ul>
 *   <li>SuperSmoothMover & Effect from Mr. Cohen</li>
 *   <li>Label by Amjad Altadmri</li>
 * </ul>
 * <h3>Sound</h3>
 * <ul>
 *   <li>Click sound effect obtained from https://www.zapsplat.com</li>
 *   <li>Rocket sound effect obtained from https://mixkit.co/free-sound-effects/rocket/</li>
 *   <li>Building purchase sound effect obtained from https://www.youtube.com/watch?v=4kVTqUxJYBA</li>
 *   <li>Powerup purchase sound effect obtained from https://www.youtube.com/watch?v=v94-AQxceGk</li>
 *   <li>Sabotage purchase sound effect obtained from https://www.youtube.com/watch?v=1UviLSfx5vs</li>
 *   <li>Rocket launch sound effect obtained from https://www.youtube.com/watch?v=YDhY0elzq20</li>
 * </ul>
 * <h3>Music</h3>
 * <ul>
 *   <li>Welcome/Menu background music: "Snowdin Town" by Toby Fox</li>
 *   <li>Simulation background music: "Field of Hopes and Dreams" by Toby Fox</li>
 * <ul>
 * @author Patrick Hu, Eddie Zhuang, Caden Chan, Jonathan Zhao
 * @version 2022.11.23
 */

public class CookieWorld extends World
{
    public static final boolean DEMO_MODE = false; // set to true for user to activate buttons, on behalf of p1
    // Variable adjustments from menu (temporary, will be passed in from constructor)
    private int startDelay = 120;
    
    // World variables
    private GreenfootImage background;
    private GreenfootSound bgMusic;
    
    // Game variables
    // select random index of buyButtons to add respective building/activate respective powerup
    private BuyButton[] buyBuildingButtons, buyPowerupButtons, buySabotageButtons, cookieUpgradeButtons;
    private WinButton winButton;
    private Label buildingTitle, powerupTitle, sabotageTitle;
    private static final int WIN = 1000000; // player must reach this amount of cookies to win
    
    // Player variables
    private Player p1, p2;
    
    // Whether game has been won
    private boolean gameWon;
    
    // Master lists of Building/Powerup classes
    private LinkedHashMap<Class, HashMap<String, Object>> buildingMap;
    private LinkedHashMap<Class, HashMap<String, Object>> powerupMap;
    /**
     * {
     *      AlchemyLab.class: {
     *          "name": "Alchemy Lab",
     *          "cost": 10
     *          },  ...etc.
     * }
     */
    /**
     * CookieWorld constructor. Each array is of length 2. Index 0 = Player 1, index 1 = Player 2.
     * 
     * @param bgMusic             The background music
     * @param grandmas            Number of starting grandmas for p1 and p2 respectively
     * @param clickers            Number of clickers for p1 and p2 respectively 
     * @param speeds              Speeds for the clickers of p1 and p2 respectively
     */
    public CookieWorld(GreenfootSound bgMusic, int[] grandmas, int[] clickers, int[] speeds)
    {   
        super(1200, 800, 1); 
        
        // Drawing Order of Classes
        setPaintOrder(CookieRocket.class, DarkOverlay.class, Lag.class, Clicker.class, HoverArea.class, Description.class, BottleOfMilk.class, Effect.class, Building.class, Powerup.class, CooldownBar.class, Label.class, BuyButton.class, BuildingRow.class, Cookie.class);
        
        // Set world background
        background = new GreenfootImage("cookie-background.png");
        setBackground(background);
        
        // - - - Prepare world data - - -
        // Initialize building hashmap
        buildingMap = new LinkedHashMap<Class, HashMap<String, Object>>();
        buildingMap.put(Grandma.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Grandma", 100}));
        buildingMap.put(Baby.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Baby", 300}));
        buildingMap.put(AlchemyLab.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Alchemy Lab", 1000}));
        buildingMap.put(Printer3D.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"3D Printer", 3500}));
        buildingMap.put(Portal.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Portal", 7500}));
        buildingMap.put(CookieGod.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Cookie God", 15000}));
        
        // Initialize powerup hashmap
        powerupMap = new LinkedHashMap<Class, HashMap<String, Object>>();
        powerupMap.put(CookieUpgrade.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Cookie Upgrade", 100}));
        powerupMap.put(WarmMits.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Warm Mits", 200}));
        powerupMap.put(ReverseDementia.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Reverse Dementia", 750}));
        powerupMap.put(ExtraExpensiveFilament.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Extra Expensive Filament", 1500}));
        powerupMap.put(LuckyClover.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Lucky Clover", 5000}));
        
        // Sabotages
        powerupMap.put(CookieMonster.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Cookie Monster", 3500}));
        powerupMap.put(GrandmaRevolution.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Grandma Revolution", 500}));
        powerupMap.put(Lag.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Lag", 1000}));
        powerupMap.put(MilkBottles.class, createHashmap(new String[]{"name", "cost"}, new Object[]{"Milk Bottles", 100})); // cost is 100 * number of babies
        
        // - - - Initialize BuyButton arrays - - -
        // Building Buttons
        buyBuildingButtons = initBuyButtons(getBuildingClasses());
        // Powerup Buttons
        ArrayList<Class> tempPowerups = getPowerupClasses(); // powerups omitting CookieUpgrade.class
        tempPowerups.removeIf(p -> Sabotage.class.isAssignableFrom(p)); // omit sabotages
        tempPowerups.removeIf(p -> p == CookieUpgrade.class);
        buyPowerupButtons = initBuyButtons(tempPowerups);  // omit CookieUpgrade class; dealt with separately in next line
        // Sabotage Buttons
        ArrayList<Class> tempSabotages = getPowerupClasses();
        tempSabotages.removeIf(p -> !Sabotage.class.isAssignableFrom(p));  // p is not an instance of Sabotage, remove
        buySabotageButtons = initBuyButtons(tempSabotages);
        // Cookie Upgrade Buttons
        cookieUpgradeButtons = initBuyButtons(new ArrayList<Class>(Arrays.asList(CookieUpgrade.class, CookieUpgrade.class)));  // two buttons for CookieUpgrade class (one for each player)
        
        // - - - Initialize players - - -
        p1 = new Player(412, getHeight(), clickers[0], speeds[0], grandmas[0], "Player 1", "red");
        p2 = new Player(420, getHeight(), clickers[1], speeds[1], grandmas[1], "Player 2", "blue");
        addObject(p1, 205, 400);
        addObject(p2, 990, 400);
        
        // - - - Rest - - -
        gameWon = false;
        
        // - - - Draw Buttons - - -
        int btnX, btnY;
        for(int i=0;i<buyBuildingButtons.length;i++) {
            btnX = 495 + 100*(i%3);
            btnY = 100 + 84*(i/3);
            addObject(buyBuildingButtons[i], btnX, btnY);
        }
        for(int i=0;i<buyPowerupButtons.length;i++) {
            btnX = 546 + 100*(i%2);
            btnY = 330 + 84*(i/2);
            addObject(buyPowerupButtons[i], btnX, btnY);
        }
        for(int i=0;i<buySabotageButtons.length;i++) {
            btnX = 546 + 100*(i%2);
            btnY = 550 + 84*(i/2);
            addObject(buySabotageButtons[i], btnX, btnY);
        }
        for(int i=0;i<cookieUpgradeButtons.length;i++) {
            btnX = (cookieUpgradeButtons[i].getImage().getWidth()/2) + 355 + 790*i;
            btnY = 360;
            addObject(cookieUpgradeButtons[i], btnX, btnY);
        }
        winButton = new WinButton(CookieRocket.class, "Cookie Rocket", WIN);
        addObject(winButton, getWidth()/2 + 10, 710);
        // - - - Draw Subtitles for BuyButton Groups
        buildingTitle = new Label("Building shop", 30);
        powerupTitle = new Label("Powerup shop", 30);
        sabotageTitle = new Label("Sabotage shop", 30);
        addObject(buildingTitle, getWidth()/2, 30);
        addObject(powerupTitle, getWidth()/2, 260);
        addObject(sabotageTitle, getWidth()/2, 480);
        // BG Music
        this.bgMusic = bgMusic;
        bgMusic.setVolume(20);
        bgMusic.playLoop();
    }
    
    public void started() {
        // Start background music
        super.started();
        bgMusic.playLoop();
    }
    
    public void stopped() {
        // Stop background music
        super.stopped();
        bgMusic.stop();
        CookieRocket.launchSound.stop();
    }
    
    public void act() {
        // Start simulation after delay
        if(startDelay > 0) {
            startDelay--;
            if(startDelay == 0) {
                p1.start();
                p2.start();
            }
            return;
        }
        
        handleActiveStateButtons();
    }
    /**
     * Returns the player that is NOT [Player thisPlayer].
     * E.g.: if p1 is passed in, return p2, and vice versa.
     * 
     * @param thisPlayer           The reference Player.
     * @return Player               The other player
     */
    public Player getOtherPlayer(Player thisPlayer) { // Used to get the affected player in the event of a sabotage
        return thisPlayer == p1 ? p2 : p1;
    }
    
    /**
     * Returns the cost of [Class itemClass] by retrieving its [public static int COST] field
     * 
     * 
     * @param itemClass         The subclass of Building or Powerup whose cost is being returned
     * @return int              The cost in cookies, pertaining to the subclass `itemClass`
     */
    public int getItemCost(Class itemClass) {
        if(Building.class.isAssignableFrom(itemClass)) { // check if itemClass is a subclass of Building
            return (int)buildingMap.get(itemClass).get("cost");
        }
        if(Powerup.class.isAssignableFrom(itemClass)) { // check if itemClass is a subclass of Powerup
            return (int)powerupMap.get(itemClass).get("cost");
        }
        return -1;
    }
    /**
     * Returns the name of [Class itemClass] by retrieving its [public static int COST] field
     * 
     * 
     * @param itemClass         The subclass of Building or Powerup whose cost is being returned
     * @return String           The name pertaining to the subclass `itemClass`
     */
    public String getItemName(Class itemClass) {
        if(Building.class.isAssignableFrom(itemClass)) { // check if itemClass is a subclass of Building
            return (String)buildingMap.get(itemClass).get("name");
        }
        if(Powerup.class.isAssignableFrom(itemClass)) { // check if itemClass is a subclass of Powerup
            return (String)powerupMap.get(itemClass).get("name");
        }
        return null;
    }
    
    /**
     * Return an array of new `BuyButton`s, for each subclass in [Class[] itemClasses] (either `BUIDLING_CLASSES` or `POWERUP_CLASES`)
     * 
     * @param itemClasses           Array containing subclasses of Building or Powerup
     * @return BuyButton[]          Array of buttons for each subclass in `itemClasses`
     */
    public BuyButton[] initBuyButtons(ArrayList<Class> itemClasses) {
        BuyButton[] buttons = new BuyButton[itemClasses.size()];
        for(int i=0;i<buttons.length;i++) {
            Class c = itemClasses.get(i);
            BuyButton btn = new BuyButton(c, getItemName(c), getItemCost(c));
            buttons[i] = btn;
        }
        return buttons;
    }
    /**
     * Return a <code>BuyButton</code> with the class of <code>sabotageClass</code>
     * 
     * @param sabotageClass         The subclass associated with the desired BuyButton
     * @return      BuyButton       The BuyButton with the subclass of sabotageClass
     */
    public BuyButton getSabotageButton(Class sabotageClass) {
        for(BuyButton btn: buySabotageButtons) {
            if(btn.getMySubclass() == sabotageClass) {
                return btn;
            }
        }
        return null;
    }
    /**
     * Return ArrayList of Building subclasses from `buildingMap`
     * 
     * @return ArrayList<Class>     Subclasses of Building
     */
    public ArrayList<Class> getBuildingClasses() {
        return new ArrayList<Class>(buildingMap.keySet());
    }
    
    /**
     * Return ArrayList of Powerup subclasses from the <code>powerupMap</code> HashMap
     * 
     * @return ArrayList<Class>     Subclasses of Powerup
     */
    public ArrayList<Class> getPowerupClasses() {
        return new ArrayList<Class>(powerupMap.keySet());
    }
    
    /**
     * Return the CookieUpgrade BuyButton associated with the player
     * 
     * @param       player              The player whose button is being returned
     * @return      BuyButton           The BuyButton that activates player's CookieUpgrade
     */
    public BuyButton getPlayerUpgradeButton(Player player) {
        if(player == p1) {
            return cookieUpgradeButtons[0];
        }
        return cookieUpgradeButtons[1];
    }
    
    /**
     * Create a HashMap from an array of keys and values. The `keys` and `values` arrays must have the same lengths
     * 
     * @param keys                              Array of keys, as Strings (i.e. index names).
     * @param values                            Array of values
     * @return HashMap<String, Object>          HashMap comprised of the keys and values from the provided arrays
     */
    private HashMap<String, Object> createHashmap(String[] keys, Object[] values){
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        for(int i=0;i<keys.length;i++ ){
            map.put(keys[i], values[i]);
        }
        return map;
    }
    /**
     * Loop through all BuyButtons. If neither player can afford it, then set it to inactive
     */
    private void handleActiveStateButtons() {
        int maxCookies = Math.max(p1.getCookieCount(), p2.getCookieCount());
        toggleButtonArray(buyBuildingButtons, maxCookies);
        toggleButtonArray(buyPowerupButtons, maxCookies);
        toggleButtonArray(buySabotageButtons, maxCookies);
        toggleButton(winButton, maxCookies);
        // CookieUpgrade BuyButtons are only affected by their respective players' cookie counts
        toggleButton(cookieUpgradeButtons[0], p1.getCookieCount());
        toggleButton(cookieUpgradeButtons[1], p2.getCookieCount());
    }
    /**
     * Set button to active or inactive, depending on whether or not it can be afforded
     * @param btn               The BuyButton being checked
     * @param cookieCount       If cookieCount is less than the button's cost, set to inactive; otherwise, set to active
     */
    public void toggleButton(BuyButton btn, int cookieCount) {
        if(btn.getMySubclass() == MilkBottles.class) {
            toggleMilkBottlesButton();
            return;
        }
        boolean state = cookieCount > btn.getCost();
        if(!btn.isMaxedOut()) {
            btn.setActive(state);
        }
    }
    /**
     * Loop the toggleButton() method for an array of buttons
     * 
     * @param btns              The array of BuyButtons being checked
     * @param cookieCount       Compare the cost of each BuyButton to this amount of cookies
     */
    private void toggleButtonArray(BuyButton[] btns, int cookieCount) {
        for(BuyButton btn: btns) {
            toggleButton(btn, cookieCount);
        }
    }
    /**
     * Special version of the toggleButton method, specifically to handle the MilkBottles sabotage
     */
    public void toggleMilkBottlesButton() {
        BuyButton btn = getSabotageButton(MilkBottles.class);
        int p1Cost = MilkBottles.getCost(p1);
        int p2Cost = MilkBottles.getCost(p2);
        if((p1.getCookieCount() > p1Cost && p1Cost != 0) || (p2.getCookieCount() > p2Cost && p2Cost != 0)) {
            btn.setActive(true);
        } else {
            btn.setActive(false);
        }
    }
    
    /**
     * Returns the amount of cookies a player needs to win
     * 
     * @return int The amount of cookies
     */
    public static int getWinAmount() {
        return WIN;
    }
    
    /**
     * Returns the win button
     * 
     * @return WinButton The button
     */
    public WinButton getWinButton() {
        return winButton;
    }
 
    /**
     * Returns an arraylist of all building BuyButtons that the player can afford, and that the player has space for
     * 
     * @param numCookies The number of cookies the player has
     * @param player The player buying the buildings
     * @return ArrayList<BuyButton> The arraylist of BuyButtons
     */
    public ArrayList<BuyButton> getAffordableBuildingButtons(int numCookies, Player player) {
        HashMap<Class, BuildingRow> buildingRows = player.getBuildingRows();
        
        ArrayList<BuyButton> affordableButtons = new ArrayList<BuyButton>();
        for (int i = 0; i < buyBuildingButtons.length; i++) {
            Class subclass = buyBuildingButtons[i].getMySubclass();
            BuildingRow buildingRow = buildingRows.get(subclass);
            
            // Returns button if the player has room left, and they can afford it
            if (buildingRow.isRoomLeft() && numCookies >= buyBuildingButtons[i].getCost()) {
                affordableButtons.add(buyBuildingButtons[i]);
            }
        }
        
        return affordableButtons;
    }
    
    /**
     * Returns an arraylist of all powerup BuyButtons that the player can afford
     * 
     * @param numCookies The number of cookies the player has
     * @param player The player buying the powerup
     * @param cookieMaxed Whether the player has already fully upgraded their cookie
     * @return ArrayList<BuyButton> The arraylist of BuyButtons
     */
    public ArrayList<BuyButton> getAffordablePowerupButtons(int numCookies, String name, boolean cookieMaxed) {
        ArrayList<BuyButton> affordableButtons = new ArrayList<BuyButton>();
        
        if (!cookieMaxed) {
            if (name == "Player 1") {
                if (numCookies >= cookieUpgradeButtons[0].getCost()) {
                    affordableButtons.add(cookieUpgradeButtons[0]);
                    return affordableButtons;
                }
            } else {
                if (numCookies >= cookieUpgradeButtons[1].getCost()) {
                    affordableButtons.add(cookieUpgradeButtons[1]);
                    return affordableButtons;
                }
            }
        }
        
        for (int i = 0; i < buyPowerupButtons.length; i++) {
            if (numCookies >= buyPowerupButtons[i].getCost()) {
                affordableButtons.add(buyPowerupButtons[i]);
            } 
        }
        
        return affordableButtons;
    }
    
    /**
     * Returns an arraylist of all sabotage BuyButtons that the player can afford
     * 
     * @param numCookies The number of cookies the player has
     * @param player The player buying the sabotage
     * @return ArrayList<BuyButton> The arraylist of BuyButtons
     */
    public ArrayList<BuyButton> getAffordableSabotageButtons(int numCookies, Player player) {
        ArrayList<BuyButton> affordableButtons = new ArrayList<BuyButton>();
        for (int i = 0; i < buySabotageButtons.length; i++) {
            //special cost handling for MilkBottles sabotage
            boolean saboIsMilkBottles = buySabotageButtons[i].getMySubclass() == MilkBottles.class;
            int saboCost = saboIsMilkBottles ? MilkBottles.getCost(player) : buySabotageButtons[i].getCost(); 
            if (numCookies >= saboCost && saboCost != 0) {
                affordableButtons.add(buySabotageButtons[i]);
            }
        }
        
        return affordableButtons;
    }
    
    /**
     * Returns whether a player has won
     * 
     * @return boolean Whether a player has won
     */
    public boolean gameIsWon() {
        return gameWon;
    }
    
    /**
     * Sets whether the game should be over
     * 
     * @param x Whether the game is over
     */
    public void setGameWon(boolean x) {
        gameWon = x;
    }
    /**
     * @return GreenfootSound       Return the background music
     */
    public GreenfootSound getBgMusic() {
        return bgMusic;
    }
    
    /**
     * Used when DEMO_MODE = true, to allow the testing of BuyButtons during the "game".
     * @return Player               Player 1 object
     */
    public Player getP1() {
        return p1;
    }
}
