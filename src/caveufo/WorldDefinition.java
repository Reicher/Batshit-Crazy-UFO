/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import java.awt.Dimension;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 *
 * @author regen
 */
public class WorldDefinition {
    WorldDefinition(Dimension screenResolution, Vec2 gravity){
        m_physicWorld = new World(gravity);
        m_screenRes = screenResolution;
        m_physicalSize = new Vec2(screenResolution.width/20, screenResolution.height/20);
    }
    
    public World getPhysicsWorld(){
        return m_physicWorld;
    }

    public Vec2 getPixelsPerMeter(){
        return new Vec2(m_screenRes.height / m_physicalSize.x
                , m_screenRes.width / m_physicalSize.y);
    }
    
    private World m_physicWorld;
    private Dimension m_screenRes;
    private Vec2 m_physicalSize;
}
