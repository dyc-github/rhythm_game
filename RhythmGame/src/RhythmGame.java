import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RhythmGame extends JPanel implements ActionListener
{
    private ArrowLane[] lanes;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT =1000;
    private int worldTime;
    private Timer timer;
    private String pathnameFile;
    private Scanner scanner;
    private String currentLine;
    private PrintWriter output;
    private MusicPlayer  musicPlayer;
    private JFrame window;
    
    public RhythmGame() {
        window = new JFrame();
        constructJPanel( window );
        timer = new Timer(5,this);
        lanes = new ArrowLane[4];
        Direction[] dir = {Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT};
        for (int i = 0; i<4;i++) {
            lanes[i] = new ArrowLane(100+200*i, dir[i]);
        }
        for (int i = 0; i<4;i++) {
            lanes[i].add();
        }
        startPlay(null); //Remove this after we add file selection
        //SetWorldTime to a negative number depending on velocity
    }
    
    private void constructJPanel( JFrame window2 )
    {
        window.add( this );
        window.setVisible( true );
        window.setSize( SCREEN_WIDTH + 20, SCREEN_HEIGHT );
        window.setTitle( "Rhythm Game" );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
    }
    
    public ArrowLane getArrowLane(int direction) {
        if (direction<lanes.length-1) {
            return lanes[direction];
        }
        return null;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent( g );
        Graphics2D g2 = (Graphics2D)g;
        for (ArrowLane al: lanes) {
            al.draw( g2 );
        }
        //draw the lines
    }
    
    
    @Override
    public void actionPerformed( ActionEvent arg0 )
    {
        for (ArrowLane al: lanes) {
            al.move();
        }
        repaint();   
    }
    
    public void startPlay(String pathname) {
        timer.start();
    }
    
    public void startRecord(String pathname) {
        
    }
    
    public void record(String direction) {
        
    }
    
    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }
    public static void main( String[] args )
    {
        RhythmGame game  = new RhythmGame();

    }

}
