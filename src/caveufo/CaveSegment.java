/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;

/**
 *
 * @author regen
 */
public class CaveSegment {
    private LineObject m_upperLine;
    private LineObject m_lowerLine;
    
    // SHould be in some checkpoint class?
    private boolean m_isCheckpoint;
    private boolean m_isCheckpointUsed;
    
    private float m_direction;
    
    public final int m_id;

    // Direction should be possible to calculte from upper and lower though...
    CaveSegment(int id, LineObject upper, LineObject lower, float direction) {
        m_upperLine = upper;
        m_lowerLine = lower;
        m_id = id;
        m_isCheckpoint = false;
    }

    void draw(Graphics2D g, WorldDefinition worldDef) {        
        g.setStroke(new BasicStroke(3));
        
        m_upperLine.draw(g, worldDef);
        m_lowerLine.draw(g, worldDef);
        
        if(m_isCheckpoint){
            g.setColor(Color.GREEN);
            Vec2 pPM = worldDef.getPixelsPerMeter();
            g.drawLine( (int)(getUpperEnd().x * pPM.x), 
                        (int)(getUpperEnd().y * pPM.y), 
                        (int)(getLowerEnd().x * pPM.x), 
                        (int)(getLowerEnd().y * pPM.y));
            g.setColor(Color.BLACK);           
        }

        //DEBUG
        if(false){
            debugDraw(g, worldDef);
        }
    }
    
    void debugDraw(Graphics2D g, WorldDefinition worldDef){
        Vec2 pPM = worldDef.getPixelsPerMeter();
        Vec2 middle = new Vec2((getUpperEnd().x + getLowerEnd().x)/2f, 
                                (getUpperEnd().y + getLowerEnd().y)/2f);
        g.setColor(Color.RED);
        g.drawLine( (int)(middle.x*pPM.x), (int)(middle.y*pPM.y), 
                    (int)(getUpperEnd().x*pPM.x), (int)(getUpperEnd().y*pPM.y));

        g.setColor(Color.GREEN);
        g.drawLine( (int)(middle.x*pPM.x), (int)(middle.y*pPM.y), 
                    (int)(getLowerEnd().x*pPM.x), (int)(getLowerEnd().y*pPM.y));


        g.setColor(Color.BLACK);
    }
    
    public void setCheckpoint(){
        m_isCheckpoint = true;
        m_isCheckpointUsed = false;
    }
    
    public boolean isCheckpoint(){
        return m_isCheckpoint;
    }
    
    public boolean isCheckPointUsed(){
        return m_isCheckpointUsed;
    }
    
    public float getDirection(){
        return m_direction;
    }
    
    public Vec2 getUpperEnd() {
        return m_upperLine.getRight();
    }
    
    public Vec2 getLowerEnd() {
        return m_lowerLine.getRight();
    }
}
