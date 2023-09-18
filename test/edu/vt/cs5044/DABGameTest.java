package edu.vt.cs5044;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Class for unit tests
 * creates a new game grid and plays the game
 * until game is over. Reinitializes the game after it's over
 * @author nilotpal
 * @version Mar 27, 2023
 *
 */
public class DABGameTest {
    
    private DotsAndBoxes game;
    
    /**
     * 
     * create new instance of DABGame class
     *
     */
    @Before
    public void setup() {
        game = new DABGame();
    }
    
    /**
     * 
     * test the init method of DABGame class
     *
     */
    @Test
    public void testInitGame() {
        game.init(3);
        assertEquals(Player.ONE, game.getCurrentPlayer());
        assertEquals(3, game.getSize());
    }
    
    /**
     * 
     * test whether calling a method without initializing the grid throws GameException
     *
     */
    @Test
    public void testCallBeforeInitGame() {
        try {
            assertEquals(null, game.getScores());
            fail("Grid needs to be initialized first");
        }
        catch (GameException ex) {
            //pass
        }
        
    }
    /**
     * 
     * Alternate way to check exceptions
     *
     */
//    @Test(expected = GameException.class)
//    public void testCallBeforeInitGameOtherWay() {
//        game.getScores();
//        
//    }
    
    /**
     * 
     * test to check whether size of the game grid is valid or not
     *
     */
    @Test
    public void testInitSizeInvalid() {
        try {
            game.init(1);
            fail("Program accepted illegal grid size!");
        }
        catch (GameException ex) {
            //pass
        }
        
    }
    
    /**
     * 
     * test to check whether code throws GameException when direction is null
     *
     */
    @Test
    public void testDirectionNull() {
        try {
            game.init(2);
            game.drawEdge(new Coordinate(0, 0), null);
            fail("Program drew edge in null direction!");
        }
        catch (GameException ex) {
            //pass
        }
        
    }
    
    /**
     * 
     * test the getDrawnEdges method before a box has no drawn edges
     *
     */
    @Test
    public void testBeforeDrawEdges() {
        game.init(2);
        Set<Direction> emptyEdges = new HashSet<>();
        assertEquals(emptyEdges, game.getDrawnEdgesAt(new Coordinate(0, 0)));
        //assertTrue(game.getDrawnEdgesAt(new Coordinate(0, 0)).isEmpty());
    }
    
    /**
     * 
     * test the drawEdge method by playing the game until it's over
     *
     */
    @Test
    public void testDrawEdgeUntilGameOver() {
        game.init(2);
        Set<Direction> expectedEdges = new HashSet<>();
        expectedEdges.add(Direction.BOTTOM);
        
        game.drawEdge(new Coordinate(0, 0), Direction.BOTTOM);
        assertEquals(expectedEdges, game.getDrawnEdgesAt(new Coordinate(0, 0)));
        assertEquals(Player.TWO, game.getCurrentPlayer());
        
        boolean edgeDrawn = game.drawEdge(new Coordinate(0, 0), Direction.BOTTOM);
        assertEquals(false, edgeDrawn);
        assertEquals(Player.TWO, game.getCurrentPlayer());
        
        edgeDrawn = game.drawEdge(new Coordinate(0, 0), Direction.TOP); 
        assertEquals(true, edgeDrawn);
        assertEquals(Player.ONE, game.getCurrentPlayer());
        
        edgeDrawn = game.drawEdge(new Coordinate(0, 0), Direction.LEFT); 
        assertEquals(true, edgeDrawn);
        assertEquals(Player.TWO, game.getCurrentPlayer());
        
        edgeDrawn = game.drawEdge(new Coordinate(0, 0), Direction.RIGHT); 
        edgeDrawn = game.drawEdge(new Coordinate(0, 1), Direction.LEFT); 
        edgeDrawn = game.drawEdge(new Coordinate(0, 1), Direction.BOTTOM); 
        edgeDrawn = game.drawEdge(new Coordinate(1, 1), Direction.TOP); 
        edgeDrawn = game.drawEdge(new Coordinate(1, 1), Direction.BOTTOM);
        edgeDrawn = game.drawEdge(new Coordinate(1, 1), Direction.RIGHT); 
        edgeDrawn = game.drawEdge(new Coordinate(1, 1), Direction.LEFT);
        
        assertEquals(Player.TWO, game.getOwnerAt(new Coordinate(0, 0)));
        
        game.getScores();
        
        edgeDrawn = game.drawEdge(new Coordinate(1, 0), Direction.RIGHT);
        edgeDrawn = game.drawEdge(new Coordinate(1, 0), Direction.TOP);  
        assertEquals(null, game.getCurrentPlayer());
    }
    
    /**
     * 
     * test the drawEdge method in a way that drawing an edge completes the neighbor box
     *
     */
    @Test
    public void testDrawEdgeNeighborBoxComplete() {
        game.init(2);
        Set<Direction> expectedEdges = new HashSet<>();
        expectedEdges.add(Direction.BOTTOM);
        
        game.drawEdge(new Coordinate(0, 0), Direction.BOTTOM);
        assertEquals(expectedEdges, game.getDrawnEdgesAt(new Coordinate(0, 0)));
        assertEquals(Player.TWO, game.getCurrentPlayer());
        
        boolean edgeDrawn = game.drawEdge(new Coordinate(0, 0), Direction.BOTTOM);
        assertEquals(false, edgeDrawn);
        assertEquals(Player.TWO, game.getCurrentPlayer());
        
        edgeDrawn = game.drawEdge(new Coordinate(0, 0), Direction.TOP); 
        assertEquals(true, edgeDrawn);
        assertEquals(Player.ONE, game.getCurrentPlayer());
        
        edgeDrawn = game.drawEdge(new Coordinate(0, 0), Direction.LEFT); 
        assertEquals(true, edgeDrawn);
        assertEquals(Player.TWO, game.getCurrentPlayer());
        
        edgeDrawn = game.drawEdge(new Coordinate(0, 0), Direction.RIGHT); 
        edgeDrawn = game.drawEdge(new Coordinate(1, 1), Direction.BOTTOM);
        edgeDrawn = game.drawEdge(new Coordinate(1, 1), Direction.RIGHT); 
        edgeDrawn = game.drawEdge(new Coordinate(1, 1), Direction.LEFT);
        edgeDrawn = game.drawEdge(new Coordinate(1, 0), Direction.BOTTOM); 
        assertEquals(Player.ONE, game.getOwnerAt(new Coordinate(1, 1)));
        
        game.getScores();
    }
}
