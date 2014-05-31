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
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 *
 * @author regen
 */
public class GameObject {
    GameObject(Vec2 pos, BodyType bType){
        m_points = new Vec2[]{ new Vec2(0.0f, 0.0f) };
        
        // Body
        m_bodyDef = new BodyDef();
        m_bodyDef.position.set( pos.x, pos.y);  
        m_bodyDef.type = bType;
    }
    
    public void setPoints(Vec2 points[]){
        // Graphics
        m_points = points;
    }
    
    public void createBody( World world ){
        // SHAPE
        m_shape = new PolygonShape();
        m_shape.set(m_points, m_points.length);
        
        // Find center of Body
        Vec2 centre = new Vec2(0.0f, 0.0f);
        for(Vec2 point : m_points)
            centre.add(point);
        
        centre.x = centre.x / m_points.length;
        centre.y = centre.y / m_points.length;
        m_shape.m_centroid.set(centre);
        
        // FIXTURE
        m_fixture = new FixtureDef();
        m_fixture.shape = m_shape;
        m_fixture.density = 0.6f;
        m_fixture.friction = 0.3f;        
        m_fixture.restitution = 0.5f;
        
        m_body =  world.createBody(m_bodyDef);
        m_body.createFixture(m_fixture);
    }
    
    public void draw(Graphics2D g, WorldDefinition worldDef){
        
        GeneralPath goShape = new GeneralPath();
        Vec2 point = m_body.getWorldPoint(m_shape.getVertex(0));
        
        Vec2 pPM = worldDef.getPixelsPerMeter();
        
        goShape.moveTo(point.x * pPM.x, point.y * pPM.y);
        
        for (int i = 1; i < m_shape.getVertexCount(); i++){
            point = m_body.getWorldPoint(m_shape.getVertex(i));
            goShape.lineTo(point.x * pPM.x, point.y * pPM.y);
        }
        
        goShape.closePath();
        g.draw(goShape);  
    }   
    
    public Vec2 getPosition(){
        return m_body.getWorldCenter();
    }
 
    protected Body m_body;
    
    private BodyDef m_bodyDef;
    private PolygonShape m_shape;
    private FixtureDef m_fixture;
    private Vec2 m_points[];
}
