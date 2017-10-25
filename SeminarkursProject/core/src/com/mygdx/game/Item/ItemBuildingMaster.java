package com.mygdx.game.Item;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Implodee on 22.10.2017.
 */

public class ItemBuildingMaster extends ItemMaster{

    protected int size;

    public ItemBuildingMaster(String name, String desc, Texture tex, int size, int id){
        this.name = name;
        this.desc = desc;
        this.tex = tex;
        this.stackSizeMax = 1;
        this.size = size;
        this.id = id;
    }

    public int getSize(){
        return size;
    }
}