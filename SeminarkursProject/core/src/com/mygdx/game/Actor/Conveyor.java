package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Christopher Schleppe on 22.10.2017.
 */
// Wenn Position von item (3, 3) und laufband (4, 3) dann wird das Item auf (3.5, 3) verschoben

public class Conveyor extends Actor {

    private Direction direction;

    private ItemMaster item;
    private IVector2 itemPos;

    private Actor previousClutch;


    public Conveyor(Direction direction, ItemMaster item, IVector2 pos) {
        this.direction = direction;
        this.item = item;
        this.pos = pos;
        itemPos = pos;
        speed = 0.1f;
        progress = 0f;
    }

    @Override
    public boolean needUpdate() {
        return true;
    }

    public boolean transfer (){
        switch (direction) {
            case left:
                itemPos = new IVector2(pos.x - 1, pos.y);
                if (checkForNearActor() == null) {
                    WorldM.setItemActor(itemPos, item);
                    return true;
                }
                break;
            case right:
                itemPos = new IVector2(pos.x + 1, pos.y);
                if (checkForNearActor() == null) {
                    WorldM.setItemActor(itemPos, item);
                    return true;
                }
                break;
            case up:
                itemPos = new IVector2(pos.x, pos.y + 1);
                if (checkForNearActor() == null) {
                    WorldM.setItemActor(itemPos, item);
                    return true;
                }
                break;
            case down:
                itemPos = new IVector2(pos.x, pos.y - 1);
                if (checkForNearActor() == null) {
                    WorldM.setItemActor(itemPos, item);
                    return true;
                }
                break;
        }
        itemPos = pos;
        return false;
    }


    public void update (float dt){
        if(item != null) {
            progress += dt * speed;
            Actor a = checkForNearActor();
            if(a != null && a.getId() == ItemId.CLUTCH) {
                moveItemToActor();
                item = null;
            } else if (progress >= 1f) {
                    if (!transfer()) {
                        if (!this.moveItemToActor()){
                            progress = 1f;
                            return;
                        }
                    }
                    item = null;
                    busy = false;
                    progress = 0f;
                }
        }

    }
    public boolean moveItemToActor (){
        Actor a = checkForNearActor();
        if(!a.isBusy()) {
            if (a.getId() == ItemId.CONVEYOR) a.setItem(item, previousClutch);
            if (a.getId() == ItemId.CLUTCH) a.setItem(item, this);
            return true;
        }else   return false;
    }
    public ItemMaster getItem(){
        return item;
    }

    @Override
    public void draw(Batch batch, int x, int y, Array<FLayer> fLayers) {
        DrawH.drawActorRot(batch, x,y, direction, image());
        if(item != null)
            switch (direction) {
                case left:
                    fLayers.add(new FLayer(x - this.progress,y, item.getImage()));
                    break;
                case right:
                    fLayers.add(new FLayer(x + this.progress,y, item.getImage()));
                    break;
                case up:
                    fLayers.add(new FLayer(x,y + this.progress, item.getImage()));
                    break;
                case down:
                    fLayers.add(new FLayer(x ,y - this.progress, item.getImage()));
                    break;
            }
    }

    private boolean checkForRightActor(Actor sideActor, Direction direction){ // this is to shrink the size of checkForNearActor()
        if (sideActor != null) {
            if (sideActor.getId() == ItemId.CLUTCH) {
                if (previousClutch != sideActor && sideActor.getDirection() == direction) return true;
            }
        }
        return false;
    }
    public Actor checkForNearActor(){
        Actor sideActor;
        switch (direction) {
            case left:
                if (pos.y != 0) {
                    sideActor = WorldM.getActor(new IVector2(pos.x, pos.y - 1));
                    if(checkForRightActor(sideActor, Direction.down)) return sideActor;

                }
                if (pos.y != WorldM.HEIGHT) {
                    sideActor = WorldM.getActor(new IVector2(pos.x, pos.y + 1));
                    if(checkForRightActor(sideActor, Direction.up))    return sideActor;
                }
                return WorldM.getActor(new IVector2(pos.x - 1, pos.y));
            case right:
                if (pos.y != 0) {
                    sideActor = WorldM.getActor(new IVector2(pos.x, pos.y - 1));
                    if(checkForRightActor(sideActor, Direction.down)) return sideActor;
                }
                if (pos.y != WorldM.HEIGHT) {
                    sideActor = WorldM.getActor(new IVector2(pos.x, pos.y + 1));
                    if(checkForRightActor(sideActor, Direction.up))    return sideActor;
                }
                return WorldM.getActor(new IVector2(pos.x + 1, pos.y));
            case up:
                if (pos.x != 0) {
                    sideActor = WorldM.getActor(new IVector2(pos.x-1, pos.y));
                    if(checkForRightActor(sideActor, Direction.left))    return sideActor;
                }
                if (pos.x != WorldM.WIDTH) {
                    sideActor = WorldM.getActor(new IVector2(pos.x+1, pos.y));
                    if(checkForRightActor(sideActor, Direction.right))    return sideActor;
                }
                return WorldM.getActor(new IVector2(pos.x, pos.y + 1));
            case down:
                if (pos.x != 0) {
                    sideActor = WorldM.getActor(new IVector2(pos.x-1, pos.y));
                    if(checkForRightActor(sideActor, Direction.left))    return sideActor;
                }
                if(pos.x != WorldM.WIDTH) {
                    sideActor = WorldM.getActor(new IVector2(pos.x+1, pos.y));
                    if(checkForRightActor(sideActor, Direction.right))    return sideActor;
                }
                return WorldM.getActor(new IVector2(pos.x, pos.y-1));
        }
        return null;
    }
    public boolean setItem(ItemMaster item, Actor actor) {
        this.item = item;
        busy = true;
        if (actor.getId() == ItemId.CLUTCH) previousClutch = actor;
        progress = 0f;
        return false;
    }
    public void setItemPos (IVector2 pos){itemPos = pos;}

    @Override
    public Collision coll() {return Collision.none;}
    public int image(){return 1;}

    public ItemId getId() {
        return ItemId.CONVEYOR;
    }
    public IVector2 getItemPos (){

        return itemPos;
    }
    @Override
    public Direction getDirection(){return direction;}
}
