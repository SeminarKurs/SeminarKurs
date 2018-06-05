package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Tobias on 24.11.2017.     Improved by Chris
 */

public class Miner extends StorageActor {

    public float progress;
    public float speed = 0.5f;
    public ItemMaster item;

    public Miner(IVector2 pos)
    {
        this.pos = pos;
        storage.setSize(2);
    }

    @Override
    public void update(float dt){

        if(WorldM.hasResource(pos)) {

            Tile t = WorldM.getResource(pos);
            if (t.hasRes()) {
                progress += dt * speed / t.resHardness();
                // look if he should mine the resources
                if (progress >= 1) {
                    // make shure there are still resources
                    if (t.resAmount() < (int) progress) {
                        if(!moveItemToActor(item, pos)){
                            progress = 1f;
                            return;
                        }
                        WorldM.updateResource(pos);
                    }else{
                        t.resSetAmount(t.resAmount() - (int) progress);
                        // decrease coal
                        if(t.getResource().getId() == ItemId.COAL){
                            if(item == null){
                                item = ItemList.coal((int) progress);
                            }else{
                                item.addStackSize((int) progress);
                            }
                        }

                    }
                    //progress -= (int)progress;
                }
            }
        }else{
            if(!moveItemToActor(item, pos)) return;
        }
    }

    public Actor assistingMethodForCheckForNearActor(IVector2 pos, Direction richtung){
        Actor a;
        if ((a = WorldM.getActor(pos)) != null) {
            if(a.getId() == ItemId.CLUTCH && a.getDirection() == richtung) return a;
        }
        return null;
    }

    public Actor checkForNearActor(IVector2 pos){
        Actor a;
        if(pos.x != WorldM.WIDTH) {
            a = assistingMethodForCheckForNearActor(new IVector2(pos.x + 1, pos.y), Direction.right);
            if (a != null) return a;
        }
        if(pos.x != 0) {
            a = assistingMethodForCheckForNearActor(new IVector2(pos.x - 1, pos.y), Direction.left);
            if (a != null) return a;
        }
        if(pos.y != WorldM.HEIGHT) {
            a = assistingMethodForCheckForNearActor(new IVector2(pos.x, pos.y + 1), Direction.up);
            if (a != null) return a;
        }
        if(pos.y != 0) {
            a = assistingMethodForCheckForNearActor(new IVector2(pos.x, pos.y - 1), Direction.down);
            if (a != null) return a;
        }
        return null;
    }

    public boolean moveItemToActor (ItemMaster item, IVector2 pos){
        Actor a = checkForNearActor(pos);
        if (a != null){
            if(a.getId() == ItemId.CLUTCH && !a.isBusy()){
                a.setItem(item, null);
                System.out.println(item.getStackSize());
                return true;
            }else   return false;
        }
        return false;
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
