package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.Collision;

/**
 * Created by Christopher Schleppe on 01.11.2017.
 */

public class Oven extends Actor{

    private ItemMaster returnedItem;

    private float progress = -2f;

    private ItemMaster item;
    private ItemMaster coal;


    public void addItem(ItemMaster item){

        if (item.getId() == ItemId.COAL){
            if (coal == null){
                coal = item;
            }else{
                coal.addStackSize(item.getStackSize());
            }
        }else{
            if(this.item.getId() == item.getId()){
                this.item.addStackSize(item.getStackSize());
            }
        }
    }

    public void melt (){
        if (item == null || coal == null){
            return;
        }else{
            if (item.getStackSize() > 0 && coal.getStackSize() > 0) {
                switch (item.getId()) {
                    case ORE_IRON:
                        returnedItem = ItemList.mat_iron(1);
                        break;
                    default:
                        return;
                }
                coal.addStackSize(-1);
                item.addStackSize(-1);
            }
        }
    }

    @Override
    public void update (float dt){
        if(item != null) {
            progress += dt;
            if (progress >= 2) {
                progress -= 2f;
                melt();
                busy = false;
            }
        }
    }

    @Override
    public Collision coll() {
        return Collision.collides;
    }
    public int image(){return 3;}

    public boolean setItem(ItemMaster item) {
        this.item = item;
        busy = true;
        return false;
    }

    public ItemId getId() {
        return ItemId.OVEN;
    }
}
