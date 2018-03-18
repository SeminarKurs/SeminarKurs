package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Tools.Collision;

/**
 * Created by Tobias on 01.10.2017.
 */

public class TestActor extends Actor {


    public TestActor(){} // never use this constructor not the right

    @Override
    public Collision coll() {
        return Collision.collides;
    }
    public int image(){return 0;}

    public ItemId getId() {
        System.out.println("TestActor needs to be implemented");
        return null;
    }
}
