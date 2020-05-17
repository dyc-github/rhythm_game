package rhythm_game;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * 
 * This represents an lane of arrows
 *
 * @author Ryan_Tai,David_Choi
 * @version May 11, 2020
 * @author Period: 1
 * @author Assignment: RhythmGame
 *
 * @author Sources: None
 */
public class ArrowLane
{

    private final int xPos;

    private static final int goal = 800;// change later

    private LinkedList<Arrow> arrows;

    private final Direction direction;


    /**
     * 
     * @param xPos
     *            the xCoordinate of all arrows in the lane
     * @param direction
     *            the direction of all arrows in the lane
     */
    public ArrowLane( int xPos, Direction direction )
    {
        this.direction = direction;
        this.xPos = xPos;
        arrows = new LinkedList<Arrow>();
    }


    /**
     * 
     * Adds an arrow to the end/top of the arrowLane
     */
    public void add()
    {
        arrows.add( new Arrow( this ) );
    }


    /**
     * 
     * Checks how close the closest arrow is and assigns a score depending on
     * the closeness.
     * 
     * @return score 0 means it is too far to count 1,2,3 means bad, good, and
     *         perfect respectively
     */
    public int compare()
    {
        if ( arrows.isEmpty() )
        {
            return 0;
        }
        Arrow arrow;
        int diff;
        int score = 0;
        int position = 0;
        while ( position + 1 < arrows.size() && arrows.get( position ).getY() > goal )
        {
            position++;
        }
        arrow = arrows.get( position );
        diff = Math.abs( arrow.getY() - goal );
        if ( position + 1 < arrows.size() )
        {
            int diff2 = Math.abs( arrows.get( position + 1 ).getY() - goal );
            if ( diff2 < diff )
            {
                diff = diff2;
            }

        }
        if ( diff <= 25 )
        {
            score = 3; // FYI 3 is best
        }
        else if ( diff <= 50 )
        {
            score = 2;
        }
        else if ( diff <= 75 )
        {
            score = 1;
        }
        else
        {
            score = 0;
            position++;
        }

        if ( score > 0 )
        {
            arrows.remove( position );
        }
        return score;
    }


    /**
     * 
     * Removes the arrow closest to the bottom of the screen
     */
    public void remove()
    {
        arrows.remove();
    }


    /**
     * 
     * Gives the xPos of all arrows in the lane
     * 
     * @return the xPosition of th arrows the lane is storing
     */
    public int getX()
    {
        return xPos;
    }


    // There is an error when an arrow is removed due to the size of the list
    // changing so if we make an array with the copy, the error dissapears since
    // the program draws according to the array. There is a cast exception error
    // when the code first starts but it dissapears which probably means that it
    // might not be the best way
    /**
     * 
     * Moves all arrows in the lane by iterating through the linked list and
     * calling each arrow's move
     * 
     */
    public void move()
    {
        Object[] arrowArr = arrows.toArray();
        for ( Object a : arrowArr )
        {
            ( (Arrow)a ).move();
        }
    }


    /**
     * 
     * Draws all arrows in the lane by calling the draw method for each arrow
     * 
     * @param g2
     *            the 2D graphics of the screen
     */
    public void draw( Graphics2D g2 )
    {
        Iterator<Arrow> arrowsIterator = arrows.descendingIterator();
        while ( arrowsIterator.hasNext() )
        {
            Arrow arrow = arrowsIterator.next();
            arrow.draw( g2, direction );
        }
    }


    /**
     * 
     * Returns the yPosition of the goal
     * 
     * @return the yPosition of the goal
     */
    public static int getGoal()
    {
        return goal;
    }

}
