package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Tobias on 24.08.2017.
 */

public class Actor {

    public Actor(){}

    // needs to be updated, false don't update
    boolean needUpdate = false;

    public void update(float dt) {

    }

    // return collison
    public int coll(){return 0;}
    // returns the number of the image
    public int image(){return 0;}

    public boolean GetNeedUpdate(){ return needUpdate;}
    public void draw(Batch batch, int x, int y)
    {
        DrawH.drawActor(batch, x,y, image());
    }
}
