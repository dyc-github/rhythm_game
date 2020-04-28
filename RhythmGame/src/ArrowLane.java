import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;


public class ArrowLane
{

    private final int xPos;

    private static final int goal = 13;// change later

    private LinkedList<Arrow> arrows;

    private final Direction direction;


    public ArrowLane( int xPos, Direction direction )
    {
        this.direction = direction;
        this.xPos = xPos;
        arrows = new LinkedList<Arrow>();
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
        Arrow arrow = arrows.peek();
        int diff = Math.abs( arrow.getY() - goal );

        // TODO define screensize and half arrow height

        if ( diff <= 10 )
        { // TODO define perfect
            return 3; // FYI 3 is best
        }
        else if ( diff <= 25 )
        { // TODO define ok
            return 2;
        }
        else if ( diff <= 50 )
        { // TODO defien bad
            return 1;
        }
        else
        {
            return 0;
        }
    }


    public void remove()
    {
        arrows.remove();
    }


    public int getX()
    {
        return xPos;
    }


    public void move()
    {
        for ( Arrow a : arrows )
        {
            a.move();
        }
    }


    public void draw( Graphics2D g2 )
    {
        Iterator<Arrow> arrowsIterator = arrows.descendingIterator();
        System.out.println( "arrowLane draw" );
        while ( arrowsIterator.hasNext() )
        {
            Arrow arrow = arrowsIterator.next();
            arrow.draw( g2, direction );
        }
    }

}
