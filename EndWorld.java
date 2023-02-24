import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The ending screen when a player wins
 * 
 * @author Eddie Zhuang
 * @version November 2022
 */
public class EndWorld extends World
{/**
     * Constructor for objects of class EndWorld.
     * 
     * @param player The player who won
     */
    public EndWorld(Player player) {    
        super(1200, 800, 1);
        
        // Draw background based on which player won
        if(player.getName().equals("Player 1")) {
            GreenfootImage bg = new GreenfootImage("endbg1.png");
            setBackground(bg);
        }
        else {
            GreenfootImage bg = new GreenfootImage("endbg2.png");
            setBackground(bg);
        }
        
        
        
        // Add text
        Label playerLabel = new Label(player.getName() + " wins!", 60);
        addObject(playerLabel, 600, 100);
    }
}
