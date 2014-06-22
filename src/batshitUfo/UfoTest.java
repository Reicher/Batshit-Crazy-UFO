/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package batshitUfo;

import batshitUfo.Player.RotationAction;
import java.awt.Dimension;
import java.awt.Insets;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

/**
 *
 * @author regen
 */
public class UfoTest extends TestbedTest {
    
    WorldDefinition m_world;
    Player m_player;

    @Override
    public void initTest(boolean bln) {
        setTitle("Couple of Things Test");
        m_world = new WorldDefinition(new Dimension(800, 600));
        m_world.SetWorld(getWorld());
        getWorld().setGravity(new Vec2(0, -9.82f));
        
        // Rocket
        m_player = new Player();
        m_player.createBody(getWorld());
        
        // Ground
        Vec2 pos = new Vec2(0, -10); 
        Vec2[] points = new Vec2[]{ new Vec2(-7, -0.5f), new Vec2(-7, 0.5f)
                , new Vec2(7, 0.5f), new Vec2(7, -0.5f) };
        BodyDef bd = new BodyDef();
        bd.position.set( pos.x, pos.y);  
        bd.type = BodyType.STATIC;
        
         // SHAPE
        PolygonShape ps = new PolygonShape();
        ps.set(points, points.length);
        
        // Find center of Body
        Vec2 centre = new Vec2(0.0f, 0.0f);
        for(Vec2 point : points)
            centre.add(point);
        
        centre.x = centre.x / points.length;
        centre.y = centre.y / points.length;
        ps.m_centroid.set(centre);
        
        // FIXTURE
        FixtureDef fix =  new FixtureDef();
        fix.shape = ps;
        fix.density = 0.6f;
        fix.friction = 0.3f;        
        fix.restitution = 0.5f;
        
        Body ground = getWorld().createBody(bd);
        ground.createFixture(fix);
        
    }
    
    // let's say we're in a custom test
    @Override
    public void step(TestbedSettings settings) {
        super.step(settings);

        TestbedModel model = getModel();
        if (model.getKeys()['w']) { // model also contains the coded key values
            m_player.setThrust(true);
        }

        if (model.getKeys()['q']) { // model also contains the coded key values
            m_player.setRotation(RotationAction.Left);
        }
        
        if (model.getKeys()['e']) { // model also contains the coded key values
            m_player.setRotation(RotationAction.Left);
        }
        
        m_player.update();

    }
    
    @Override
    public String getTestName() {
        return "Test stuff";
    }
    
}
