package rhythm_game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
/**
 * 
 *  Listens to when a key is pressed and calls recieveInput with the key pressed
 *
 *  @author  Ryan_Tai, David_Choi
 *  @version May 11, 2020
 *  @author  Period: 1
 *  @author  Assignment: RhythmGame
 *
 *  @author  Sources: None
 */
public class ArrowListener extends AbstractAction implements ActionListener
{
    private RhythmGame game;
    private String key;
    /**
     * 
     * @param g the game the ArrowListener belongs to
     * @param k the string representing which key the arrowListener is listening to
     */
    public ArrowListener(RhythmGame g,String k) {
        game = g;
        key = k;
        System.out.println( "constructed" );
    }
    
    @Override // Aparently overrides don't need javadoc, they probably just have it
    public void actionPerformed( ActionEvent arg0 )
    {

        game.recieveInput(key);
        
    }
    


}
