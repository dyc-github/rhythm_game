import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
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
    private InputMap im;
    private ActionMap am;
    private int score = 0;
    
    public RhythmGame() {
        window = new JFrame();
        constructJPanel( window );
        timer = new Timer(10,this);
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
    
    private void setKeyBindingsPlay() {
        im = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        am = this.getActionMap();
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_LEFT,0 ), "Left" );
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_DOWN,0 ), "Down" );
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_UP,0 ), "Up" );
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_RIGHT,0 ), "Right" );
        am.put( "Left", new ArrowListener(this,"Left") );
        am.put( "Down", new ArrowListener(this,"Down") );
        am.put( "Up", new ArrowListener(this,"Up") );
        am.put( "Right", new ArrowListener(this,"Right") );
    }
    
    public ArrowLane getArrowLane(int direction) {
        if (direction<lanes.length) {
            return lanes[direction];
        }
        return null;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent( g );
        Graphics2D g2 = (Graphics2D)g;
        Font font = new Font("Helvetica",Font.LAYOUT_LEFT_TO_RIGHT,25);
        g2.setFont( font );
        g2.drawString( "Score: " + score, 50, 50 );
        g2.drawLine( 0, ArrowLane.getGoal() - Arrow.getHalfHeight(), 
            SCREEN_WIDTH, ArrowLane.getGoal() - Arrow.getHalfHeight() );
        g2.drawLine( 0, ArrowLane.getGoal() + Arrow.getHalfHeight(), 
            SCREEN_WIDTH, ArrowLane.getGoal() + Arrow.getHalfHeight() );
        for (ArrowLane al: lanes) {
            if (Math.random()*150<1) {
                al.add();
            }
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
        setKeyBindingsPlay();
        timer.start();
    }
    
    public void addScore(int i) {
        score = score + 100*i;
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
