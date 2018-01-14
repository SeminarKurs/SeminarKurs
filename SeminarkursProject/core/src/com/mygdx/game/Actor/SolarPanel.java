package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Types.Collision;

/**
 * Created by Christopher Schleppe on 04.01.2018.
 */

public class SolarPanel extends StorageActor {

    private int battery; // max = 10

    private float progress = -0.5f;


    public void update (float dt){
        progress += dt/200;
        if (progress <= 100f){
            progress -= 100f;
            if (battery <= 10) battery++;
        }
    }

    public Collision coll(){return Collision.collides;}

    public int image(){return 0;}

    @Override
    public ItemMaster getItem() {
        return null;
    }

    @Override
    public ItemMaster takeItem() {
        return null;
    }

    @Override
    public boolean setItem(ItemMaster Item) {
        return false;
    }
}
