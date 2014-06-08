/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

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
public class Player extends SolidObject implements ContactListener {
        
    // Engines
    private boolean m_thrust;
    private float m_thrustPower;
    private float m_rotPower;
    private float m_sidePower;
    
    private RotationAction m_rotAction;
    private SideAction m_sideAction;

    public enum RotationAction{
        Left, None, Right
    }
    
    public enum SideAction{
        Left, None, Right
    }
    
    public Player() {
        super(null);
        
        setPoints( new Vec2[]{ new Vec2(-1.0f, -1.5f), new Vec2(-1.0f, 1.5f)
                , new Vec2(1.0f, 1.5f), new Vec2(1.0f, -1.5f) });
        
        m_thrust = false;
        m_rotAction = RotationAction.None;
        m_sideAction = SideAction.None;
        m_thrustPower = 200.0f; 
        m_rotPower =  40f;
        m_sidePower = 30f;
        
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
    
     @Override
    public void beginContact(Contact contact) {
    }    
    
    @Override
    public void endContact(Contact contact) {
        
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        for( float f : impulse.normalImpulses)
            System.out.println(f);
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
}
