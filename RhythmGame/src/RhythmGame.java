package rhythm_game;


import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 * 
 *  This represents the RhythmGame which contains the screen and the timer that
 *  dictates the frame speed
 *
 *  @author  Ryan_Tai,David_Choi
 *  @version May 11, 2020
 *  @author  Period: 1
 *  @author  Assignment: RhythmGame
 *
 *  @author  Sources: None
 */
public class RhythmGame extends JPanel implements ActionListener {
	private Recorder recorder;
	private GameState gameState;
	private MenuBar menuBar;
	private ArrowLane[] lanes;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT =1000;
    private int worldTime;
    private Timer timer;
    private String currentLine;
    private MusicPlayer  musicPlayer;
    private JFrame window;
    private InputMap im;
    private ActionMap am;
    private int score = 0;

    /**
     * Creates a RhythmGame
     */
	public RhythmGame() {
		window = new JFrame();

		timer = new Timer(7 , this);
		lanes = new ArrowLane[4];
		musicPlayer = new MusicPlayer();
		menuBar = new MenuBar(this,window,musicPlayer);
		gameState = GameState.IDLE;//Should be IDLE, currently Play for testing
		recorder = new Recorder(musicPlayer);
		
		setKeyBindings();
		Direction[] dir = { Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT };
		for (int i = 0; i < 4; i++) {
			lanes[i] = new ArrowLane(100 + 200 * i, dir[i]);
		}
	    constructJPanel(window);
		// SetWorldTime to a negative number depending on velocity
	}

	/**
	 * 
	 * Adds things such as the name and the ability to close it to the window
	 * @param window2 the window which things are added on
	 */
	private void constructJPanel(JFrame window2) {
		window.add(this);
		window.setVisible(true);
		window.setSize(SCREEN_WIDTH + 20, SCREEN_HEIGHT);
		window.setTitle("Rhythm Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	/**
	 * 
	 * Creates key bindings and an ArrowListener so the user can interact with
	 * the game
	 */
	private void setKeyBindings() {
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
	
	/**
	 * 
	 * Reacts according to the current state of the game and the key pressed.
	 * Records the input if it is recording, calls an arrowlane's compare if playing
	 * and does nothing if recording
	 * @param input the key pressed
	 */
	public void recieveInput(String input) {
		System.out.println(input);

		//TODO you need to define gameState as recording and playing when you create the menu
		if(gameState == gameState.IDLE) {
			return;
		}
		else if(gameState == GameState.RECORD) {
			recorder.record(input);
		}
		else if (gameState == GameState.PLAY) {
			int comparison = 0;
			System.out.println( "play" );
			if (input.equals( "Left" )) {
	            comparison = lanes[0].compare();
	        }
	        else if (input.equals( "Down" )) {
	            comparison = lanes[1].compare();
	        }
	        else if (input.equals( "Up" )) {
	            comparison = lanes[2].compare();
	        }
	        else if (input.equals( "Right" )) {
	            comparison = lanes[3].compare();
	        }
	        System.out.println( comparison );
	        addScore( comparison );
		}
	}
	
	/**
	 * 
	 * Increases the score
	 * @param i the score to add times 100
	 */
	public void addScore(int i) {
        score = score + 100*i;
    }

//	public ArrowLane getArrowLane(int direction) {
//		if (direction < lanes.length - 1) {
//			return lanes[direction];
//		}
//		return null;
//	}

	/**
	 * paints the screen
	 * @param g the graphics of the screen
	 */
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
	public void actionPerformed(ActionEvent arg0) {
		for (ArrowLane al : lanes) {
			al.move();
		}
		if(!musicPlayer.isRunning()) {
			if(gameState == GameState.RECORD) {
				recorder.stopRecording();
			}
			gameState = GameState.IDLE;
		}
	    repaint();
	}

	/**
	 * 
	 * Starts the play/recording of the game
	 * @param isPlay true = playing, false = recording
	 */
	public void start(boolean isPlay) {
	    //true = play, false = record
	    if (musicPlayer.play()) {
	        if (isPlay) {
	            gameState = GameState.PLAY;
	        }
	        else{
	            gameState = GameState.RECORD;
	        }
	        timer.start();
	    }
	    else {
	        Graphics2D g2 = (Graphics2D)this.getGraphics();
	        String str = "Music not found";
	        g2.setFont( new Font("Helvetica",Font.LAYOUT_LEFT_TO_RIGHT,25) );
	        FontMetrics fm = g2.getFontMetrics();
	        int x = SCREEN_WIDTH/2 - (fm.stringWidth( str )/2);
	        g2.drawString( str , x, 50 );
	    }
	}

	/**
	 * 
	 * returns the height of the screen
	 * @return screen height
	 */
	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}
	
	/**
	 * 
	 * Starts the game by creating a new rhythmGame
	 * @param args
	 */
	public static void main(String[] args) {
		RhythmGame game= new RhythmGame();
//		game.gameState = GameState.RECORD;
//		game.recorder.startNewRecodring("song2");

	}
}
