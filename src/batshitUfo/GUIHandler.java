/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package batshitUfo;

import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author regen
 */
public class GUIHandler {
    private float m_timeLeft = 0;
    private float m_health = 0;
    private float m_score = 0;
    
    public GUIHandler( ){
        
    }
    
    public void setTimeLeft(float timeLeft){
        m_timeLeft = timeLeft;
    }
    
    public void setScore(float Score){
        m_score = Score;
    }
        
    public void setHealth(float health){
        m_health = health;
    }
    
    public void draw(Graphics2D g, WorldDefinition worldDef){        
        drawCrudeTexts(g, worldDef);
    }
    
    private void drawCrudeTexts(Graphics2D g, WorldDefinition worldDef){
        int textSize = 30;
        g.setFont(new Font("TimesRoman", Font.PLAIN, textSize)); 
        
        g.drawString("Time left: " +  String.format("%.2f", m_timeLeft)
                , 10
                , 30);
        
        g.drawString("Health: " +  String.format("%.2f", m_health)
        , 10
        , 40 + textSize);
        
        g.drawString("Score: " +  String.format("%.2f", m_score)
        , 10
        , 50 + textSize*2);
    }
}
