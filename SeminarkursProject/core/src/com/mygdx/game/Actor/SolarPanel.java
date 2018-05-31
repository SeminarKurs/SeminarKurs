package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.IVector2;

/**
 * Created by Christopher Schleppe on 04.01.2018.
 */

public class SolarPanel extends ElectricActor {

    private float progress = 0;

    public SolarPanel (IVector2 pos){
        this.pos = pos;
        maxCapacity = 10;
    }

    public void update (float dt){
        progress += dt/10;
        if (progress <= 20){
            progress = 0;
            if (capacity < 10) capacity++; // generate
        }
    }

    public Collision coll(){return Collision.collides;}

    public int image(){return 7;}



    public ItemId getId() {
        return ItemId.SOLARPANEL;
    }

    @Override
    public boolean movePowerToElectricActor() {return false;}
}
