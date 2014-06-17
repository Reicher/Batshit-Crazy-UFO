/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;

/**
 *
 * @author regen
 */
public class Player extends SolidObject implements ContactListener, KeyListener  {
        
    // Engines
    private boolean m_thrust;
    private float m_thrustPower;
    private float m_rotPower;
    private float m_sidePower;
    
    private RotationAction m_rotAction;
    private SideAction m_sideAction;
    
    private boolean m_freshCheckpointTaken; // so fucking stupid
    
    private final float m_damageThreshold = 20.0f;
    private float m_maxHealth = 100;
    private float m_health = m_maxHealth;
    
    private float m_score = 0;

    public enum RotationAction{
        Left, None, Right
    }
    
    public enum SideAction{
        Left, None, Right
    }
    
    public Player() {
        super(null);
        
        setPoints( new Vec2[]{ new Vec2(-0.75f, -1.5f), new Vec2(-1.0f, 1.5f)
                , new Vec2(1.0f, 1.5f), new Vec2(0.75f, -1.5f) });
        
        m_thrust = false;
        m_rotAction = RotationAction.None;
        m_sideAction = SideAction.None;
        m_thrustPower = 100.0f; 
        m_rotPower =  40f;
        m_sidePower = 30f;
        m_freshCheckpointTaken = false;
        
        this.setWeight(1f);
        this.setBodyType(BodyType.DYNAMIC);
    }
    
    public void setThrust(boolean mode){
        m_thrust = mode;
    }   
    
    public void setRotation(RotationAction mode){
        m_rotAction = mode;
    }
    
    public void SetSide(SideAction mode){
        m_sideAction = mode;
    }

    // Must take away before someone see
    public boolean tookCheckpoint(){
        if( !m_freshCheckpointTaken )
            return false;
        
        m_freshCheckpointTaken = false;
        m_score += 100;
        return true;
    }    
    
    public float getHealth(){
        return m_health;
    }
    
    public float getScore(){
        return m_score;
    }
    
    protected void reset(){
        m_health = m_maxHealth;
        m_score = 0;
        
        m_body.setTransform(new Vec2(0, 0), 0f);        

        m_body.setAngularVelocity(0);
        m_body.setLinearVelocity(new Vec2(0, 0));
    }
    
    public void update() {
        Vec2 ThrustVec;
        if( m_thrust ){
            ThrustVec = new Vec2(m_thrustPower * (float)Math.sin(m_body.getAngle()), 
                           -m_thrustPower * (float)Math.cos(m_body.getAngle()));
            m_body.applyForceToCenter(ThrustVec);
        }
        else
            m_body.applyForceToCenter(new Vec2(0, 0));
            
        switch( m_rotAction ){
            case Left:
                m_body.applyTorque(-m_rotPower);
               break;
            case Right:
                m_body.applyTorque(m_rotPower);
            case None:
            default:
                m_body.applyTorque(0); // Needed?
        }
        
        switch( m_sideAction ){
            case Left:
            case Right:
            ThrustVec = new Vec2(m_sidePower * (float)Math.sin(m_body.getAngle()- Math.PI/2), 
                                 m_sidePower * (float)Math.cos(m_body.getAngle()- Math.PI/2));
            if(m_sideAction == SideAction.Left)
                m_body.applyForceToCenter(ThrustVec);
            else
                m_body.applyForceToCenter(ThrustVec.negate());                
            case None:
            default:
                m_body.applyTorque(0); // Needed?
        }
    }
        
     @Override
    public void beginContact(Contact contact) {    
        if(contact.getFixtureA().getBody().getUserData().getClass().equals(Checkpoint.class)){
            m_freshCheckpointTaken = Checkpoint.class.cast(contact.getFixtureA().getBody().getUserData()).Take();
        }
    }    
    
    @Override
    public void endContact(Contact contact) {  
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // check damage
        for(float force : impulse.normalImpulses){
            if(force > m_damageThreshold)
                m_health -= force - m_damageThreshold;
        }
        
        if(m_health < 0)
            m_health = 0f;
    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent key) {
        int code = key.getKeyCode();
        
        if(code == KeyEvent.VK_W) {
            setThrust(true);
        }
     
        if(code == KeyEvent.VK_E) {
            setRotation(RotationAction.Right);
        }
        if(code == KeyEvent.VK_Q) {
            setRotation(RotationAction.Left);
        }
        
        // LEFT RIGHT
        if(code == KeyEvent.VK_D) {
            SetSide(SideAction.Right);
        }
        
        if(code == KeyEvent.VK_A) {
            SetSide(SideAction.Left);
        }

    }

    @Override
    public void keyReleased(KeyEvent key) {
        int code = key.getKeyCode();
        
        if(code == KeyEvent.VK_W) {
            setThrust(false);
        }
     
        if(code == KeyEvent.VK_E || code == KeyEvent.VK_Q) {
            setRotation(RotationAction.None);
        }
        
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_D) {
            SetSide(SideAction.None);
        }
    }

}
