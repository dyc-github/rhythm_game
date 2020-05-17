package rhythm_game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 * 
 * This is the menu bar that allows the player to select songs and if they are
 * recording or playing the song
 *
 *  @author  Ryan_Tai,David_Choi
 *  @version May 11, 2020
 *  @author  Period: 1
 *  @author  Assignment: RhythmGame
 *
 *  @author  Sources: https://stackoverflow.com/questions/13516829/jfilechooser-change-default-directory-in-windows
 */
public class MenuBar implements ActionListener
{
    private RhythmGame game;
    JMenuBar menubar;
    JMenu file,play;
    JMenuItem selectFile,quit,startGame, startRecord;
    JFileChooser chooser;
    MusicPlayer mp;
    
    /**
     * 
     * @param g the game that is connected to the menu bar
     * @param window the window that is connected to the menu bar
     * @param musicPlayer the music player that is connected to the menu bar
     */
    public MenuBar(RhythmGame g, JFrame window, MusicPlayer musicPlayer) {
        game = g;
        menubar = new JMenuBar();
        mp = musicPlayer;
        window.setJMenuBar( menubar );
        
        JMenu file = new JMenu("File");
        menubar.add( file );
        
        selectFile = new JMenuItem("Select File");
        selectFile.addActionListener( this );
        file.add( selectFile );
        
        quit = new JMenuItem("Quit");
        quit.addActionListener( this );
        file.add( quit );
        
        JMenu play = new JMenu("Play");
        menubar.add( play );
        
        startGame = new JMenuItem("Start Game");
        startGame.addActionListener( this );
        play.add( startGame );
        
        startRecord = new JMenuItem("Start Record");
        startRecord.addActionListener( this );
        play.add( startRecord );
        
        
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object o = e.getSource();
        if (selectFile == o) {
            System.out.println( "File Selected" );
            String path = System.getProperty( "user.dir" )+"\\music";
            chooser = new JFileChooser("Select a file");
            chooser.setCurrentDirectory( new File(path) );
            int result = chooser.showOpenDialog( game );
            if (result == JFileChooser.APPROVE_OPTION) {
                System.out.println( "Haven't tested this part yet. I hope it works" );
                File file = chooser.getSelectedFile();
                System.out.println( file.getAbsolutePath() );
                mp.defineSong( file.getAbsolutePath() );
            }
            System.out.println( "I got the Absolute Path of the file, I have no clue "
                + "what to do with it, but I got it");
        }
        else if (startGame == o) {
            System.out.println( "Game starts" );
            game.start(true);
        }
        else if (startRecord == o) {
            System.out.println( "Record starts" );
            game.start(false);
        }
        else if (quit == o) {
            System.exit( 0 );
        }
        
    }
    
    
}
