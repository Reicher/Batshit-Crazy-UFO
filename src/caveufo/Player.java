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

    public Player(Vec2 pos, double weight) {
        super(pos, weight);
        
        Vec2[] points = new Vec2[]{ new Vec2(-1.0f, -1.5f), new Vec2(-1.0f, 1.5f)
                , new Vec2(1.0f, 1.5f), new Vec2(1.0f, -1.5f) };
        setPoints(points);
        
        m_thrust = false;
        m_thrustPower = -120.0f; 
        m_rotPower =  -30.0f;
    }
    
    public void setThrust(){
        Vec2 ThrustVec = new Vec2(-m_thrustPower * (float)Math.sin(m_body.getAngle()), 
                                   m_thrustPower * (float)Math.cos(m_body.getAngle()));
        m_body.applyForce(ThrustVec, m_body.getWorldCenter());
    }   
    
    public void setRotation(boolean Right){
        if( Right )
            m_body.applyTorque(-m_rotPower);
        else
            m_body.applyTorque(m_rotPower);
    }
}
