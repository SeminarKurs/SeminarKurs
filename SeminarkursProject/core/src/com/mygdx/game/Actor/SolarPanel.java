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


            public SolarPanel (IVector2 pos){
                this.pos = pos;
                maxCapacity = 10;
                speed = 0.1f;
                progress = 0;
            }

            public void update (float dt){
                progress += dt * speed;
                movePowerToElectricActor();
                if (progress >= 2){
                    if (capacity < maxCapacity)  capacity++; // generate
                    progress = 0;
        }
    }


    @Override
    public boolean movePowerToElectricActor() {
        ElectricActor electricActor = (ElectricActor) checkForNearActor();
        if(electricActor != null && capacity > 0){
            if(!electricActor.isBusy()) {
                electricActor.addCapacity(1);
                capacity--;
                return true;
            }
        }
        return false;
    }

    private Actor checkForRightActor(IVector2 pos, Direction richtung){
        Actor a;
        if ((a = WorldM.getActor(pos)) != null) {
            if(a.getId() == ItemId.POWERLINE && a.getDirection() == richtung) return a;
        }
        return null;
    }

    @Override
    public Actor checkForNearActor(){
        Actor a;
        if(pos.x != WorldM.WIDTH) {
            a = checkForRightActor(new IVector2(pos.x + 1, pos.y), Direction.right);
            if (a != null) return a;
        }
        if(pos.x != 0) {
            a = checkForRightActor(new IVector2(pos.x - 1, pos.y), Direction.left);
            if (a != null) return a;
        }
        if(pos.y != WorldM.HEIGHT) {
            a = checkForRightActor(new IVector2(pos.x, pos.y + 1), Direction.up);
            if (a != null) return a;
        }
        if(pos.y != 0) {
            a = checkForRightActor(new IVector2(pos.x, pos.y - 1), Direction.down);
            if (a != null) return a;
        }
        return null;
    }


    public Collision coll(){return Collision.collides;}

    public int image(){return 7;}

    public boolean needUpdate(){ return true;}

    public ItemId getId() {
        return ItemId.SOLARPANEL;
    }

}
