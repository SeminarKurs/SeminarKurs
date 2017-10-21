package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.WorldM;

/**
 * Created by Tobias on 21.10.2017.
 */

// helper class for actors to draw
public class DrawH {
    public static void draw(Batch batch, int x, int y, int imageNum)
    {
        if(WorldM.getTexture(imageNum) != null) {
            batch.draw(WorldM.getTexture(imageNum), x - 0.5f, y - 0.5f, 1f, 1f);
        }else{
            System.out.println("Error: invalid image num!");
        }

    }
}
