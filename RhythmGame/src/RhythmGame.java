
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class RhythmGame extends JPanel implements ActionListener{
	private Recorder recorder;
	private GameState gameState;
	private MenuBar menuBar;
	private ArrowLane[] lanes;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT =1000; //kinda irrelevent
    private int worldTime;
    private Timer timer;
    private String currentLine = "";
    private MusicPlayer  musicPlayer;
    private Scanner scanner;
    private JFrame window;
    private InputMap im;
    private ActionMap am;
    private int score = 0;
    private Color scoreColor;
    private String levelTitle;
    private Map<String, ArrayList<String>> playerScoresMap;


	public RhythmGame() {
		window = new JFrame();

		timer = new Timer(5 , this);
		lanes = new ArrowLane[4];
		musicPlayer = new MusicPlayer();
		menuBar = new MenuBar(this,window);
		gameState = GameState.IDLE;//Should be IDLE, currently Play for testing
		recorder = new Recorder(musicPlayer);		
		scoreColor = Color.WHITE;
		levelTitle = "";
		playerScoresMap = new HashMap<String, ArrayList<String>>();
		setKeyBindings();
		constructJPanel(window);
		Direction[] dir = { Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT };
		for (int i = 0; i < 4; i++) {
			lanes[i] = new ArrowLane(100 + 200 * i, dir[i], window.getHeight());
		}
	    
		// SetWorldTime to a negative number depending on velocity
	}

	private void constructJPanel(JFrame window2) {
		window.add(this);
		window.setVisible(true);
		window.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
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
	
	public void addScore(int i) {
		Graphics2D g2d = (Graphics2D)this.getGraphics();
		switch(i) {
		case 1 :
			scoreColor = Color.RED;
			break;
		case 2:
			scoreColor= Color.YELLOW;
			break;
		case 3:
			scoreColor= Color.GREEN;
			break;
		}
		System.out.println('i');
		g2d.fillRect(0, window.getContentPane().getHeight() - Arrow.getHalfHeight() * 3, SCREEN_WIDTH, Arrow.getHalfHeight() * 2);
		
		if(i > 0) {
			score = score + 100 * i;
		}
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
        
		g2.setColor(scoreColor);
		g2.fillRect(0, window.getContentPane().getHeight() - Arrow.getHalfHeight() * 3, SCREEN_WIDTH, Arrow.getHalfHeight() * 2);
		g2.setColor(Color.BLACK);
		Font font = new Font("Helvetica",Font.LAYOUT_LEFT_TO_RIGHT,25);
        g2.setFont( font );
		g2.drawString("Score: " + score, 50, 50);
		g2.drawLine(0, window.getContentPane().getHeight() - Arrow.getHalfHeight(), SCREEN_WIDTH,
				window.getContentPane().getHeight() - Arrow.getHalfHeight());
		g2.drawLine(0, window.getContentPane().getHeight() - Arrow.getHalfHeight()* 3 , SCREEN_WIDTH,
				window.getContentPane().getHeight() - Arrow.getHalfHeight()* 3);
		for (ArrowLane al : lanes) {
//            if (Math.random()*150<1) {
//                al.add();
			ArrowLane.setGoal(window.getContentPane().getHeight());
//            }
            al.draw( g2 );
		}
		if (gameState == GameState.IDLE && !levelTitle.isEmpty()&&playerScoresMap.containsKey(levelTitle)) {
			ArrayList<String>playerScores = playerScoresMap.get(levelTitle);
			Iterator<String> playerScoresIt = playerScores.iterator();
			int leaderboardPos = 50;
			g2.drawString("Leaderboard: ", window.getContentPane().getWidth()-200,leaderboardPos);
			if (playerScores.isEmpty()) {
				leaderboardPos += 10; 
				g2.drawString("no scores", window.getContentPane().getWidth()-200,leaderboardPos);
			} else {
				while (playerScoresIt.hasNext()) {
					leaderboardPos += 40;
					g2.drawString(playerScoresIt.next(), window.getContentPane().getWidth()- 130, leaderboardPos);
				}
			}

			
		}
	}
    
	// if the window is ever resized it will fix all the units to match the new
	// window of different screen
    
//    public void componentResized(ComponentEvent e) {
//    	Graphics2D g2 = (Graphics2D)this.getGraphics();
//    	//TODO FIX
//    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (currentLine.isEmpty() && scanner != null && scanner.hasNext()) {
			currentLine = scanner.nextLine();
		}
		if (gameState == GameState.PLAY) {
			if (scanner.hasNextLine() && Integer.parseInt(currentLine.substring(1)) <= 
					(musicPlayer.getCurrentTime()+ ArrowLane.getGoal() + Arrow.getHalfHeight())) {//this only works assuming that the velocity is 1 
				switch (currentLine.charAt(0)) {
				case 'L':
					lanes[0].add();
					break;
				case 'D':
					lanes[1].add();
					break;
				case 'U':
					lanes[2].add();
					break;
				case 'R':
					lanes[3].add();
					break;
				}
				if (scanner.hasNext()) {
					currentLine = scanner.nextLine();
				}
			}

			for (ArrowLane al : lanes) {
				al.move();
			}
		}

		if (!musicPlayer.isRunning()) {
			if (gameState == GameState.RECORD) {
				recorder.stopRecording();
			}
			if(gameState == GameState.PLAY) {
				if (!levelTitle.isEmpty()&&!playerScoresMap.containsKey(levelTitle)) {
					playerScoresMap.put(levelTitle, new ArrayList<String>());
				}
				ArrayList<String>playerScores = playerScoresMap.get(levelTitle);
				playerScores.add(Integer.toString(score));
				
			}
			scoreColor = Color.WHITE;
			currentLine = "";
			score = 0;
			gameState = GameState.IDLE;
			timer.stop();
		}
	    repaint();
	}
	

	public void start(GameState state, File file) {
	    //true = play, false = record
		levelTitle= file.getName();
		musicPlayer.defineSong(file.getAbsolutePath());
		if (gameState == GameState.IDLE && musicPlayer.play()) {
			if (state == gameState.PLAY) {
				try {
					scanner = new Scanner(new File(System.getProperty("user.dir") + "/levels/" + file.getName()+ "_Arrow.txt"));
				} catch (FileNotFoundException e) {
					System.out.println("no such file exists");
					e.printStackTrace();
				}
			}
	    	if (state == gameState.RECORD) {
	    		try {
					recorder.startNewRecodring(levelTitle);
				} catch (FileAlreadyExistsException e) {
					System.out.println("the file already exists");
					e.printStackTrace();
				}
	    	}
	        gameState = state;
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

	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public static void main(String[] args) {
		RhythmGame game= new RhythmGame();
//		game.gameState = GameState.RECORD;
//		game.recorder.startNewRecodring("song2");

	}
}
