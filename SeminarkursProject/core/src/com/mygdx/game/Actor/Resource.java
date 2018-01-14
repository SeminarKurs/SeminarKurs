package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Tobias on 22.10.2017.
 */

public class Resource{

    // the type of the resource also the image num
    private int type;
    // the amount that can be gained
    public int amount = 500;

    public Resource(int type)
    {
        this.type = type;
    }

    public void draw(Batch batch, int x, int y)
    {
        DrawH.drawResource(batch, x,y, type);
    }
    public int hardness(){return 1;}
    public int getType() { return type; }
}
