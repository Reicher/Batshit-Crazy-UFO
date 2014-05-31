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
        Vec2 upperEdge = new Vec2(0, -3);
        Vec2 lowerEdge = new Vec2(0, 3);
        for(int i = 0; i < 40; i++){
            CaveSegment Next = new CaveSegment(world, upperEdge, lowerEdge);
            m_caveSegment.add(Next);
            upperEdge = Next.getUpperEnd();
            lowerEdge = Next.getLowerEnd();
        }
    }
    
    public void draw(Graphics2D g, WorldDefinition world){
        for( CaveSegment cs : m_caveSegment)
            cs.draw(g, world);
    }
    
    private ArrayList<CaveSegment> m_caveSegment;
}
