import java.awt.Graphics2D;

public class Arrow
{   
    private int yPos;
    private static final int YVEL = 10;
    private static final int SHIFT= 50;
    private ArrowLane lane;
    public Arrow(ArrowLane al) {
        yPos = 50;
        lane = al;
    }
    
    public void move() {
        yPos += YVEL;
    }
    
    public void draw(Graphics2D g2, Direction dir) {
        int x = lane.getX();
        switch(dir) {
            case DOWN:
                int[] xcoor = {x-SHIFT,x,x+SHIFT};
                int[] ycoor = {yPos - SHIFT,yPos + SHIFT,yPos - SHIFT};
                g2.drawPolygon( xcoor, ycoor, 3 );
                break;
            case LEFT:
                int[] xcoor1 = {x-SHIFT,x+SHIFT,x+SHIFT};
                int[] ycoor1 = {yPos,yPos - SHIFT,yPos+SHIFT};
                g2.drawPolygon( xcoor1, ycoor1, 3 );
                break;
            case RIGHT:
                int[] xcoor2 = {x + SHIFT,x-SHIFT,x-SHIFT};
                int[] ycoor2 = {yPos,yPos+SHIFT,yPos - SHIFT};
                g2.drawPolygon( xcoor2, ycoor2, 3 );
                break;
            case UP:
                int[] xcoor3 = {x-SHIFT,x,x+SHIFT};
                int[] ycoor3 = {yPos + SHIFT,yPos -SHIFT,yPos + SHIFT};
                g2.drawPolygon( xcoor3, ycoor3, 3 );
                break;
            default:
                return;
        };
        
    }
    
    public int getY() {
        return yPos;
    }
    
    public static int getHalfHeight() {
        return SHIFT;
    }
}
