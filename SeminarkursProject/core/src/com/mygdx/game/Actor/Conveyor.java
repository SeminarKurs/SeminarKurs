package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Types.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Christopher Schleppe on 22.10.2017.
 */
// Wenn Position von item (3, 3) und laufband (4, 3) dann wird das Item auf (3.5, 3) verschoben

public class Conveyor extends Actor {
    private static final float SCHRITT_WEITE = (float) 0.5;

    private int richtung ;
    private ItemMaster item;
    private IVector2 pos;
    private float progress = -0.5f;

    public void transfer (){
        switch (richtung) {
            case 1:
                if (WorldM.setItemActor(new IVector2(pos.x - 1, pos.y), item)) {
                    item = null;
                }
                break;
            case 2:
                if (WorldM.setItemActor(new IVector2(pos.x + 1, pos.y), item)) {
                    item = null;
                }
                break;
            case 3:
                if (WorldM.setItemActor(new IVector2(pos.x, pos.y + 1), item)) {
                    item = null;
                }
                break;
            case 4:
                if (WorldM.setItemActor(new IVector2(pos.x, pos.y - 1), item)) {
                    item = null;
                }
                break;
        }
    }

    //1 = Links; 2 = Rechts; 3 = Oben; 4 = Unten
    public Conveyor(int richtung, ItemMaster item, IVector2 pos) {
        needUpdate = true;
        this.richtung = richtung;
        this.item = item;
        this.pos = pos;
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
        if(item != null)
            switch (richtung) {
                case 1: // links
                    fLayers.add(new FLayer(x - progress,y, item.getImage()));
                    break;
                case 2: // rechts
                    fLayers.add(new FLayer(x + progress,y, item.getImage()));
                    break;
                case 3: // oben
                    fLayers.add(new FLayer(x,y + progress, item.getImage()));
                    break;
                case 4: // unten
                    fLayers.add(new FLayer(x ,y - progress, item.getImage()));
                    break;
            }
    }

    @Override
    public Collision coll() {
        return Collision.none;
    }
    public int image(){return 0;}

}
