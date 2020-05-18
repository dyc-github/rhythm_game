
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;


public class ArrowLane
{

    private final int xPos;

    private static int goal;// change later

    private LinkedList<Arrow> arrows;

    private final Direction direction;


    public ArrowLane( int xPos, Direction direction, int screenSize)
    {
        this.direction = direction;
        this.xPos = xPos;
        arrows = new LinkedList<Arrow>();
        setGoal(screenSize);
    }


    public void add()
    {
        arrows.add( new Arrow( this ) );
    }


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
        do
        {
            arrow = arrows.get( position );
            diff = Math.abs( arrow.getY() - goal );
            if ( diff <= 27 )
            { // TODO define perfect
                score = 3; // FYI 3 is best
            }
            else if ( diff <= 60 )
            { // TODO define ok
                score = 2;
            }
            else if ( diff <= 100 )
            { // TODO define bad
                score = 1;
            }
            else
            {
                score = 0;
                position++;
            }
            
        } while (score == 0 && arrow.getY()>goal-100 && position<arrows.size());
        if (score>0) {
            arrows.remove( position );
        }
        
        return score;
    }


    public void remove()
    {
        arrows.remove();
    }


    public int getX()
    {
        return xPos;
    }


    // There is an error when an arrow is removed due to the size of the list
    // changing so if we make an array with the copy, the error dissapears since
    // the program draws according to the array. There is a cast exception error
    // when the code first starts but it dissapears which probably means that it
    // might not be the best way
    public void move()
    {
        Object[] arrowArr = arrows.toArray();
        for ( Object a : arrowArr )
        {
            ( (Arrow)a ).move();
        }
    }


    public void draw( Graphics2D g2 )
    {
        Iterator<Arrow> arrowsIterator = arrows.descendingIterator();
        while ( arrowsIterator.hasNext() )
        {
            Arrow arrow = arrowsIterator.next();
            arrow.draw( g2, direction );
        }
    }


    public static int getGoal()
    {
        return goal;
    }
    public static void setGoal(int screenSize){
    	goal = screenSize - Arrow.getHalfHeight() * 2;
    }
    


}
