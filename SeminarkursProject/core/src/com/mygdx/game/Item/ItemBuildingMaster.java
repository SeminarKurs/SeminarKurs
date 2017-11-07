package com.mygdx.game.Item;

/**
 * Created by Implodee on 22.10.2017.
 */

public class ItemBuildingMaster extends ItemMaster{

    protected int size;

    public ItemBuildingMaster(String desc, int image, int size, ItemId id){

        this.desc = desc;
        this.image = image;
        this.stackSize = 1;
        this.stackSizeMax = 1;
        this.size = size;
        this.id = id;
    }

    public int getSize(){
        return size;
    }
}