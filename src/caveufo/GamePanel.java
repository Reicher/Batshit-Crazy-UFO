/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caveufo;

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
import org.jbox2d.dynamics.World;

/**
 *
 * @author regen
 */

public class GamePanel extends JPanel implements KeyListener {
    //private final Dimension m_screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final Dimension m_screenSize = new Dimension(800, 600);

    private final int FPS = 30;
    private int updateInterval;
    private Timer gameTimer;
    private boolean m_loaded = false;

    private ActionListener m_listener;
    private State m_gameState;
    
    private Player m_player;
    private Terrain m_terrain; // should be a list or something later
    
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
        
        gameTimer = new Timer(updateInterval, m_listener);
        addKeyListener(this);
        m_gameState = State.GAME;
        
        Vec2 gravity = new Vec2(0.0f, 9.82f);
        m_world = new WorldDefinition(m_screenSize, gravity);
        
        m_terrain = new Terrain(m_world);

        Vec2 pos = new Vec2(10, 4); 
        m_player = new Player(pos, 50.0);
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
                float timeStep = 1.0f / FPS;
                int velocityIterations = 6;
                int positionIterations = 2;
                m_world.getPhysicsWorld().step(timeStep, velocityIterations, positionIterations);
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
        m_terrain.draw(g2, m_world);

        
        g2.translate(0, 0);
    }
    

    private void draw(){
        if(!m_loaded)
            return;
        
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent key) {            
        int code = key.getKeyCode();
        
        if(code == KeyEvent.VK_W) {
                m_player.setThrust();
        }
     
        if(code == KeyEvent.VK_E) {
                m_player.setRotation(true);
        }
        
        if(code == KeyEvent.VK_Q) {
                m_player.setRotation(false);
        }


        if(code == KeyEvent.VK_ESCAPE)
            m_gameState = State.EXIT;
    }
 
    @Override
    public void keyReleased(KeyEvent key) {
        int code = key.getKeyCode();
    }
}
