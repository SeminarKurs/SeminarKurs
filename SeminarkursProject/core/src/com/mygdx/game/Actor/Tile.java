package com.mygdx.game.Actor;

import com.badlogic.gdx.utils.Array;

/**
 * Created by 745379 on 02.09.2017.
 */




public class Tile {
    public int type = 0;
    // 0 non 1 overllapp 2 block
    public int collision;
    public Array<Actor> actors = new Array<Actor>();

}
