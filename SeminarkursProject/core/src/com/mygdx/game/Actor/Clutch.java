package com.mygdx.game.Actor;

/**
 * Created by Neutral on 28.12.2017.
 */

import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Types.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Christopher Schleppe on 26.11.2017.
 */

public class Clutch extends StorageActor {
    private static final float SCHRITT_WEITE = (float) 0.5;

    private int richtung;
    private ItemMaster item;
    private IVector2 pos;
    private float progress = -0.5f;

    public Clutch (int richtung, ItemMaster item, IVector2 pos){
        this.richtung = richtung;
        this.item = item;
        this.pos = pos;
    }

    public void update (float dt){
        progress += dt /20 ;
        if (progress >= 0.5f ) {
            progress -= 0.5f;
            transfer();
        }

    }

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
    public boolean setItem(ItemMaster Item) {
        return false;
    }
}

