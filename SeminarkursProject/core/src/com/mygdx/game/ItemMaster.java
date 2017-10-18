package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public interface ItemMaster {
    public String getName();
    public String getDesc();
    public Texture getTexture();
    public int getStackSizeMax();
}