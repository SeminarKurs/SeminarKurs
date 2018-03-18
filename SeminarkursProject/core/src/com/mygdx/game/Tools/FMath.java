package com.mygdx.game.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by 745379 on 20.09.2017.
 */

public class FMath {

    public static IVector2 getTile(Vector2 pos){ return getTile(pos.x, pos.y); }
    public static IVector2 getTile(Vector3 pos){return getTile(pos.x, pos.y);}
    public static IVector2 getTile(float x, float y){
        if(x < 0){ x --; } // -0.01 to -0.99 it will be raunded to 0 so whe have two 0,0 positons
        if(y < 0){ y --; } // -0.01 to -0.99 it will be raunded to 0 so whe have two 0,0 positons
        return new IVector2((int)(x +0.5),(int)(y+0.5));
    }
}
