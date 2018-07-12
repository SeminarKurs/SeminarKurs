package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Christopher Schleppe on 04.01.2018.
 */

public class ElectricOven extends ElectricActor {

    private ItemMaster item;
    private ItemMaster returnedItem;

    public ElectricOven(){
        maxCapacity = 10;
        progress = 0;
    }


    public void melt (){
        if (item.getStackSize() > 0 && capacity > 0) {
            switch (item.getId()) {
                case ORE_IRON:
                    if (returnedItem == null)   returnedItem = ItemList.mat_iron(1);
                    returnedItem.addStackSize(1);
                    break;
                default:
                    return;
            }
            capacity--;
            item.addStackSize(-1);
        }
    }
    @Override
    public void update (float dt){
        if(item != null && capacity >= 0) {
            progress += dt;
            if (progress >= 100) {
                progress -= 100;
                melt();
            }
        }
    }

    @Override
    public Collision coll() {
        return Collision.collides;
    }
    public int image(){return 5;}


    public ItemMaster getItem() {
        return item;
    }

    @Override
    public boolean setItem(ItemMaster item) {
        return false;
    }

    @Override
    public boolean setItem(ItemMaster item, Actor actor) {
        if(this.item != null) {
            if (this.item.getId() == item.getId()) {
                this.item.addStackSize(item.getStackSize());
                return true;
            }
        }else{
            this.item = item;
            return true;
        }
        return false;
    }


    public ItemId getId() {
        return ItemId.ELECTRICOVEN;
    }
}
