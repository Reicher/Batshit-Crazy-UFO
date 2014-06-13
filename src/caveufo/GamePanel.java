/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caveufo;

import caveufo.Player.RotationAction;
import caveufo.Player.SideAction;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.jbox2d.common.Vec2;

/**
 *
 * @author regen
 */

public class GamePanel extends JPanel implements KeyListener {
    //private final Dimension m_screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Dimension m_screenSize = new Dimension(800, 600);

    private final int FPS = 60;
    private int updateInterval;
    private Timer gameTimer;
    private TimeKeeper m_timeKeeper;
    
    private boolean m_loaded = false;

    private ActionListener m_listener;
    private State m_gameState;
    
    private Player m_player;
    private LevelHandler m_levelHandler;     
    private WorldDefinition m_world;
    
    private enum State{
        EXIT, MENU, GAME
    }

    public GamePanel() {
        super();
        init();
    }
    
    public void init(){   
        this.updateInterval = 1000 / FPS;
        
        setPreferredSize(m_screenSize);
        setFocusable(true);
        requestFocus();
        
        // Init Game loop
        m_listener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event)
            {   
                update();
                draw();
            }
        };
        
        m_world = new WorldDefinition(m_screenSize);
        
        gameTimer = new Timer(updateInterval, m_listener);
        m_timeKeeper = new TimeKeeper();
        
        addKeyListener(this);
        m_gameState = State.GAME;
        
        m_levelHandler = new LevelHandler(m_world);

        m_player = new Player();
        addKeyListener(m_player);
        m_world.getPhysicsWorld().setContactListener(m_player);
        m_player.createBody(m_world.getPhysicsWorld());
    
        m_loaded = true;
        gameTimer.start();
    }
    
    private void update(){
        if(!m_loaded)
            return;
        
        switch(m_gameState){
            case EXIT:
                JFrame parentJFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                parentJFrame.dispatchEvent(new WindowEvent(parentJFrame, WindowEvent.WINDOW_CLOSING));
                break;
            case MENU:
                
                break;
            case GAME: 
                m_player.update();
                m_levelHandler.update(m_player.getPosition());
                
                if(m_player.tookCheckpoint())
                    m_timeKeeper.resetTimer();
                
                if(m_timeKeeper.getTimeLeft() <= 0){
                    System.out.println("Game over man! New Game Starting!");
                    m_levelHandler.GenerateNewLevel();
                    m_player.reset();
                    m_timeKeeper.resetTimer();
                    
                }
                
                int velocityIterations = 6;
                int positionIterations = 2;
                m_world.getPhysicsWorld().step(this.updateInterval/1000.0f, velocityIterations, positionIterations);
                break;
            default:
                System.out.println("Invalid state");
        }

    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; 
                
        Vec2 playerPos = new Vec2(
        m_player.getPosition().x * m_world.getPixelsPerMeter().x - m_screenSize.width/2
        ,m_player.getPosition().y * m_world.getPixelsPerMeter().y - m_screenSize.height/2);

        g2.translate(-playerPos.x, -playerPos.y);
        
        m_player.draw(g2, m_world);
        m_levelHandler.draw(g2, m_world);

        
        g2.translate(0, 0);
    }
    

    private void draw(){
        if(!m_loaded)
            return;
        
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent key) {}

    @Override
    public void keyPressed(KeyEvent key) {            
        int code = key.getKeyCode();

        if(code == KeyEvent.VK_ESCAPE)
            m_gameState = State.EXIT;
    }
 
    @Override
    public void keyReleased(KeyEvent key) {}
}
