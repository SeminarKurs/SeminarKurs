package com.mygdx.game.Actor;

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
}
