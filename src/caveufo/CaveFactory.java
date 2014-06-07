/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 *
 * @author regen
 */
public abstract class CaveFactory {
    
    private final static float m_segmentLength = 5;
    private final static float m_maxDiviation = (float)Math.PI/4;
    private final static float m_minWide = 5;
    private final static float m_maxWide = 7;
    private static WorldDefinition m_world;    
    
    public static void setWorld(WorldDefinition world){
        m_world = world;
    }
    
    public static CaveSegment getFirst(){
        LineObject cieling, floor;
        
        Vec2 upperPoints[] = new Vec2[2];
        Vec2 lowerPoints[] = new Vec2[2];
        upperPoints[0] = new Vec2(-m_segmentLength, 0);
        lowerPoints[0] = new Vec2(-m_segmentLength, 0);
        upperPoints[1] = new Vec2(0, -m_minWide/2);
        lowerPoints[1] = new Vec2(0, m_minWide/2);
        
        // Set the points to the gameobject and create body
        cieling = new LineObject(upperPoints);
        floor = new LineObject(lowerPoints);
        cieling.createBody(m_world.getPhysicsWorld());
        floor.createBody(m_world.getPhysicsWorld());
        
        return new CaveSegment(cieling, floor);
    }
    
    public static CaveSegment getNext(CaveSegment segment){
        LineObject cieling, floor;  
        
        Vec2 upperPoint[] = new Vec2[2];
        Vec2 lowerPoint[] = new Vec2[2];
        upperPoint[0] = segment.getUpperEnd();
        lowerPoint[0] = segment.getLowerEnd();
        
        Vec2 middle = segment.getLowerEnd().add(segment.getUpperEnd());
        middle.x /= 2;
        middle.y /= 2;
        
        float a = (float)Math.random() * m_maxDiviation - m_maxDiviation/2;
        Vec2 dir = new Vec2( m_segmentLength * (float)Math.cos(a), 
                             m_segmentLength * (float)Math.sin(a));
        
        Vec2 newMiddle = middle.add(dir);
        Vec2 d = newMiddle.sub(middle);
        d.normalize();
        float wide = (float)Math.random() * (m_maxWide - m_minWide) + m_minWide;
        upperPoint[1] = new Vec2( newMiddle.x + (d.y * wide), 
                                  newMiddle.y - (d.x * wide));
        lowerPoint[1] = new Vec2( newMiddle.x - (d.y * wide), 
                                  newMiddle.y + (d.x * wide));
        
        // Set the points to the gameobject and create body
        cieling = new LineObject(upperPoint);
        floor = new LineObject(lowerPoint);
        cieling.createBody(m_world.getPhysicsWorld());
        floor.createBody(m_world.getPhysicsWorld());
        return new CaveSegment(cieling, floor);
    }
}
