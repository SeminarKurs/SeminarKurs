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

    protected boolean busy = false;
    protected float speed;
    protected float progress;
    public IVector2 pos;

    public Actor(){}

    public void update(float dt) {}

    public Direction getDirection(){return null;}

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
    public boolean setItem(ItemMaster item, Actor actor) {return false;} // Actor-Parameter is for previous Clutch in Conveyor
    public ItemMaster getItem(){return null;}
    public void setProgress(int progress){}
    public boolean isBusy() {return busy;}
    public void setBusy(boolean busy) {this.busy = busy;}

    public int getCapacity (){return 0;}
}