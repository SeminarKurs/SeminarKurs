package com.mygdx.game.Saving;

import com.mygdx.game.Actor.Tile;
import com.mygdx.game.Player.PlayerController;

import java.io.Serializable;

/**
 * Created by Christopher Schleppe on 20.12.2017.
 */

public class Data_To_Save implements Serializable {

    public Tile [][] tiles;
    public PlayerController playerController;



}
