package rhythm_game.RhythmGame.src;

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
    private String key;
    
    public ArrowListener(RhythmGame g,String k) {
        game = g;
        key = k;
        System.out.println( "constructed" );
    }

    @Override
    public void actionPerformed( ActionEvent arg0 )
    {

        game.recieveInput(key);
        
    }
    


}
