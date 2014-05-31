/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import javax.swing.JFrame;
import org.jbox2d.testbed.framework.TestList;
import org.jbox2d.testbed.framework.TestbedController;
import org.jbox2d.testbed.framework.TestbedController.UpdateBehavior;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSetting.SettingType;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

/**
 *
 * @author regen
 */
public class CaveUFO {

    /**
     * @param args the command line arguments
     */
    public static void runTests(){
        TestbedModel model = new TestbedModel();         // create our model

        // add tests
        TestList.populateModel(model);                   // populate the provided testbed tests
        model.addCategory("My Super Tests");             // add a category
        model.addTest(new UfoTest());                // add our test
        
        // add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
        model.getSettings().addSetting(new TestbedSetting("My Range Setting", SettingType.ENGINE, 10, 0, 20));

        TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

        JFrame testbed; // put both into our testbed frame
        testbed = new TestbedFrame(model, panel, UpdateBehavior.UPDATE_CALLED);
        // etc
        testbed.setVisible(true);
        testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        JFrame window = new JFrame("Cave UFO");
        
        if( false ){
            runTests();
        }
        else{
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setContentPane(new GamePanel());
            window.pack();
            //window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            window.setVisible(true);
        }
    }
    
}
