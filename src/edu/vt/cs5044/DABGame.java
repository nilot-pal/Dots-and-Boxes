package edu.vt.cs5044;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that implements the methods defined by the interface DotsAndBoxes
 * This game is played on a matrix pattern of dots, 
 * with each dot representing one vertex of a grid of neighboring, 
 * non-overlapping 1x1 boxes. Each turn involves the current player 
 * drawing a single straight line to connect any two unconnected adjacent dots, 
 * either horizontally or vertically. The drawn line represents one edge of 
 * at least one box. Note that each edge is shared between two neighboring boxes, 
 * except for boxes located along the outer-most grid boundaries.
 * Play begins with Player ONE taking a turn to draw an edge. 
 * If no box is completed during a turn, it becomes the opponent's turn. 
 * If one or two boxes are completed during a turn, then the newly-completed box 
 * or boxes become owned by the player, and the same player must take another turn. 
 * Invalid moves, such as drawing an already-drawn edge, or specifying a box 
 * location outside of the grid, don't count as a turn.
 * Play continues until all the boxes are owned, at which point the game is over. 
 * The winner is the player who owns the most boxes. A game with an 
 * even number of boxes may end in a tie.
 * @author nilotpal
 * @version Mar 27, 2023
 *
 */
public class DABGame implements DotsAndBoxes {
    
    private Player currPlayer;
    private int size;
    private Map<Coordinate, Box> boxGrid;
    /**
     * boolean value that tells whether grid has been initialized or not
     */
    boolean gridInitialized;
    /**
     * 
     * Construct a new DABGame object.
     *
     */
    public DABGame() {
        currPlayer = null;
        boxGrid = null;
        size = -1;
        gridInitialized = false;
    }

    @Override
    public boolean drawEdge(Coordinate coord, Direction dir) {
        validateGridInitialized();
        Box box = fetchBox(coord);
        if (box.isDrawnEdgeAt(dir)) {
            return false;
        }
        Coordinate neighborCoord = coord.getNeighbor(dir);
        boolean neighborBoxComplete = false;
//        if (boxGrid.containsKey(neighborCoord)) {
        try {
            Box neighborBox = fetchBox(neighborCoord); // get the neighbor box
            neighborBox.drawEdge(dir.getOpposite(), currPlayer); 
            if (neighborBox.getDrawnEdges().size() == 4) {
                neighborBoxComplete = true;
            }
        }
        catch (GameException ex) {
            //
        }
            
        box.drawEdge(dir, currPlayer); // this step assigns box its owner if box is complete
        if (box.getDrawnEdges().size() != 4 && !neighborBoxComplete) {
            currPlayer = currPlayer.getOpponent();
        }
        return true;
    }


    @Override
    public Player getCurrentPlayer() {
        validateGridInitialized();
        return getPlayer();
    }
    
/**
 *    
 * Helper method to return current Player after a turn, but null if the game is over.
 * Used by the public method getCurrentPlayer
 * @return returns current player or null
 */
    private Player getPlayer() {
        for (Box box: boxGrid.values()) {
            if (box.getDrawnEdges().size() != 4) {
                return currPlayer;
            }
        }
        return null;
    }
    
    @Override
    public Collection<Direction> getDrawnEdgesAt(Coordinate coord) {
        validateGridInitialized();
        return fetchBox(coord).getDrawnEdges(); // returns box.drawnEdges - a set of directions
    }

    @Override
    public Player getOwnerAt(Coordinate coord) {
        validateGridInitialized();
        Box box = fetchBox(coord);
        return box.getOwner();
    }
    
/**
 * 
 * private method to fetch box from its coordinate
 * used by public methods drawEdge(), getDrawnEdgesAt(),getOwnerAt() and getScores()
 *
 * @param coord Coordinate
 * @return returns box
 */
    private Box fetchBox(Coordinate coord) {
        Box box = boxGrid.get(coord);
        if (box == null) {
            throw new GameException("Invalid coordinate!");
        }
        return box;
    }
    /**
     * 
     * Private helper method to check if grid has been initialized 
     * before calling any of the public methods
     *
     */
    private void validateGridInitialized() {
        if (!gridInitialized) {
            throw new GameException("Grid is not initialized!");
        }
    }

    @Override
    public Map<Player, Integer> getScores() {
        validateGridInitialized();
        Map<Player, Integer> scores = new HashMap<>();
        scores.put(Player.ONE, 0);
        scores.put(Player.TWO, 0);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Coordinate coord = new Coordinate(x, y);
                Box box = fetchBox(coord);
                if (box.getOwner() == Player.ONE) {
                    scores.put(Player.ONE, scores.get(Player.ONE) + 1);
                }
                else if (box.getOwner() == Player.TWO) {
                    scores.put(Player.TWO, scores.get(Player.TWO) + 1);
                }
            }
        }
        return scores;
    }

    @Override
    public int getSize() {
        validateGridInitialized();
        return size;
    }

    @Override
    public void init(int size) {
        if (size < 2) {
            throw new GameException("The grid size is less than 2!");
        }
        this.size = size;
        currPlayer = Player.ONE;
        boxGrid = new HashMap<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                boxGrid.put(new Coordinate(x, y), new Box());
            }
        }
        gridInitialized = true;
    }

}
