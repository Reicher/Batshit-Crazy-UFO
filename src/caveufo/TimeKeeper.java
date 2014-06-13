/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

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
    
    private int m_updateFrequency = 1000; //ms
    private int m_checkpointTimeMax = 10; //s
    private int m_checkpointTimeLeft = 10; //s
    private Timer m_checkpointTimer;
    private ActionListener m_listener;
    
    TimeKeeper(){
        m_listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(m_checkpointTimeLeft > 0)
                    m_checkpointTimeLeft--;
                
                System.out.println(m_checkpointTimeLeft);
            }
        };
        m_checkpointTimer = new Timer(m_updateFrequency, m_listener);
        m_checkpointTimer.start();
        resetTimer();
    }
    
    public void resetTimer(){
        m_checkpointTimeLeft = m_checkpointTimeMax;
    }
    
    public int getTimeLeft(){
        return m_checkpointTimeLeft;
    }

}
