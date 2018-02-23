package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Types.Collision;

import java.io.Serializable;

/**
 * Created by Tobias on 02.09.2017.
 */




public class Tile{
    public float image = 0;
    // 0 non 1 overllapp 2 block
    public Collision collision = Collision.none;
    public Actor actor;
    private Resource resource;
    public ItemMaster item;

    public void setRes(int type)
    {
        resource = new Resource(type);
        //if(type == 1)
            //collision = Collision.collides;
    }

    public void deleteResource (){resource = null;}
    public void setResource (Resource resource){this.resource = resource;}
    public void refresh()
    {
        if(resource.amount <= 0)
        {
            resource = null;
        }
    }

    public void updateColl()
    {
        if(actor != null) {
            if(actor.coll() == Collision.collides)// if the actor wouldn't collied then we don't need to change anything
                {
                    collision = actor.coll();
                    return;
                }
        }
        collision = Collision.none;
    }
    public boolean hasRes(){return resource != null;}
    public void resDraw(Batch b, int x, int y){ resource.draw(b, x, y); }
    public int resAmount(){return resource.amount;}
    public void resSetAmount(int amount){ resource.amount = amount; }
    public int resHardness(){ return resource.hardness();}
    public int getResourcesType(){return resource.getType();}
    public ItemMaster getItemResource(int size){ return resource.getItem(size);}
    public Resource getResource (){return resource;}


}
