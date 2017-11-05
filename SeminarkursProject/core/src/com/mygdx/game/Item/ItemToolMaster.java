package com.mygdx.game.Item;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Implodee on 22.10.2017.
 */

public class ItemToolMaster extends ItemMaster{

    protected int durability;
    protected float efficiency;

    public ItemToolMaster(String name, String desc, Texture tex, int durability, float efficiency, int id){
        this.name = name;
        this.desc = desc;
        this.tex = tex;
        this.stackSize = 1;
        this.stackSizeMax = 1;
        this.durability = durability;
        this.efficiency = efficiency;
        this.id = id;
    }
}