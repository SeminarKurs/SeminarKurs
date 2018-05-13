package com.mygdx.game.Actor;

/**
 * Created by Neutral on 28.12.2017.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Christopher Schleppe on 26.11.2017.
 */

public class Clutch extends StorageActor {
    private static final float SCHRITT_WEITE = (float) 0.5;

    private Direction richtung ;
    private ItemMaster item;
    private IVector2 pos;
    private IVector2 itemPos;
    private float progress = -0.5f;

    public Clutch (IVector2 pos, Direction richtung){
        this.richtung = richtung;
        this.pos = pos;
        itemPos = pos;
    }
    public boolean needUpdate() {
        return true;
    }
    public void update (float dt){
        if (item != null) {
            progress += dt / 5;
            if (progress >= 0.5f) {
                if (!transfer()) this.moveItemToActor(item, pos);
                item = null;
                progress = -0.5f;
            }
        }
    }

    public Actor checkForNearActor(IVector2 pos){

        switch (richtung) {
            case left: // links
                return WorldM.getActor(new IVector2(pos.x-1, pos.y));
            case right: // rechts
                System.out.println("gott");
                return WorldM.getActor(new IVector2(pos.x+1, pos.y));
            case up: // oben
                return WorldM.getActor(new IVector2(pos.x, pos.y+1));
            case down: // unten
                return WorldM.getActor(new IVector2(pos.x, pos.y-1));
        }
        return null;
    }
    public void moveItemToActor (ItemMaster item, IVector2 pos){
        Actor a = checkForNearActor(pos);
        if (a != null) a.setItem(item, this);
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

    public void addItem (ItemMaster item, Oven oven){
        oven.addItem(item);
    }

    @Override
    public ItemMaster getItem() {
        return null;
    }

    @Override
    public ItemMaster takeItem() {
        return null;
    }

    @Override
    public boolean setItem(ItemMaster item) {
        this.item = item;
        return false;
    }
    public void setRichtung (Direction richtung){this.richtung = richtung;
        System.out.println(this.richtung);}

    @Override
    public ItemId getId() {
        return ItemId.CLUTCH;
    }

    public int image(){return 4;}

}


