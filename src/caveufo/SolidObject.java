/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

/**
 *
 * @author regen
 */
public class SolidObject extends GameObject{

    public SolidObject(Vec2[] points) {
        super(points);            
    }
    
    @Override
    protected void createShape(Vec2 points[]){
        m_shape = new PolygonShape();
        ((PolygonShape)m_shape).set(points, points.length);
    }
    
}
