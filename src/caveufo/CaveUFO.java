/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import javax.swing.JFrame;

/**
 *
 * @author regen
 */
public class CaveUFO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Cave UFO");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new GamePanel());
        window.pack();
        //window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setVisible(true);
    }
    
}
