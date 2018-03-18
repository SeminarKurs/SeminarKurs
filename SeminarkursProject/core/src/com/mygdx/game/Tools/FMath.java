package com.mygdx.game.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by 745379 on 20.09.2017.
 */

public class FMath {

    public static IVector2 getTile(Vector2 pos){return new IVector2((int)(pos.x +0.5),(int)(pos.y+0.5));}
    public static IVector2 getTile(Vector3 pos){return new IVector2((int)(pos.x +0.5),(int)(pos.y+0.5));}
    public static IVector2 getTile(float x, float y){return new IVector2((int)(x +0.5),(int)(y+0.5));}

}
