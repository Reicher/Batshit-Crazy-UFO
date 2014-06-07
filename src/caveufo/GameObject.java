/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 *
 * @author regen
 */
abstract class GameObject {
    
    protected Body m_body;
    
    private BodyDef m_bodyDef;
    protected Shape m_shape;
    private FixtureDef m_fixture;
    private Vec2 m_points[];
    
    protected GameObject(Vec2 points[]){
        
        // Body
        m_bodyDef = new BodyDef();
        m_bodyDef.position.set( 0, 0);  
        m_bodyDef.type = BodyType.STATIC;
        m_points = points;
    }
    
    protected void setPoints(Vec2 points[]){
        m_points = points;
    }
    
    protected void setBodyType(BodyType bType){
        m_bodyDef.type = bType;
    }
    
    protected abstract void createShape(Vec2 points[]);
    
    public void createBody( World world ){
        // SHAPE
        createShape(m_points);
 
        // FIXTURE
        m_fixture = new FixtureDef();
        m_fixture.shape = m_shape;
        m_fixture.density = 0.6f;
        m_fixture.friction = 0.3f;        
        m_fixture.restitution = 0.2f;
        
        
        m_body =  world.createBody(m_bodyDef);
        m_body.createFixture(m_fixture);
    }
    
    public void draw(Graphics2D g, WorldDefinition worldDef){
        Vec2 pPM = worldDef.getPixelsPerMeter();
        
        if(m_shape.getClass() == PolygonShape.class){
            GeneralPath goShape = new GeneralPath();
            Vec2 point;
            point = ((PolygonShape)m_shape).getVertex(0);
            point = m_body.getWorldPoint(point);

            goShape.moveTo(point.x * pPM.x, point.y * pPM.y);

            for (int i = 1; i < ((PolygonShape)m_shape).getVertexCount(); i++){
                point = ((PolygonShape)m_shape).getVertex(i);
                point = m_body.getWorldPoint(point);
                goShape.lineTo(point.x * pPM.x, point.y * pPM.y);
            }

            goShape.closePath();            
            g.draw(goShape); 
        }
        else if(m_shape.getClass() == EdgeShape.class){
            Vec2 start = ((EdgeShape)m_shape).m_vertex1;
            Vec2 stop = ((EdgeShape)m_shape).m_vertex2;
            g.drawLine(
                    (int)(start.x * pPM.x), 
                    (int)(start.y * pPM.y),
                    (int)(stop.x * pPM.x),
                    (int)(stop.y * pPM.y));
        }
        
 
    }   
    
    public Vec2 getPosition(){
        return m_body.getWorldCenter();
    }
}
