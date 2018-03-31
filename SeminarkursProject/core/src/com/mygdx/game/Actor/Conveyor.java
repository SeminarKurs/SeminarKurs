package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.WorldM;

/**
 * Created by Christopher Schleppe on 22.10.2017.
 */
// Wenn Position von item (3, 3) und laufband (4, 3) dann wird das Item auf (3.5, 3) verschoben

public class Conveyor extends Actor {
    private static final float SCHRITT_WEITE = (float) 0.5;

    private Direction richtung;
    private ItemMaster item;
    private com.mygdx.game.Tools.IVector2 pos;
    private float progress = -0.5f;

    //1 = Links; 2 = Rechts; 3 = Oben; 4 = Unten
    public Conveyor(Direction richtung, ItemMaster item, com.mygdx.game.Tools.IVector2 pos) {
        this.richtung = richtung;
        this.item = item;
        this.pos = pos;
    }

    @Override
    public boolean needUpdate() {
        return true;
    }

    public void transfer (){
        switch (richtung) {
            case left:
                if (WorldM.setItemActor(new com.mygdx.game.Tools.IVector2(pos.x - 1, pos.y), item)) {
                    item = null;
                }
                break;
            case right:
                if (WorldM.setItemActor(new com.mygdx.game.Tools.IVector2(pos.x + 1, pos.y), item)) {
                    item = null;
                }
                break;
            case up:
                if (WorldM.setItemActor(new com.mygdx.game.Tools.IVector2(pos.x, pos.y + 1), item)) {
                    item = null;
                }
                break;
            case down:
                if (WorldM.setItemActor(new com.mygdx.game.Tools.IVector2(pos.x, pos.y - 1), item)) {
                    item = null;
                }
                break;
        }
    }

    public void update (float dt){
        progress += dt /10 ;
        if (progress >= 0.5f ) {
            progress = 0.5f;
            transfer();
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

    @Override
    public com.mygdx.game.Tools.Collision coll() {
        return com.mygdx.game.Tools.Collision.none;
    }
    public int image(){return 1;}

    public ItemId getId() {
        return ItemId.CONVEYOR;
    }
}
