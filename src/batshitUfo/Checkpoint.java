/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package batshitUfo;

import java.awt.Color;
import java.awt.Graphics2D;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

/**
 *
 * @author regen
 */
public class Checkpoint extends LineObject{
    
    private boolean m_used;
    public final int m_id;
    
    public Checkpoint(Vec2[] points, int id){
        super(points);
        m_used = false;
        m_fixture.isSensor = true;
        m_id = id;
    }
    
    public void draw(Graphics2D g, WorldDefinition worldDef){
        g.setColor(Color.GREEN);
        super.draw(g, worldDef);
        g.setColor(Color.BLACK);      
    }

    public boolean Take(){
        if(!m_used){
            m_used = true;
            return true;
        }
        return false;
    }
    
    public boolean isUsed(){
        return m_used;
    }
    
    @Override
    public void createBody( World world ){
        super.createBody(world);
        m_body.setUserData(this);
    }
}
