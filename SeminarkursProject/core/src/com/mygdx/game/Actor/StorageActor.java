package com.mygdx.game.Actor;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemMaster;

/**
 * Created by Tobias on 26.11.2017.
 */

public abstract class StorageActor extends Actor {
    public Array<ItemMaster> storage = new Array<ItemMaster>();

    public abstract ItemMaster getItem();
    public abstract ItemMaster takeItem();
    public abstract boolean setItem(ItemMaster Item);



}
