package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Types.Collision;

/**
 * Created by Christopher Schleppe on 04.01.2018.
 */

public class ElectricOven extends StorageActor {
    private float progress;

    private ItemMaster item;
    private ItemMaster returnedItem;
    private int battery; // max = 10

    public boolean addItem(ItemMaster item){
        if(this.item.getId() == item.getId()){
            this.item.addStackSize(item.getStackSize());
            return true;
        }
        return false;
    }
    
    public void addEnergy (int energy){
        if (battery + energy <= 10){
            battery += energy;
        }
    }

    public void melt (){
        if (item == null){
            return;
        }else{
            if (item.getStackSize() > 0 && battery > 0) {
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
                battery--;
                item.addStackSize(-1);
            }
        }
    }
    @Override
    public void update (float dt){
        progress += dt;
        if (progress >= 100){
            progress -= 100;
            melt();
        }
    }

    @Override
    public Collision coll() {
        return Collision.collides;
    }
    public int image(){return 0;}



    public ItemMaster getItem() {
        return item;
    }

    @Override
    public ItemMaster takeItem() {
        return null;
    }

    @Override
    public boolean setItem(ItemMaster Item) {
        return false;
    }

    public ItemId getId() {
        System.out.println("ElectricOven needs to be implemented");
        return null;
    }
}
