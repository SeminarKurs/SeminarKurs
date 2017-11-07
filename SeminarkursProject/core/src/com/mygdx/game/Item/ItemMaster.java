package com.mygdx.game.Item;

/**
 * Created by Implodee on 22.10.2017.
 */

public class ItemMaster {

    protected String desc;
    protected int image;
    protected int stackSize;
    protected int stackSizeMax;
    protected ItemId id;

    public ItemMaster(String desc, int image, int stackSize, int stackSizeMax, ItemId id){

        this.desc = desc;
        this.image = image;
        this.stackSizeMax = stackSizeMax;
        this.id = id;
    }

    public ItemMaster(){}

    public String getDesc(){
        return desc;
    }

    public int getImage(){
        return image;
    }

    public int getStackSizeMax(){
        return stackSizeMax;
    }

    public ItemId getId(){return id;}

    public int getStackSize(){return stackSize;}

    public boolean addStackSize(int size){if(stackSize + size > stackSizeMax) return false; stackSize += size; return true;}
}