package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Textures.TexturesClass;

/**
 * Created by Tobias on 21.10.2017.
 */

// helper class for actors to drawActor
public class DrawH {
    public static void drawActor(Batch batch, int x, int y, int imageNum)
    {
        if(TexturesClass.getTextureActor(imageNum) != null) {
            batch.draw(TexturesClass.getTextureActor(imageNum), x - 0.5f, y - 0.5f, 1f, 1f);
        }else{
            System.out.println("Error: invalid image num! actor");
        }
    }
    public static void drawResource(Batch batch, int x, int y, int imageNum)
    {
        if(TexturesClass.getTextureResource(imageNum) != null) {
            batch.draw(TexturesClass.getTextureResource(imageNum), x - 0.5f, y - 0.5f, 1f, 1f);
        }else{
            System.out.println("Error: invalid image num! resource");
        }
    }
    public static void drawItemActor(Batch batch, int x, int y, int imageNum)
    {
        if(TexturesClass.getTextureItem(imageNum) != null) {
            batch.draw(TexturesClass.getTextureItem(imageNum), x - 0.5f, y - 0.5f, 1f, 1f);
        }else{
            System.out.println("Error: invalid image num! resource");
        }
    }
    public static void drawItemActor(Batch batch, float x, float y, int imageNum)
    {
        if(TexturesClass.getTextureItem(imageNum) != null) {
            batch.draw(TexturesClass.getTextureItem(imageNum), x - 0.5f, y - 0.5f, 1f, 1f);
        }else{
            System.out.println("Error: invalid image num! itemActor");
        }
    }
}
