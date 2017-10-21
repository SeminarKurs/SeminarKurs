package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Tobias on 24.08.2017.
 */

public class Actor {
    public Actor(){}

    public void update(float dt)
    {

    }

    // return collison
    public int coll(){return 0;}
    // returns the number of the image
    public int image(){return 1;}

    public void draw(Batch batch, int x, int y)
    {
        DrawH.draw(batch, x,y, image());
    }
}
