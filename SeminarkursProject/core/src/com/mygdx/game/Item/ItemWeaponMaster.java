package com.mygdx.game.Item;

/**
 * Created by Implodee on 22.10.2017.
 */

public class ItemWeaponMaster extends ItemMaster{

    protected int damage;
    protected int speed;

    public ItemWeaponMaster(String desc, int image, int damage, int speed, ItemId id){

        this.desc = desc;
        this.image = image;
        this.id = id;
        this.damage = damage;
        this.speed = speed;
        this.stackSize =1;
        this.stackSizeMax = 1;
    }

    public int getDamage(){
        return damage;
    }
    public int getSpeed(){
        return speed;
    }
}
