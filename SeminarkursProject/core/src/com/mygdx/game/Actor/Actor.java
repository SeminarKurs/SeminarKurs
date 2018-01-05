package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Tobias on 24.08.2017.
 */

public class Actor {

    public Actor(){}

    public void update(float dt) {}

    // return collison
    public com.mygdx.game.Types.Collision coll(){return com.mygdx.game.Types.Collision.none;}
    // returns the number of the image
    public int image(){return 0;}

    // true needs to be updated, false don't update
    public boolean needUpdate(){ return false;}
    // FLayer is the topmost layer to be drawn (but the player is further in the front)
    public void draw(Batch batch, int x, int y, Array<FLayer> fLayers){ DrawH.drawActor(batch, x,y, image());}

}
