package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemId;

/**
 * Created by Tobias on 01.10.2017.
 */

public class TestActor extends Actor {


    public TestActor(){} // never use this constructor not the right

    @Override
    public com.mygdx.game.Types.Collision coll() {
        return com.mygdx.game.Types.Collision.collides;
    }
    public int image(){return 0;}

    public ItemId getId() {
        System.out.println("TestActor needs to be implemented");
        return null;
    }
}
