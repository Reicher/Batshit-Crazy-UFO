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
    
    private float m_direction;

    // Direction should be possible to calculte from upper and lower though...
    CaveSegment(LineObject upper, LineObject lower, float direction) {
        m_upperLine = upper;
        m_lowerLine = lower;
    }

    void draw(Graphics2D g, WorldDefinition worldDef) {        
        g.setStroke(new BasicStroke(3));
        
        m_upperLine.draw(g, worldDef);
        m_lowerLine.draw(g, worldDef);

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
    
    float getDirection(){
        return m_direction;
    }
    
    Vec2 getUpperEnd() {
        return m_upperLine.getRight();
    }
    
    Vec2 getLowerEnd() {
        return m_lowerLine.getRight();
    }
}
