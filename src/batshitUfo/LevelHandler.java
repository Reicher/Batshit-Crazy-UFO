/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package batshitUfo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

/**
 *
 * @author regen
 */
public class LevelHandler{
    private ArrayList<CaveSegment> m_caveSegments;
    private CaveSegment m_lastSegment;
    private WorldDefinition m_world;

    LevelHandler(WorldDefinition world){
        m_world = world;
        CaveFactory.setWorld(world);
        m_caveSegments = new ArrayList<CaveSegment>();

        GenerateNewLevel();
    }
    
    public void GenerateNewLevel(){
        for(CaveSegment segment : m_caveSegments)
            segment.removeSegment(m_world.getPhysicsWorld());
            
        m_caveSegments.clear();
                
        // Initial Cave Segment
        m_lastSegment = CaveFactory.getFirst();
        m_caveSegments.add(m_lastSegment);
    }
    
    private void addSegment(){
        m_lastSegment = CaveFactory.getNext(m_lastSegment);
        m_caveSegments.add(m_lastSegment);
    }
    
    public void draw(Graphics2D g, WorldDefinition world){
        for( CaveSegment cs : m_caveSegments)
            cs.draw(g, world);
    }
    
    // Grows the map infront of the player
    public void update(Vec2 position){   
        while(position.x + m_world.getPhysicalSize().x > m_lastSegment.getLowerEnd().x)
            addSegment();   
    }
    
    void checkpointTaken(int id){
        System.out.println("SOME KIND OF CHECKPOINT TAKEN, " + id);
        for( CaveSegment cs : m_caveSegments)
            if(cs.isCheckpoint() && cs.getId() == id)
                cs.removeCheckpoint(m_world.getPhysicsWorld());
    }
}
