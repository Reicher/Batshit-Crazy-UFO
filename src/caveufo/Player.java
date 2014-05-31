/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import org.jbox2d.common.Vec2;

/**
 *
 * @author regen
 */
public class Player extends ActiveGameObject {
        
    // Engines
    private boolean m_thrust;
    private float m_thrustPower;
    private float m_rotPower;
    
    private RotationAction m_rotAction;

    public enum RotationAction{
        Left, None, Right
    }
    
    public Player(Vec2 pos) {
        super(pos);
        
        Vec2[] points = new Vec2[]{ new Vec2(-1.0f, -1.5f), new Vec2(-1.0f, 1.5f)
                , new Vec2(1.0f, 1.5f), new Vec2(1.0f, -1.5f) };
        setPoints(points);
        
        m_thrust = false;
        m_rotAction = RotationAction.None;
        m_thrustPower = 200.0f; 
        m_rotPower =  20f;
    }
    
    public void setThrust(boolean mode){
        m_thrust = mode;
    }   
    
    public void setRotation(RotationAction mode){
        m_rotAction = mode;
    }
    
       
    public void update() {
        if( m_thrust ){
            Vec2 ThrustVec = new Vec2(m_thrustPower * (float)Math.sin(m_body.getAngle()), 
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
    }
}
