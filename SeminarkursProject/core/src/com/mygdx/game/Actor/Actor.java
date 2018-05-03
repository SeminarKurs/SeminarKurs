package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Tobias on 24.08.2017.
 */

public abstract class Actor {

    public Actor(){}

    public void update(float dt) {}

    public Actor checkForNearActor(IVector2 pos){

        if(WorldM.getActor(new IVector2(pos.x + 1, pos.y)) != null){
            System.out.println("LEBEN 1");
            return WorldM.getActor(new IVector2(pos.x + 1, pos.y));
        }
        if(WorldM.getActor(new IVector2(pos.x - 1, pos.y)) != null){
            System.out.println("LEBEN 2");
            return WorldM.getActor(new IVector2(pos.x - 1, pos.y));
        }
        if(WorldM.getActor(new IVector2(pos.x, pos.y + 1)) != null){
            System.out.println("LEBEN 3");
            return WorldM.getActor(new IVector2(pos.x, pos.y + 1));
        }
        if(WorldM.getActor(new IVector2(pos.x + 1, pos.y - 1)) != null){
            System.out.println("LEBEN 4");
            return WorldM.getActor(new IVector2(pos.x, pos.y - 1));
        }
        return null;
    }

    public void moveItemToActor (ItemMaster item, IVector2 pos){
        Actor a = checkForNearActor(pos);
        if (a != null){
            a.setItem(item);
        }
    }

    // return collison
    public Collision coll(){return Collision.none;}
    // returns the number of the image
    public int image(){return 0;}

    // true needs to be updated, false don't update
    public boolean needUpdate(){ return false;}
    // FLayer is the topmost layer to be drawn (but the player is further in the front)
    public void draw(Batch batch, int x, int y, Array<FLayer> fLayers){ DrawH.drawActor(batch, x,y, image());}

    public abstract ItemId getId();

    public Actor checkForNearActor (){return null;}
    public boolean setItem(ItemMaster item) {return false;}
}