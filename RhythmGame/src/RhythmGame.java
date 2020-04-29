import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
=======
import java.awt.event.KeyEvent;
>>>>>>> 99e55e2d7ff401c0f91f5e5012248bcdb35e0645
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

<<<<<<< HEAD
public class RhythmGame extends JPanel implements ActionListener {
	private ArrowLane[] lanes;
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 1000;
	private int worldTime;
	private Timer timer;
	private String pathnameFile;
	private Scanner scanner;
	private String currentLine;
	private PrintWriter output; // can't this just be a local variable in the record class?
	private MusicPlayer musicPlayer;
	private JFrame window;

	public RhythmGame() {
		window = new JFrame();
		constructJPanel(window);
		timer = new Timer(5, this);
		lanes = new ArrowLane[4];
		musicPlayer = new MusicPlayer();
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

	public ArrowLane getArrowLane(int direction) {
		if (direction < lanes.length - 1) {
			return lanes[direction];
		}
		return null;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (ArrowLane al : lanes) {
			al.draw(g2);
		}
		// draw the lines
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (ArrowLane al : lanes) {
			al.move();
		}
		repaint();
	}

	public void startPlay(String pathname) {
		timer.start();
		musicPlayer.play();

	}

	public void startRecord(String songTitle) {
		musicPlayer.defineSong(songTitle + ".wav");
		new File("levels").mkdir(); // creates a new levels folder if one was not created
		String path = System.getProperty("user.dir") + "/levels/" + songTitle + "_Arrow.txt";
		File recordFile = new File(path);
		if (recordFile.exists()) {
			System.out.println("file already exists");
		} else {
			try {
				recordFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("error creating file");
				e.printStackTrace();
				return;
			}

			try {
				output = new PrintWriter(path);
			} catch (FileNotFoundException e) {
				System.out.println("cannot find" + songTitle + "_Arrow.txt");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			musicPlayer.play();
			scanner = new Scanner(System.in);
			while (musicPlayer.isRunning()) {
				String outputString = "";
				// TODO reading inputs properly (below is just a temporary example)
				String inputString = scanner.next();

				switch (inputString) {
				case "w":
					outputString = "u";
					break;
				case "a":
					outputString = "l";
					break;
				case "s":
					outputString = "d";
					break;
				case "d":
					outputString = "r";
					break;
				}
				// end of stuff to replace
				int constant = 0; // TODO figure out how much time to subtract
				if (!outputString.isEmpty()) {
					output.println(outputString + (musicPlayer.getCurrentTime() - constant));
				}

			}
			output.close();

		}

	}

<<<<<<< HEAD
	public void record(String direction) {

	}

=======
>>>>>>> 4831d1edb1fb1bef7a5bfd1d4beed4d5361004c5
	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public static void main(String[] args) {
		RhythmGame game = new RhythmGame();
<<<<<<< HEAD
		game.startRecord("song2");
=======
>>>>>>> 4831d1edb1fb1bef7a5bfd1d4beed4d5361004c5

	}
=======
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
>>>>>>> 99e55e2d7ff401c0f91f5e5012248bcdb35e0645

}
