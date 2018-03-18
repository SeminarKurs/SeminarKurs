package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.IVector2;

/**
 * Created by Christopher Schleppe on 05.01.2018.
 */

public class Powerline extends Actor {
    
    private IVector2 pos;

    //1 = Links; 2 = Rechts; 3 = Oben; 4 = Unten
    public Powerline(IVector2 pos) {
        this.pos = pos;
    }
    
    @Override
    public void draw(Batch batch, int x, int y, Array<FLayer> fLayers) {
        //DrawH.drawActorRot(batch, x, y, image());
        
    }
    
    

    @Override
    public Collision coll() {return Collision.none;}
    public int image(){return 1;}

    public ItemId getId() {
        System.out.println("PowerLine needs to be implemented");
        return null;
    }

}
