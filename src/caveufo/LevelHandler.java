/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

/**
 *
 * @author regen
 */
public class LevelHandler {
    private ArrayList<CaveSegment> m_all;
    private ArrayList<CaveSegment> m_current;

    LevelHandler(WorldDefinition world){
        CaveFactory.setWorld(world);
        m_all = new ArrayList<CaveSegment>();
        
        // Initial Cave Segments
        m_all.add(CaveFactory.getFirst());
        for(int i = 0; i < 40; i++)
            m_all.add(CaveFactory.getNext(m_all.get(m_all.size() - 1)));
    }
    
    public void draw(Graphics2D g, WorldDefinition world){
        for( CaveSegment cs : m_all)
            cs.draw(g, world);
        
    }
}
