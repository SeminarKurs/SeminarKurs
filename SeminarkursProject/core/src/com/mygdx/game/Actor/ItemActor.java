package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.ItemMaster;

/**
 * Created by Tobias on 23.10.2017.
 */

public class ItemActor {

    ItemMaster item;

    // returns the number of the image
    public int image(){return 1;}

    public void draw(Batch batch, int x, int y)
    {
        DrawH.drawItemActor(batch, x,y, image());
    }

    public void draw(Batch batch, float x, float y)
    {
        DrawH.drawItemActor(batch, x,y, image());
    }
}
