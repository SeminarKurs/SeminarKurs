package com.mygdx.game.Enemy;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tobias on 17.12.2017.
 */

public class Enemy {
    public final static float ENEMYSIZEHX = com.mygdx.game.Player.PlayerController.PlSIZEHX;
    public final static float ENEMYSIZEHY = com.mygdx.game.Player.PlayerController.PlSIZEHY;

    private int image;
    private Vector2 position = new Vector2(1.2f,1.4f);

    public int getImage() { return image; }

    public Vector2 getPosition() { return position; }

}
