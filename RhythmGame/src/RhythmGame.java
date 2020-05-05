import java.awt.Font;
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

public class RhythmGame extends JPanel implements ActionListener {
	private Recorder recorder;
	private GameState gameState;
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


	public RhythmGame() {
		window = new JFrame();
		constructJPanel(window);
		timer = new Timer(5, this);
		lanes = new ArrowLane[4];
		musicPlayer = new MusicPlayer();
		gameState = GameState.IDLE;
		recorder = new Recorder(musicPlayer);
		setKeyBindings();
		Direction[] dir = { Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT };
		for (int i = 0; i < 4; i++) {
			lanes[i] = new ArrowLane(100 + 200 * i, dir[i]);
		}
		for (int i = 0; i < 4; i++) {
			lanes[i].add();
		}
//        startPlay(null); //Remove this after we add file selection
		// SetWorldTime to a negative number depending on velocity
	}

	private void constructJPanel(JFrame window2) {
		window.add(this);
		window.setVisible(true);
		window.setSize(SCREEN_WIDTH + 20, SCREEN_HEIGHT);
		window.setTitle("Rhythm Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
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
	
	public void addScore(int i) {
        score = score + 100*i;
    }

//	public ArrowLane getArrowLane(int direction) {
//		if (direction < lanes.length - 1) {
//			return lanes[direction];
//		}
//		return null;
//	}

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
		repaint();
		if(!musicPlayer.isRunning()) {
			if(gameState == GameState.RECORD) {
				recorder.stopRecording();
			}
			gameState = GameState.IDLE;
		}
		System.out.println("idk");
	}

	public void startPlay(String pathname) {
		timer.start();
		musicPlayer.play();

	}
	
	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public static void main(String[] args) {
		RhythmGame game= new RhythmGame();
		game.gameState = GameState.RECORD;
		game.recorder.startNewRecodring("song2");

	}
}
