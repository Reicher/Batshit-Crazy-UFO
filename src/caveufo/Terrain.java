/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import java.awt.Graphics2D;
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
        m_staticTerrain = new Vector<GameObject>();
        ActiveGameObject = new Vector<ActiveGameObject>();
        
        // Create a piece of terrain
        Vec2 pos = new Vec2(10, 10); 
        Vec2[] points = new Vec2[]{ new Vec2(-7, -0.5f), new Vec2(-7, 0.5f)
                , new Vec2(7, 0.5f), new Vec2(7, -0.5f) };
        GameObject piece = new GameObject(pos, BodyType.STATIC);
        piece.setPoints(points);
        piece.createBody(world.getPhysicsWorld());
        
        m_staticTerrain.add(piece);
    }
    
    public void draw(Graphics2D g, WorldDefinition world){
        for(GameObject piece : m_staticTerrain)
            piece.draw(g, world);
        
        for(ActiveGameObject piece : ActiveGameObject)
            piece.draw(g, world);
    }
    
    Vector<GameObject> m_staticTerrain;
    Vector<ActiveGameObject> ActiveGameObject; // Will be used later...
    
}
