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
public class Terrain {
    Terrain(WorldDefinition world){
        m_caveSegment = new ArrayList<CaveSegment>();

        // Initial Cave Segments
        Vec2 upperEdge = new Vec2(0, -6);
        Vec2 lowerEdge = new Vec2(0, 6);
        m_caveSegment.add(new CaveSegment(world, upperEdge, lowerEdge, true));
        for(int i = 0; i < 40; i++)
            m_caveSegment.add(getNextSegment(world));

        
    }
    
    private CaveSegment getNextSegment(WorldDefinition world){
        CaveSegment Last = m_caveSegment.get(m_caveSegment.size() - 1);
        return new CaveSegment(world, Last.getUpperEnd(), Last.getLowerEnd(), false);
    }
    
    public void draw(Graphics2D g, WorldDefinition world){
        for( CaveSegment cs : m_caveSegment)
            cs.draw(g, world);
    }
    
    private ArrayList<CaveSegment> m_caveSegment;
}
