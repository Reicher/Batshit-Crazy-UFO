/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package batshitUfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

/**
 *
 * @author regen
 */
public class TimeKeeper implements ContactListener{
    
    private int m_updateFrequency = 100; //ms
    private float m_checkpointTimeMax = 10f; //s
    private float m_checkpointTimeLeft = 0; //s
    private Timer m_checkpointTimer;
    private ActionListener m_listener;
    
    TimeKeeper(){
        m_listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(m_checkpointTimeLeft > 0.0f)
                    m_checkpointTimeLeft -= 0.1f;
            }
        };
        m_checkpointTimer = new Timer(m_updateFrequency, m_listener);
        m_checkpointTimer.start();
        resetTimer();
    }
    
    public void resetTimer(){
        m_checkpointTimeLeft = m_checkpointTimeMax;
    }
    
    public float getTimeLeft(){
        return m_checkpointTimeLeft;
    }
    
    @Override   
    public void beginContact(Contact contact) { 
        Object A = contact.getFixtureA().getBody().getUserData();
        Object B = contact.getFixtureB().getBody().getUserData();
        
        // Do i really need to mirror it?
        if(A.getClass().equals(Player.class)){
            if(B.getClass().equals(Checkpoint.class))
                resetTimer();
        }        
        else if(B.getClass().equals(Player.class)){
            if(A.getClass().equals(Checkpoint.class))
                resetTimer();
        }
    }
    
    @Override
    public void endContact(Contact cntct) {}
    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {}
    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {}

}
