/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

/**
 *
 * @author regen
 */
public class CaveSegment {
    
    private Vec2 m_upperEnd;
    private Vec2 m_lowerEnd;
    
    private final float m_minLength = 3;
    private final float m_maxLength = 6;
    private final float m_maxDiviation = (float)Math.PI/2;
    private float m_minWide = 6;
    private float m_maxWide = 18;
    
    private GameObject m_upperHalf;
    private GameObject m_lowerHalf;


    CaveSegment(WorldDefinition world, Vec2 upperEdge, Vec2 lowerEdge) {
        m_upperHalf = new GameObject(new Vec2(0, 0), BodyType.STATIC);
        m_lowerHalf = new GameObject(new Vec2(0, 0), BodyType.STATIC);
        
        ArrayList<Vec2> upperPoints = new ArrayList<Vec2>();
        ArrayList<Vec2> lowerPoints = new ArrayList<Vec2>();
        
        upperPoints.add(upperEdge);
        lowerPoints.add(lowerEdge);
        
        Vec2 lastUpper = upperEdge;
        Vec2 lastLower = lowerEdge;
        
        //Not so nice looking
        Vec2 newUpper, newLower;
        do{
            float r = (float)Math.random() * (m_maxLength - m_minLength) + m_minLength;
            float a = (float)Math.random() * m_maxDiviation - m_maxDiviation/2;
            newUpper = new Vec2( lastUpper.x + (r * (float)Math.cos(a))
                                    , lastUpper.y + (r * (float)Math.sin(a)));
            r = (float)Math.random() * (m_maxLength - m_minLength) + m_minLength;
            a = (float)Math.random() * m_maxDiviation - m_maxDiviation/2;
            newLower = new Vec2( lastLower.x + (r * (float)Math.cos(a))
                                    , lastLower.y + (r * (float)Math.sin(a)));
        }while(Math.abs(newUpper.y - newLower.y) < m_minWide 
                || Math.abs(newUpper.y - newLower.y) > m_maxWide);
        upperPoints.add(newUpper);
        lowerPoints.add(newLower);
        
        m_upperEnd = newUpper;
        m_lowerEnd = newLower;
        
        // the two points away from the cave
        upperPoints.add(new Vec2(m_upperEnd.x, m_upperEnd.y - 5));
        lowerPoints.add(new Vec2(m_lowerEnd.x, m_lowerEnd.y + 5));
        upperPoints.add(new Vec2(upperEdge.x, upperEdge.y - 5));
        lowerPoints.add(new Vec2(lowerEdge.x, lowerEdge.y + 5));
        
        Vec2[] upper = upperPoints.toArray(new Vec2[upperPoints.size()]);
        Vec2[] lower = lowerPoints.toArray(new Vec2[lowerPoints.size()]);
        
        // Set the points to the gameobject and create body
        m_upperHalf.setPoints(upper);
        m_lowerHalf.setPoints(lower);
        m_upperHalf.createBody(world.getPhysicsWorld());
        m_lowerHalf.createBody(world.getPhysicsWorld());
    }

    void draw(Graphics2D g, WorldDefinition worldDef) {
        m_upperHalf.draw(g, worldDef);
        m_lowerHalf.draw(g, worldDef);
    }

    Vec2 getLowerEnd() {
        return m_lowerEnd;
    }

    Vec2 getUpperEnd() {
        return m_upperEnd;
    }
    
}
