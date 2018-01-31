package com.mygdx.game.Saving;

import com.mygdx.game.Actor.Actor;
import com.mygdx.game.Actor.Tile;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Player.PlayerController;

import java.io.Serializable;

/**
 * Created by Christopher Schleppe on 30.01.2018.
 */

public class SaveData implements Serializable {
    //Player
    private int playerX, playerY;

    //Tile
    private float image;

    private int resourceType, resourceAmount;

    public int itemMasterImage;
    public int itemMasterStackSize;
    public int itemMasterStackSizeMax;
    public String itemMasterDesc;
    public ItemId itemMasterId;
    public Actor actor;

    public void setPlayer(PlayerController pc){
        playerX = (int) pc.getPosition().x;
        playerY = (int) pc.getPosition().y;
    }

    public void setTile (Tile t){
        resourceAmount = t.getResource().amount;
        resourceType = t.getResource().getType();

        image = (int) t.image;

        itemMasterImage = t.item.getImage();
        itemMasterStackSize = t.item.getStackSize();
        itemMasterStackSizeMax = t.item.getStackSizeMax();
        itemMasterDesc = t.item.getDesc();
        itemMasterId = t.item.getId();
        actor = t.actor;

    }
}
