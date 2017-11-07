package com.mygdx.game.Item;

/**
 * Created by Implodee on 22.10.2017.
 */

public class ItemToolMaster extends ItemMaster{

    protected int durability;
    protected float efficiency;

    public ItemToolMaster(String desc, int image, int durability, float efficiency, ItemId id){

        this.desc = desc;
        this.image = image;
        this.stackSize = 1;
        this.stackSizeMax = 1;
        this.durability = durability;
        this.efficiency = efficiency;
        this.id = id;
    }

    public float getEfficiency(){return efficiency;}
}