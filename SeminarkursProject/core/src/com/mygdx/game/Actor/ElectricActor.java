package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemMaster;

/**
 * Created by valen on 28.05.2018.
 */

public abstract class ElectricActor extends Actor {
    protected int capacity = 0;
    protected int maxCapacity;

    public boolean movePowerToElectricActor(){return false;}

    public boolean addCapacity (int capacity){
        if(this.capacity + capacity <= maxCapacity) {
            this.capacity += capacity;
            return true;
        }else{
            return false;
        }
    }

    public int getCapacity (){return capacity;}

    public ItemMaster getItem() {
        return null;
    }
    public boolean setItem(ItemMaster item) {
        return false;
    }
}
