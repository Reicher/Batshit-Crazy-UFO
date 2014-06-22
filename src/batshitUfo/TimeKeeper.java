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
public class TimeKeeper{
    
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

}