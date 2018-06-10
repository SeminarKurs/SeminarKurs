package com.mygdx.game.Saving;

import com.mygdx.game.Actor.Direction;
import com.mygdx.game.Actor.Resource;
import com.mygdx.game.Actor.Tile;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Player.PlayerController;

import java.io.Serializable;

/**
 * Created by Christopher Schleppe on 30.01.2018.
 */

public class SaveData implements Serializable {
    //Player
    public float playerX, playerY;
    // Items
    public int[][] itemMasterStackSize;
    public ItemId[][] itemMasterId;
    public float[][] images;
    // actoren
    public ItemId[][] actorId;
    public Direction[][] actorDirection;
    public ItemId[][] actorItemId;
    public int[][] actorItemStackSize;
    public int[][] actorCapacity;

    // Ressourcen
    public Resource[][] resources;

    public void setPlayer(PlayerController pc){
        playerX =  pc.getPosition().x;
        playerY =  pc.getPosition().y;
    }

    public void setTile (Tile[][] tiles){

        if (tiles.length == 0){
            System.out.println("saveData setTiles \\ no tiles");
            return;
        }
        images = new float[tiles.length][tiles[0].length];

        actorId = new ItemId[tiles.length][tiles[0].length];
        actorDirection = new Direction[tiles.length][tiles[0].length];
        actorItemId = new ItemId[tiles.length][tiles[0].length];
        actorItemStackSize = new int[tiles.length][tiles[0].length];
        actorCapacity = new int[tiles.length][tiles[0].length];

        resources = new Resource[tiles.length][tiles[0].length];
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if (tiles[x][y].actor != null) {
                    actorCapacity[x][y] = tiles[x][y].actor.getCapacity();
                    actorId[x][y] = tiles[x][y].actor.getId();
                    actorDirection[x][y] = tiles[x][y].actor.getDirection();
                    if(tiles[x][y].actor.getItem() != null){
                        actorItemId[x][y] = tiles[x][y].actor.getItem().getId();
                        actorItemStackSize[x][y] = tiles[x][y].actor.getItem().getStackSize();
                    }
                }
                if (resources != null) {
                    resources[x][y] = tiles[x][y].getResource();
                }
                images[x][y] = tiles[x][y].image;
            }
        }
    }
}
