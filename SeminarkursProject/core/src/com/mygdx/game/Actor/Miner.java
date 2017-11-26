package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Types.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Tobias on 24.11.2017.
 */

public class Miner extends Actor {

    int energy  = 100;
    float progress;
    float speed = 1;
    IVector2 pos;
    ItemMaster item;

    public Miner(IVector2 pos)
    {
        this.pos = pos;
    }

    @Override
    public void update(float dt) {

        if(energy > 0 && WorldM.hasResource(pos)) {

            Tile t = WorldM.getResource(pos);
            if (t.hasRes()) {
                progress += dt * speed / t.resHardness();
                if (progress >= 1) {
                    if (t.resAmount() < (int) progress) {
                        item = ItemList.coal(t.resAmount());
                        t.resSetAmount(0);
                        WorldM.updateResource(pos);
                    }else{
                        t.resSetAmount(t.resAmount() - (int) progress);
                        item = ItemList.coal((int)progress);
                    }
                }
            }
        }
    }

    @Override
    public Collision coll() {
        return Collision.collides;
    }

    @Override
    public boolean GetNeedUpdate() {
        return true;
    }

    @Override
    public int image() {
        return 2;
    }
}
