import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

public class ArrowListener extends AbstractAction implements ActionListener
{
    private RhythmGame game;
    String key;
    
    public ArrowListener(RhythmGame g,String k) {
        game = g;
        key = k;
        System.out.println( "constructed" );
    }

    @Override
    public void actionPerformed( ActionEvent arg0 )
    {
        int comparison = 0;
        if (key.equals( "Left" )) {
            comparison = game.getArrowLane( 0 ).compare();
        }
        else if (key.equals( "Down" )) {
            comparison = game.getArrowLane( 1 ).compare();
        }
        else if (key.equals( "Up" )) {
            comparison = game.getArrowLane( 2 ).compare();
        }
        else if (key.equals( "Right" )) {
            System.out.println( game );
            comparison = game.getArrowLane( 3 ).compare();
        }
        System.out.println( comparison );
        game.addScore( comparison );
        
    }
    


}
