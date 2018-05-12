package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Tobias on 24.11.2017.
 */

public class Miner extends StorageActor {

    int energy  = 100;
    float progress;
    float speed = 0.5f;
    IVector2 pos;
    ItemMaster item;

    public Miner(IVector2 pos)
    {
        //needUpdate = true;
        this.pos = pos;
        storage.setSize(2);
    }

    @Override
    public void update(float dt){

        if(energy > 0 && WorldM.hasResource(pos)) {

            Tile t = WorldM.getResource(pos);
            if (t.hasRes()) {
                progress += dt * speed / t.resHardness();
                // look if he should mine the resources
                if (progress >= 1) {
                    // make shure there are still resources
                    if (t.resAmount() < (int) progress) {
                        item = ItemList.coal(t.resAmount());
                        this.moveItemToActor(item, pos);
                        t.resSetAmount(0);
                        WorldM.updateResource(pos);
                    }else{
                        t.resSetAmount(t.resAmount() - (int) progress);
                        // decrease coal
                        item = ItemList.coal((int)progress);
                    }
                }
            }
        }
    }
    public void moveItemToActor (ItemMaster item, IVector2 pos){
        Actor a = checkForNearActor(pos);
        if (a != null){
            System.out.println("nervt");

            a.setItem(item);
        }
    }


    @Override
    public Collision coll() {
        return Collision.collides;
    }

    @Override
    public boolean needUpdate() {
        return true;
    }

    @Override
    public int image() {
        return 2;
    }

    @Override
    public ItemMaster getItem() {
        return storage.get(2);
    }

    @Override
    public ItemMaster takeItem() {
        storage.set(2, null);
        return storage.get(2);
    }

    @Override
    public boolean setItem(ItemMaster item) {
        if(storage.get(1) == null)
        {
            storage.set(1, item);
            return true;
        }
        else
        {
            if (storage.get(1).getId() == item.getId())
            {
                storage.get(1).addStackSize(item.getStackSize());
                return true;
            }
            return false;
        }
    }
    public ItemId getId() {
        return ItemId.MINER;
    }
}
