package com.mygdx.game.Actor;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemMaster;

/**
 * Created by Tobias on 26.11.2017.
 */

public class Storage extends Actor {
    public Array<ItemMaster> storage = new Array<ItemMaster>();
    // the index that the
    protected int takeFrom = 0;

    public int getTakeFrom()
    {
        if(storage.size < takeFrom)
        {
            System.out.println("storage not large enough : Storage");
            return -1;
        }
        else
            return takeFrom;
    }
}
