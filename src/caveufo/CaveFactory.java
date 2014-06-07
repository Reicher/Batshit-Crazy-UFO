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
    
    private final static float m_minLength = 1;
    private final static float m_maxLength = 10;
    private final static float m_maxDiviation = (float)Math.PI/2;
    private final static float m_minWide = 7;
    private final static float m_maxWide = 18;
    private final static float m_formations = 0.4f;
    private final static float m_maxFormation = 4;
    private static WorldDefinition m_world;    
    
    public static void setWorld(WorldDefinition world){
        m_world = world;
    }
    
    public static CaveSegment getFirst(){
        LineObject cieling, floor;
        
        Vec2 upperPoints[] = new Vec2[2];
        Vec2 lowerPoints[] = new Vec2[2];
        upperPoints[0] = new Vec2(-m_maxLength, 0);
        lowerPoints[0] = new Vec2(-m_maxLength, 0);
        upperPoints[1] = new Vec2(0, 0-m_minWide/2);
        lowerPoints[1] = new Vec2(0, 0+m_minWide/2);
        
        // Set the points to the gameobject and create body
        cieling = new LineObject(upperPoints);
        floor = new LineObject(lowerPoints);
        cieling.createBody(m_world.getPhysicsWorld());
        floor.createBody(m_world.getPhysicsWorld());
        
        return new CaveSegment(cieling, floor);
    }
    
    public static CaveSegment getNext(CaveSegment segment){
        LineObject cieling, floor;
        
        Vec2 upperPoints[] = new Vec2[2];
        Vec2 lowerPoints[] = new Vec2[2];
        upperPoints[0] = segment.getUpperEnd();
        lowerPoints[0] = segment.getLowerEnd();

        //Not so nice looking
        Vec2 newUpper, newLower;
        do{
            float r = (float)Math.random() * (m_maxLength - m_minLength) + m_minLength;
            float a = (float)Math.random() * m_maxDiviation - m_maxDiviation/2;
            
            newUpper = new Vec2( segment.getUpperEnd().x + (r * (float)Math.cos(a))
                               , segment.getUpperEnd().y + (r * (float)Math.sin(a)));
            
            a = (float)Math.random() * m_maxDiviation - m_maxDiviation/2;
            newLower = new Vec2( segment.getLowerEnd().x + (r * (float)Math.cos(a))
                               , segment.getLowerEnd().y + (r * (float)Math.sin(a)));
            
        }while(    Math.abs(newUpper.y - newLower.y) < m_minWide 
                || Math.abs(newUpper.y - newLower.y) > m_maxWide);
        
        upperPoints[1] = newUpper;
        lowerPoints[1] = newLower;
        
        // Set the points to the gameobject and create body
        cieling = new LineObject(upperPoints);
        floor = new LineObject(lowerPoints);
        cieling.createBody(m_world.getPhysicsWorld());
        floor.createBody(m_world.getPhysicsWorld());
        
        /*
        // Rockformations
        if(Math.random() < m_formations){                
            float formationSize = (float)Math.random() * m_maxFormation;
            Vec2[] lowPoints = { new Vec2(lower[0].x + (lower[1].x - lower[0].x)*0.4f
                                        , lower[0].y + (lower[1].y - lower[0].y)*0.4f)
                              , new Vec2( lower[0].x + (lower[1].x - lower[0].x)*0.6f
                                       ,  lower[0].y + (lower[1].y - lower[0].y)*0.6f)
                              , new Vec2( lower[0].x + (lower[1].x - lower[0].x)/2,
                                          lower[0].y - formationSize)};
            m_stalactite = new SolidObject(lowPoints);
            m_stalactite.createBody(world.getPhysicsWorld());
            
            formationSize = (float)Math.random() * m_maxFormation;
            Vec2[] highPoints = { new Vec2( upper[0].x + (upper[1].x - upper[0].x)*0.4f
                                          , upper[0].y + (upper[1].y - upper[0].y)*0.4f)
                                , new Vec2( upper[0].x + (upper[1].x - upper[0].x)*0.6f
                                         ,  upper[0].y + (upper[1].y - upper[0].y)*0.6f)
                                , new Vec2( upper[0].x + (upper[1].x - upper[0].x)/2
                                          , upper[0].y + formationSize)};
            
            m_stalagmite = new SolidObject(highPoints);  
            m_stalagmite.createBody(world.getPhysicsWorld());
        }  */
        
        return new CaveSegment(cieling, floor);
    }
    
}
