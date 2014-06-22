/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package batshitUfo;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

/**
 *
 * @author regen
 */
public class LineObject extends GameObject{

    public LineObject(Vec2[] points) {
        super(points);
    }

    @Override
    protected void createShape(Vec2[] points) {
        m_shape = new EdgeShape();
        ((EdgeShape)m_shape).set(points[0], points[1]);     
    }
    
    public Vec2 getLeft(){
        return ((EdgeShape)m_shape).m_vertex1;
    }
    
    public Vec2 getRight(){
        return ((EdgeShape)m_shape).m_vertex2;
    }
}
