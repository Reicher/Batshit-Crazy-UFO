/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import java.awt.Graphics2D;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;

/**
 *
 * @author regen
 */
public class CaveSegment {
    
    private Vec2 m_upperEnd;
    private Vec2 m_lowerEnd;
    
    private final float m_minLength = 1;
    private final float m_maxLength = 10;
    private final float m_maxDiviation = (float)Math.PI/2;
    private final float m_minWide = 7;
    private final float m_maxWide = 18;
    private final float m_formations = 0.4f;
    private final float m_maxFormation = 4;
    
    private LineObject m_upperLine;
    private LineObject m_lowerLine;
    private SolidObject m_stalagmite;
    private SolidObject m_stalactite;


    CaveSegment(LineObject upper, LineObject lower ) {
        m_upperLine = upper;
        m_lowerLine = lower;
    }


    void draw(Graphics2D g, WorldDefinition worldDef) {
        m_upperLine.draw(g, worldDef);
        m_lowerLine.draw(g, worldDef);
        
        if(m_stalagmite != null)
            m_stalagmite.draw(g, worldDef);
        if(m_stalactite != null)
            m_stalactite.draw(g, worldDef);
    }

    Vec2 getUpperEnd() {
        return m_upperLine.getRight();
    }
    
    Vec2 getLowerEnd() {
        return m_lowerLine.getRight();
    }
    
}
