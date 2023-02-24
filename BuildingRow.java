import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * The rectangles in which the buildings will stay
 * 
 * @author Eddie Zhuang
 * @version November 2022
 */
public class BuildingRow extends Clickable
{
    private Class buildingType;
    private int spacing;
    private Player player;
    private ArrayList<Building> buildings = new ArrayList<Building>();
    
    /**
     * @param buildingType The class of building that will live in this row
     * @param width The width of the row
     * @param height The height of the row
     * @param spacing How far apart each building will be
     */
    public BuildingRow(Player player, Class buildingType, int width, int height, int spacing) {
        this.player = player;
        this.buildingType = buildingType;
        this.spacing = spacing;
        
        setImage(drawBuildingRow(width, height));
    }
    
    /**
     * Adds a building to this row, which starts at an x and y position and glides over to the building row
     * 
     */
    public void addBuilding(int x, int y) {
        if (!isRoomLeft()) return;
        
        try {
            // Create the building
            Constructor<Building> c = buildingType.getConstructor(Player.class);
            Building building = c.newInstance(player);
            getWorld().addObject(building, x, y);
            
            // Make the building move towards the appropriate position
            int buildingX;
            int imageWidth = building.getImage().getWidth();
            if (buildings.isEmpty()) {
                buildingX = getX() - (getImage().getWidth() / 2) + (imageWidth / 2) + 7;
            } else {    
                buildingX = (int)buildings.get(buildings.size() - 1).getTargetX() + (imageWidth / 2) + spacing;
            }
            building.startGlidingTo(buildingX, getY(), 15);
            
            // Add building to array
            buildings.add(building);
        } catch(Exception e) {}
    }
    private GreenfootImage drawBuildingRow(int width, int height) {
        GreenfootImage ret = new GreenfootImage(width, height);
        
        // Draw inside
        ret.setColor(new Color(194, 178, 128));
        ret.fillRect(0, 0, width, height);
        
        // Draw border
        ret.setColor(new Color(38, 20, 8));
        ret.drawRect(0, 0, width, height);
        
        return ret;
    }
    
    /**
     * Returns whether this row has enough space for another building
     */
    public boolean isRoomLeft() {
        if (buildings.isEmpty()) {
            return true;
        } else {
            Building lastBuilding = buildings.get(buildings.size() - 1);
            int buildingEdge = (int)lastBuilding.getTargetX() + spacing + lastBuilding.getImage().getWidth();
            int rowEdge = getX() + (getImage().getWidth() / 2);
            return buildingEdge < rowEdge;
        }
    }
    
    /**
     * Returns the buildings in this row
     */
    public ArrayList<Building> getBuildings() {
        return buildings;
    }
}
