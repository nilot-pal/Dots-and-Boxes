package edu.vt.cs5044;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Class for Box object
 * Used to reduce complexity of the code by delegating some tasks of the interface to this class
 * Has fields owner and drawnEdges
 *
 * @author nilotpal
 * @version Mar 27, 2023
 *
 */
public class Box {
    
    private Player owner;
    private Set<Direction> drawnEdges;
    
    /**
     * 
     * Construct a new Box object.
     *
     */
    public Box() {
        owner = null;
        drawnEdges = new HashSet<>();
        
    }
    
    /**
     * 
     * get the owner of a box
     *
     * @return return owner of box
     */
    public Player getOwner() {
        return owner;
    }
    
    /**
     * 
     * get the set of directions in which drawn edges exist
     *
     * @return returns set of directions
     */
    public Set<Direction> getDrawnEdges() { // I need to return a copy of drawnEdges)
        return new HashSet<>(drawnEdges);
    }
    
    /**
     * 
     * check if a drawn edge exists in that direction
     *
     * @param dir direction
     * @return returns true or false
     */
    public boolean isDrawnEdgeAt(Direction dir) {
        nullDirection(dir);
        return drawnEdges.contains(dir);
    }

    /**
     * 
     * draw an edge in that direction
     * if box is complete, assign owner status to the current Player
     *
     * @param dir Direction
     * @param currPlayer current Player
     */
    public void drawEdge(Direction dir, Player currPlayer) {
        nullDirection(dir);
        drawnEdges.add(dir);
        if (isBoxCompleted()) {
            owner = currPlayer;
        }
    }
    
    /**
     * 
     * helper method that throws GameException if direction is null
     *
     * @param dir direction
     */
    private void nullDirection(Direction dir) {
        if (dir == null) {
            throw new GameException("Direction is null!");
        }
    }

    /**
     * 
     * private method to check if box is complete with 4 drawn edges
     *
     * @return returns true or false
     */
    private boolean isBoxCompleted() {
        return drawnEdges.size() == 4;
    }


}
