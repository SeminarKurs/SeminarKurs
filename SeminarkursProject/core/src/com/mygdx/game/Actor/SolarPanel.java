package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Christopher Schleppe on 04.01.2018.
 */

public class SolarPanel extends ElectricActor {

    private float progress = 0;

    public SolarPanel (IVector2 pos){
        this.pos = pos;
        maxCapacity = 10;
    }

    public void update (float dt){
        progress += dt/10;
        System.out.println(progress);
        if (progress >= 2){
            progress = 0;
            if (capacity < 10){
                capacity++; // generate
                System.out.println(capacity);
                movePowerToElectricActor();
                System.out.println(capacity);
            }
        }
    }


    @Override
    public boolean movePowerToElectricActor() {
        ElectricActor electricActor = (ElectricActor) checkForNearActor();
        if(electricActor != null){
            electricActor.addCapacity(1);
            capacity--;
            return true;
        }
        return false;
    }

    @Override
    public Actor checkForNearActor (){
        Actor actor;
        IVector2 pos;

        if(assistingMethodForCheckForNearActor((pos = new IVector2(this.pos.x -1, this.pos.y)))) return WorldM.getActor(pos);
        if(assistingMethodForCheckForNearActor((pos = new IVector2(this.pos.x +1, this.pos.y)))) return WorldM.getActor(pos);
        if(assistingMethodForCheckForNearActor((pos = new IVector2(this.pos.x, this.pos.y -1)))) return WorldM.getActor(pos);
        if(assistingMethodForCheckForNearActor((pos = new IVector2(this.pos.x, this.pos.y +1)))) return WorldM.getActor(pos);

        return null;
    }

    private boolean assistingMethodForCheckForNearActor(IVector2 pos){
        Actor actor;
        if((actor = WorldM.getActor(pos)) != null){
            if(actor.getId() == ItemId.POWERLINE)   return true;
        }
        return false;
    }

    public Collision coll(){return Collision.collides;}

    public int image(){return 7;}

    public boolean needUpdate(){ return true;}

    public ItemId getId() {
        return ItemId.SOLARPANEL;
    }

}
