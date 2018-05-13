package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Christopher Schleppe on 22.10.2017.
 */
// Wenn Position von item (3, 3) und laufband (4, 3) dann wird das Item auf (3.5, 3) verschoben

public class Conveyor extends Actor {
    private static final float SCHRITT_WEITE = (float) 0.5;

    private Direction richtung;
    private IVector2 pos;

    private ItemMaster item;
    private IVector2 itemPos;

    private Actor previousClutch;

    private float progress = -0.5f;

    //1 = Links; 2 = Rechts; 3 = Oben; 4 = Unten
    public Conveyor(Direction richtung, ItemMaster item, IVector2 pos) {
        this.richtung = richtung;
        this.item = item;
        this.pos = pos;
        itemPos = pos;
    }

    @Override
    public boolean needUpdate() {
        return true;
    }

    public boolean transfer (){
        switch (richtung) {
            case left:
                itemPos = new IVector2(pos.x - 1, pos.y);
                if (checkForNearActor(pos) == null) {
                    WorldM.setItemActor(itemPos, item);
                    return true;
                }
                break;
            case right:
                itemPos = new IVector2(pos.x + 1, pos.y);
                if (checkForNearActor(pos) == null) {
                    WorldM.setItemActor(itemPos, item);
                    return true;
                }
                break;
            case up:
                itemPos = new IVector2(pos.x, pos.y + 1);
                if (checkForNearActor(pos) == null) {
                    WorldM.setItemActor(itemPos, item);
                    return true;
                }
                break;
            case down:
                itemPos = new IVector2(pos.x, pos.y - 1);
                if (checkForNearActor(pos) == null) {
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
            progress += dt / 10;
            if (progress >= 0.5f) {
                if (!transfer()) this.moveItemToActor(item, pos);
                item = null;
                progress = -0.5f;
            }
        }

    }
    public void moveItemToActor (ItemMaster item, IVector2 pos){
        Actor a = checkForNearActor(pos);
        if (a != null && a.getId() == ItemId.CONVEYOR) a.setItem(item, null);
    }
    public ItemMaster getItem(){
        return item;
    }

    @Override
    public void draw(Batch batch, int x, int y, Array<FLayer> fLayers) {
        DrawH.drawActorRot(batch, x,y, richtung, image());
        if(item != null)
            switch (richtung) {
                case left: // links
                    fLayers.add(new FLayer(x - progress,y, item.getImage()));
                    break;
                case right: // rechts
                    fLayers.add(new FLayer(x + progress,y, item.getImage()));
                    break;
                case up: // oben
                    fLayers.add(new FLayer(x,y + progress, item.getImage()));
                    break;
                case down: // unten
                    fLayers.add(new FLayer(x ,y - progress, item.getImage()));
                    break;
            }
    }
    public Actor checkForNearActor(IVector2 pos){
        Actor sideActor1;
        Actor sideActor2;

        switch (richtung) {
            case left: // links
                sideActor1 = WorldM.getActor(new IVector2(pos.x, pos.y-1));
                sideActor2 = WorldM.getActor(new IVector2(pos.x, pos.y+1));
                if (sideActor1 != null) {
                    if (sideActor1.getId() == ItemId.CLUTCH) {
                        if(previousClutch != sideActor1) return sideActor1;
                    }
                }
                if(sideActor2 != null) {
                    if (sideActor2.getId() == ItemId.CLUTCH) {
                        if(previousClutch != sideActor2) return sideActor2;
                    }
                }
                return WorldM.getActor(new IVector2(pos.x - 1, pos.y));
            case right: // rechts
                sideActor1 = WorldM.getActor(new IVector2(pos.x, pos.y-1));
                sideActor2 = WorldM.getActor(new IVector2(pos.x, pos.y+1));
                if (sideActor1 != null) {
                        if(sideActor1.getId() == ItemId.CLUTCH) {
                            if (previousClutch != sideActor1) return sideActor1;
                        }
                    }
                if(sideActor2 != null && previousClutch != sideActor2) {
                    if (sideActor2.getId() == ItemId.CLUTCH) {
                        if(previousClutch != sideActor2) return sideActor2;
                    }
                }
                return WorldM.getActor(new IVector2(pos.x+1, pos.y));
            case up: // oben
                sideActor1 = WorldM.getActor(new IVector2(pos.x-1, pos.y));
                sideActor2 = WorldM.getActor(new IVector2(pos.x+1, pos.y));
                if (sideActor1 != null) {
                    if (sideActor1.getId() == ItemId.CLUTCH) {
                        if(previousClutch != sideActor1) return sideActor1;
                    }
                }
                if(sideActor2 != null) {
                    if (sideActor2.getId() == ItemId.CLUTCH) {
                        if(previousClutch != sideActor2) return sideActor2;
                    }
                }
                return WorldM.getActor(new IVector2(pos.x, pos.y+1));
            case down: // unten
                sideActor1 = WorldM.getActor(new IVector2(pos.x-1, pos.y));
                sideActor2 = WorldM.getActor(new IVector2(pos.x+1, pos.y));
                if (sideActor1 != null) {
                    if (sideActor1.getId() == ItemId.CLUTCH) {
                        if(previousClutch != sideActor1) return sideActor1;
                    }
                }
                if(sideActor2 != null) {
                    if (sideActor2.getId() == ItemId.CLUTCH) {
                        if(previousClutch != sideActor2) return sideActor2;
                    }
                }
                return WorldM.getActor(new IVector2(pos.x, pos.y-1));
        }
        return null;
    }
    public boolean setItem(ItemMaster item, Actor actor) {
        this.item = item;
        if (actor.getId() == ItemId.CLUTCH) previousClutch = actor;
        progress = 0f;
        return false;
    }
    public void setItemPos (IVector2 pos){itemPos = pos;}

    @Override
    public com.mygdx.game.Tools.Collision coll() {
        return com.mygdx.game.Tools.Collision.none;
    }
    public int image(){return 1;}

    public ItemId getId() {
        return ItemId.CONVEYOR;
    }
    public IVector2 getItemPos (){

        return itemPos;
    }
}
