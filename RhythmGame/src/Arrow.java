package rhythm_game;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 *  Represents a single arrow that moves down the screen
 *
 *  @author  Ryan_Tai,David_Choi
 *  @version May 11, 2020
 *  @author  Period: 1
 *  @author  Assignment: RhythmGame
 *
 *  @author  Sources: None
 */
public class Arrow
{   
    private int yPos;
    private static final int YVEL = 5;
    private static final int SHIFT= 50;
    private static Color green = new Color(75, 210, 80);
    private static Color red = new Color(195,30,30);
    private static Color yellow = new Color(215,210,15);
    private static Color blue = new Color(33,160,210);
    private ArrowLane lane;
    /**
     * Creates an arrow in the given arrow lane
     * @param al the arrow lane the arrow is in
     */
    public Arrow(ArrowLane al) {
        yPos = 50;
        lane = al;
    }
    
    /**
     * 
     * Moves the arrow by its velocity
     */
    public void move() {
        yPos += YVEL;
        if (yPos > RhythmGame.getScreenHeight()) {
            lane.remove();
        }
    }
    
    /**
     * 
     * Draws the arrow on the screen
     * @param g2 the 2D graphics of the screen
     * @param dir the direction of the arrow
     */
    public void draw(Graphics2D g2, Direction dir) {
        int x = lane.getX();
        switch(dir) {
            case DOWN:
                int[] xcoor = {x-SHIFT,x,x+SHIFT};
                int[] ycoor = {yPos - SHIFT,yPos + SHIFT,yPos - SHIFT};
                g2.setColor( green );
                g2.fillPolygon( xcoor, ycoor, 3 );
                g2.setColor( Color.black );
                g2.drawPolygon( xcoor, ycoor, 3 );
                break;
            case LEFT:
                int[] xcoor1 = {x-SHIFT,x+SHIFT,x+SHIFT};
                int[] ycoor1 = {yPos,yPos - SHIFT,yPos+SHIFT};
                g2.setColor( red );
                g2.fillPolygon( xcoor1, ycoor1, 3 );
                g2.setColor( Color.black );
                g2.drawPolygon( xcoor1, ycoor1, 3 );
                break;
            case RIGHT:
                int[] xcoor2 = {x + SHIFT,x-SHIFT,x-SHIFT};
                int[] ycoor2 = {yPos,yPos+SHIFT,yPos - SHIFT};
                g2.setColor( yellow );
                g2.fillPolygon( xcoor2, ycoor2, 3 );
                g2.setColor( Color.black );
                g2.drawPolygon( xcoor2, ycoor2, 3 );
                break;
            case UP:
                int[] xcoor3 = {x-SHIFT,x,x+SHIFT};
                int[] ycoor3 = {yPos + SHIFT,yPos -SHIFT,yPos + SHIFT};
                g2.setColor( blue );
                g2.fillPolygon( xcoor3, ycoor3, 3 );
                g2.setColor( Color.black );
                g2.drawPolygon( xcoor3, ycoor3, 3 );
                break;
            default:
                return;
        };
        
    }
    
    /**
     * 
     * returns the current height of the arrow
     * @return the y position of the arrow
     */
    public int getY() {
        return yPos;
    }
    
    /**
     * 
     * Returns the half height of the arrow
     * @return half of the arrow's height
     */
    public static int getHalfHeight() {
        return SHIFT;
    }
}
