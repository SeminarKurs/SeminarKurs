package com.mygdx.game.Item;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Implodee on 22.10.2017.
 */

public class ItemWeaponMaster extends ItemMaster{

    protected int damage;
    protected int speed;

    public ItemWeaponMaster(String name, String desc, Texture tex, int damage, int speed, int id){
        this.name = name;
        this.desc = desc;
        this.tex = tex;
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
