package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.Collision;

import static com.mygdx.game.Item.ItemId.MAT_IRON;

/**
 * Created by Christopher Schleppe on 01.11.2017.
 */

public class Oven extends Actor{
    // speed is 1 no need in Oven
    private ItemMaster returnedItem;

    private ItemMaster item;
    private ItemMaster coal;

    public Oven (){
        progress = 0;
    }

    public boolean setItem(ItemMaster item, Actor actor){

        if (item.getId() == ItemId.COAL){
            if (coal == null){
                coal = item;
            }else{
                coal.addStackSize(item.getStackSize());
            }
        }else{
            if(item!= null){
                if(this.item.getId() == item.getId()){
                    this.item.addStackSize(item.getStackSize());
                }
            }else   this.item = item;
            busy = true;
        }
        return false;
    }

    public void melt (){
        if (item.getStackSize() > 0 && coal.getStackSize() > 0) {
            if(returnedItem == null) {
                switch (item.getId()) {
                    case ORE_IRON:
                        if (returnedItem == null){
                            returnedItem = ItemList.mat_iron(1);
                        }
                        returnedItem.addStackSize(1);
                        break;
                    default:
                        return;
                }
            }else{
                returnedItem.addStackSize(1);
            }
            coal.addStackSize(-1);
            item.addStackSize(-1);
        }
    }

    @Override
    public void update (float dt){
        if(item != null && coal != null) {
            progress += dt;
            if (progress >= 2) {
                progress = 0;
                melt();
            }
        }
    }

    @Override
    public Collision coll() {
        return Collision.collides;
    }
    public int image(){return 3;}
    @Override
    public ItemMaster getItem() {
        return item;
    }

    public ItemMaster takeItem(int amount){
        switch (returnedItem.getId()){
            case MAT_IRON:
                if(returnedItem.getStackSize() >= amount) return ItemList.mat_iron(amount);
                break;
            default:
                break;
        }
        return null;
    }

    public ItemId getId() {
        return ItemId.OVEN;
    }
}
