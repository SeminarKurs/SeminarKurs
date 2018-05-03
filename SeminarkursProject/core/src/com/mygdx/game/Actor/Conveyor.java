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


    private float progress = -0.5f;

    //1 = Links; 2 = Rechts; 3 = Oben; 4 = Unten
    public Conveyor(Direction richtung, ItemMaster item, com.mygdx.game.Tools.IVector2 pos) {
        this.richtung = richtung;
        this.item = item;
        this.pos = pos;
        itemPos = pos;
    }

    @Override
    public boolean needUpdate() {
        return true;
    }

    public void transfer (){
        switch (richtung) {
            case left:
                itemPos.set(itemPos.x -1, itemPos.y);
                if (WorldM.setItemActor(itemPos, item)) {
                    item = null;
                }
                break;
            case right:
                itemPos.set(itemPos.x +1, itemPos.y);
                if (WorldM.setItemActor(itemPos, item)) {
                    item = null;
                }
                break;
            case up:
                itemPos.set(itemPos.x , itemPos.y +1);
                if (WorldM.setItemActor(itemPos, item)) {
                    item = null;
                }
                break;
            case down:
                itemPos.set(itemPos.x, itemPos.y -1);
                if (WorldM.setItemActor(itemPos, item)) {
                    item = null;
                }
                break;
        }
        WorldM.resetItemActor(itemPos);
    }

    public void update (float dt){
        if(item != null) {
            progress += dt / 10;
            if (progress >= 0.5f) {
                this.moveItemToActor(item, pos);
                transfer();
                progress = 0f;
            }
        }

    }
    public ItemMaster getActor(){
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
    public boolean setItem(ItemMaster item) {
        this.item = item;
        itemPos = pos;
        progress = 0f;
        System.out.println("got the item boss");
        return false;
    }

    @Override
    public com.mygdx.game.Tools.Collision coll() {
        return com.mygdx.game.Tools.Collision.none;
    }
    public int image(){return 1;}

    public ItemId getId() {
        return ItemId.CONVEYOR;
    }
}
