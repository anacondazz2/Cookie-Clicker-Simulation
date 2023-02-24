import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.ArrayList;
/**
 * 40-60% of the opponents' Grandma objects become angry, and begin a revolution!
 * Instead of baking cookies, they will savagely (or as savage as a Grandma can be) destroy the opponent player's cookies
 * 
 * @author Caden Chan, Patrick Hu, Jonathan Zhao
 * @version 2022.11.22
 */
public class GrandmaRevolution extends Sabotage
{
    private double percent;  // The percentage of the opponent's grandmas that get angered 
    /**
     * @param origin            The Player that activated this GrandmaRevolution
     */
    public GrandmaRevolution(Player origin) {
        super(origin);
    }
    /**
     * Anger 20-30% of [Player target]'s Grandma objects
     * @param w
     */
    public void addedToWorld(World w) {
        super.addedToWorld(w);
        percent = getRandomNumberInRange(40, 60); // 40-60% of grandmas get angered
        angerGrandmas();
    }
    
    public void act() {
        if(actCount == duration) {
            getWorld().removeObject(this);
            return;
        }
        actCount++;
    }
    /**
     * Anger a number of the opponents' Grandma objects, depending on the <code>percent</code> instance variable
     */
    private void angerGrandmas() {
        // Get array of the opponents' non-angry grandmas. target = opponent
        ArrayList<Grandma> grandmaBuildings = getTargetGrandmaBuildings();
        ArrayList<Grandma> notAngryGrandmas = new ArrayList<Grandma>();
        for (Grandma granny : grandmaBuildings) {
            if (!granny.isAngry()) {
                notAngryGrandmas.add(granny);
            }
        }
        if(notAngryGrandmas.size() == 0) { // if all grandmas are angry, do nothing
            return;
        }
        int angerCount = (int)(notAngryGrandmas.size() * (percent / 100.0)); // how many Grandmas get angered, based on `percent` variable
        angerCount = angerCount == 0 ? 1 : angerCount;  // at least 1 grandma will be angered
        for(int i=0;i<angerCount;i++) {
            int idx = Greenfoot.getRandomNumber(notAngryGrandmas.size());
            notAngryGrandmas.get(idx).setAngry(true); // anger a random Grandma
            notAngryGrandmas.remove(idx);  // remove newly angered Grandma from array of non-angry Grandma objects
        }
    }
    /**
     * @return ArrayList<Grandma>           An ArrayList of the opponents' Grandma objects
     */
    private ArrayList<Grandma> getTargetGrandmaBuildings() {
        HashMap<Class, BuildingRow> buildingRows = target.getBuildingRows();
        BuildingRow grandmaBuildingRow = buildingRows.get(Grandma.class);
        return (ArrayList<Grandma>)(ArrayList<?>)grandmaBuildingRow.getBuildings();
    }
}