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
    //rot: 1 = Links; 2 = Rechts; 3 = Oben; 4 = Unten
    public static void drawActorRot(Batch batch, int x, int y, int rot, int imageNum)
    {
        if(TexturesClass.getTextureActor(imageNum) != null){
            if(rot < 3)
                batch.draw(TexturesClass.getTextureActor(imageNum), x - 0.5f, y - 0.5f, 0,0, 1,1, 1,1, 0, 0,0, 32,32,rot == 1,false);
            else
            {
                if(rot == 3)
                    batch.draw(TexturesClass.getTextureActor(imageNum), x + 0.5f, y - 0.5f, 0,0, 1,1, 1,1, 90, 0,0, 32,32,false,false);
                if(rot == 4)
                    batch.draw(TexturesClass.getTextureActor(imageNum), x + 0.5f, y - 0.5f, 0, 1, 1,1, 1,1, 270, 0,0, 32,32,false,false);
            }
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
