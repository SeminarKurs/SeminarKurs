package com.mygdx.game.Item;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Implodee on 22.10.2017.
 */

public class ItemMaster {

    protected String name;
    protected String desc;
    protected Texture tex;
    protected int stackSizeMax;
    protected int stackSize;
    protected int id;

    public ItemMaster(String name, String desc, Texture tex, int stackSizeMax, int id){
        this.name = name;
        this.desc = desc;
        this.tex = tex;
        this.stackSizeMax = stackSizeMax;
        this.id = id;
    }

    public ItemMaster(){}

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public Texture getTex(){
        return tex;
    }

    public int getStackSizeMax(){
        return stackSizeMax;
    }

    public int getID(){
        return id;
    }

    public int getStackSize (){
        return stackSize;
    }

    public void addStackSize(int s){
        stackSize += s;
    }
}