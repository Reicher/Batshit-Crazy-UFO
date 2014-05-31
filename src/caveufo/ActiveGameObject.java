/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caveufo;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

/**
 *
 * @author regen
 */

public class ActiveGameObject extends GameObject {

    ActiveGameObject(Vec2 pos){
        super(pos, BodyType.DYNAMIC);
    }
}
