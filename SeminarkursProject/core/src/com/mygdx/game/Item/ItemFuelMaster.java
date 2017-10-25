package com.mygdx.game.Item;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Implodee on 22.10.2017.
 */

public class ItemFuelMaster extends ItemMaster{

    protected float fuelPower;

    public ItemFuelMaster(String name, String desc, Texture tex, int stackSizeMax, float fuelPower, int id){
        this.name = name;
        this.desc = desc;
        this.tex = tex;
        this.id = id;
        this.stackSizeMax = stackSizeMax;
        this.fuelPower = fuelPower;
    }

    public float getFuelPower(){
        return fuelPower;
    }
}