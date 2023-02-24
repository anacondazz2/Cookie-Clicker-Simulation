import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * The menu screen. Here, the user may adjust simulation settings, before hitting the
 * "Start" button, to begin the simulation.
 * 
 * @author Caden Chan
 * @version 2022.11.22
 */
public class MenuWorld extends World
{
    private int[] grandmas, clickers, speeds; // modifiable simulation parameters
    private GreenfootImage bg;
    // Start simulation button
    private StartButton startButton;
    // Setting displays w/ buttons
    private MenuSetting p1_grandmaSetting, p1_clickerSetting, p1_speedSetting, p2_grandmaSetting, p2_clickerSetting, p2_speedSetting;
    private Label p1_title, p2_title;
    // Clicker and Grandma previewer arrays
    private ArrayList<PreviewClicker> p1_previewClickers, p2_previewClickers;
    private ArrayList<PreviewGrandma> p1_previewGrandmas, p2_previewGrandmas;
    // Min/Max values for each setting
    private final int[] gMinMax = {0, 5};       // {min,max} starting grandma counts
    private final int[] cMinMax = {0, 5};       // {min,max} clicker counts
    private final int[] speedMinMax = {1, 5};     // {min,max} speed
    // Preset Grandma previewer positions
    private final int[][] p1_previewGrandmaPos = getGrandmaPositions(400, 200, 1);
    private final int[][] p2_previewGrandmaPos = getGrandmaPositions(800, 200, -1);
    // Preset Clicker previewer positions
    private final int[] p1_previewClickerPos = new int[]{450, 420};
    private final int[] p2_previewClickerPos = new int[]{750, 420};
    // Background Music
    GreenfootSound[] tracks;
    /**
     * Constructor for the MenuWorld, where the user makes selections on their simulation settings.
     * @param tracks            The world's background music tracks.
     */
    public MenuWorld(GreenfootSound[] tracks)
    {    
        super(1200, 800, 1); 
        // Background
        bg = new GreenfootImage("menu-background.png");
        setBackground(bg);
        // Music
        this.tracks = tracks;
        // Start button
        startButton = new StartButton();
        addObject(startButton, 600, 640);
        // Player 1 Settings
        p1_title = new Label("Player 1", 60);
        p1_title.setFillColor(Color.RED);
        p1_title.setLineColor(Color.WHITE);
        p1_grandmaSetting = new MenuSetting(gMinMax[0], gMinMax[1], 200, 300, "Number of\nGrandmas", 0, 1);
        p1_clickerSetting = new MenuSetting(cMinMax[0], cMinMax[1], 200, 500, "Number of\nClickers", 1, 1);
        p1_speedSetting = new MenuSetting(speedMinMax[0], speedMinMax[1], 200, 700, "Clicker Speed", 2, 1);
        addObject(p1_title, 200, 100);
        addObject(p1_grandmaSetting, 0, 0);
        addObject(p1_clickerSetting, 0, 0);
        addObject(p1_speedSetting, 0, 0);
        // Player 2 Settings
        p2_title = new Label("Player 2", 60);
        p2_title.setFillColor(Color.BLUE);
        p2_title.setLineColor(Color.WHITE);
        p2_grandmaSetting = new MenuSetting(gMinMax[0], gMinMax[1], 1000, 300, "Number of\nGrandmas", 0, 2);
        p2_clickerSetting = new MenuSetting(cMinMax[0], cMinMax[1], 1000, 500, "Number of\nClickers", 1, 2);
        p2_speedSetting = new MenuSetting(speedMinMax[0], speedMinMax[1], 1000, 700, "Clicker Speed", 2, 2);
        addObject(p2_title, 1000, 100);
        addObject(p2_grandmaSetting, 0, 0);
        addObject(p2_clickerSetting, 0, 0);
        addObject(p2_speedSetting, 0, 0);
        // add PreviewGrandmas
        p1_previewGrandmas = new ArrayList<PreviewGrandma>();
        p2_previewGrandmas = new ArrayList<PreviewGrandma>();
        for(int i=0;i<gMinMax[0];i++) {
            addPreviewGrandma(1);
            addPreviewGrandma(2);
        }
        // add PreviewClickers
        p1_previewClickers = new ArrayList<PreviewClicker>();
        p2_previewClickers = new ArrayList<PreviewClicker>();
        for(int i=0;i<cMinMax[0];i++) {
            addPreviewClicker(1);
            addPreviewClicker(2);
        }
    }
    // Program started (via Greenfoot)
    public void started() {
        super.started();
        tracks[0].playLoop();
    }
    // Program stopped (via Greenfoot)
    public void stopped() {
        super.stopped();
        tracks[0].pause();
    }
    /**
     * Start the simulation. Pass in the user's selected settings into CookieWorld. Stop the menu background music
     */
    public void start() {
        grandmas = new int[]{p1_grandmaSetting.getCount(), p2_grandmaSetting.getCount()};
        clickers = new int[]{p1_clickerSetting.getCount(), p2_clickerSetting.getCount()};
        speeds = new int[]{p1_speedSetting.getCount(), p2_speedSetting.getCount()};
        tracks[0].stop();
        Greenfoot.setWorld(new CookieWorld(tracks[1], grandmas, clickers, speeds));
    }
    /**
     * Display a PreviewGrandma object to the Menu. Position is based on p1_previewGrandmaPos and p2_previewGrandmaPos
     * 
     * @param player            The ID of the player whose starting grandma count is increased
     */
    public void addPreviewGrandma(int player) {
        PreviewGrandma grandma = new PreviewGrandma();
        int[] pos;
        // Player 1
        if(player == 1) {
            p1_previewGrandmas.add(grandma);
            pos = p1_previewGrandmaPos[p1_previewGrandmas.size()-1];
            addObject(grandma, pos[0], pos[1]);
        // Player 2
        } else {
            p2_previewGrandmas.add(grandma);
            pos = p2_previewGrandmaPos[p2_previewGrandmas.size()-1];
            addObject(grandma, pos[0], pos[1]);
        }
    }
    /**
     * Remove a PreviewGrandma object from the Menu
     * 
     * @param player            The ID of the player whose starting grandma count is decreased
     */
    public void removePreviewGrandma(int player) {
        PreviewGrandma grandma;
        int idx;
        // Player 1
        if(player == 1) {
            idx = p1_previewGrandmas.size()-1;  // remove last GrandmaPreview in array
            grandma = p1_previewGrandmas.get(idx);
            p1_previewGrandmas.remove(idx);
        // Player 2
        } else {
            idx = p2_previewGrandmas.size()-1;  // remove last GrandmaPreview in array
            grandma = p2_previewGrandmas.get(idx);
            p2_previewGrandmas.remove(idx);
        }
        removeObject(grandma);
    }
    /**
     * Display a PreviewClicker object with the appropriate speed to the Menu.
     * 
     * @param player                The ID of the player whose clicker count is increased
     */
    public void addPreviewClicker(int player) {
        PreviewClicker clicker;
        // Player 1
        if(player == 1) {
            clicker = new PreviewClicker(80, p1_clickerSetting.getCount()+2);
            p1_previewClickers.add(clicker);
            addObject(clicker, p1_previewClickerPos[0], p1_previewClickerPos[1]);
        // Player 2
        } else {
            clicker = new PreviewClicker(80, p2_clickerSetting.getCount()+2);
            p2_previewClickers.add(clicker);
            addObject(clicker, p2_previewClickerPos[0], p2_previewClickerPos[1]);
        }
    }
    /**
     * Remove a PreviewClicker object from the Menu.
     * 
     * @param player            The ID of the player whose clicker count is decreased
     */
    public void removePreviewClicker(int player) {
        PreviewClicker clicker;
        int idx;
        // Player 1
        if(player == 1) {
            idx = p1_previewClickers.size()-1;   // remove the last clicker
            clicker = p1_previewClickers.get(idx);
            p1_previewClickers.remove(idx);
        // Player 2
        } else {
            idx = p2_previewClickers.size()-1;   // remove the last clicker
            clicker = p2_previewClickers.get(idx);
            p2_previewClickers.remove(idx);
        }
        removeObject(clicker);
    }
    /**
     * Change the speed of the PreviewClickers belonging to the given player. This reflects what the clicker speed
     * would be in the simulation itself.
     * 
     * @param player                The ID of the player whose CPS is altered
     * @param newSpeed                The player's clickers' new CPS
     */
    public void editSpeed(int player, int newSpeed) {
        // Player 1
        if(player == 1) {
            for(PreviewClicker clicker: p1_previewClickers) {
                clicker.setSpeed(2 + newSpeed);
            }
        // Player 2
        } else {
            for(PreviewClicker clicker: p2_previewClickers) {
                clicker.setSpeed(2 + newSpeed);
            }
        }
    }
    /**
     * Calculate an array, of length gMinMax[1], of positions for PreviewGrandmas
     * 
     * @param startX            x-value of the first PreviewGrandma
     * @param startY            y-value of the first PreviewGrandma
     * @param d                 d = 1 or -1.        |       1 -> new PreviewGrandma objects are placed to the right. -1 -> new PreviewGrandma objects are placed to the left.
     * 
     * @return int[][]          Array of {x,y} positions for PreviewGrandma objects
     */
    private int[][] getGrandmaPositions(int startX, int startY, int d) {
        int[][] positions = new int[gMinMax[1]][];  // max number of grandmas to be displayer = gMinMax[1]
        for(int i=0;i<gMinMax[1];i++) {
            int yOffset = i%2 == 1 ? 50 : 0;  // zig zag between yoffset of 50 and 0
            int[] pos = {startX + i*30*d, startY + yOffset};  // x-value is spaced by 30 pixels per grandma
            positions[i] = pos;
        }
        return positions;
    }
}
