package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

public class Generator extends ElectricActor{
    private ItemMaster coal;

    private float progress = 0;


    public Generator (IVector2 pos){
        this.pos = pos;
        maxCapacity = 10;
    }

    @Override
    public boolean setItem (ItemMaster item, Actor actor){
        if(item.getId() == ItemId.COAL){
            coal = item;
            System.out.println("got");
            busy = true;
            return true;
        }
        return false;
    }

    public void generate (){
        if(capacity < maxCapacity){
            capacity++;
            coal.addStackSize(-1);
        }
    }

    public void update (float dt){
        if(coal != null) {
            //System.out.println(coal.getStackSize());
            if(coal.getStackSize() > 0) {
                progress += dt / 10;
                if (progress >= 1) {
                    generate();
                    System.out.println("fertig");
                    movePowerToElectricActor();
                    progress = 0;
                }
            }else   busy = false;
        }
    }

    private Actor assistingMethodForCheckForNearActor(IVector2 pos){
        Actor actor;
        if((actor = WorldM.getActor(pos)) != null) return actor;
        return null;
    }
    @Override
    public Actor checkForNearActor (){
        Actor actor;
        if((actor = assistingMethodForCheckForNearActor(new IVector2(pos.x - 1, pos.y))).getId() == ItemId.POWERLINE) return actor;//links
        if((actor = assistingMethodForCheckForNearActor(new IVector2(pos.x + 1, pos.y))).getId() == ItemId.POWERLINE) return actor;//rechts
        if((actor = assistingMethodForCheckForNearActor(new IVector2(pos.x, pos.y - 1))).getId() == ItemId.POWERLINE) return actor;//unten
        if((actor = assistingMethodForCheckForNearActor(new IVector2(pos.x, pos.y + 1))).getId() == ItemId.POWERLINE)  return actor; //oben

        return null;
    }

    @Override
    public boolean movePowerToElectricActor() {
        ElectricActor electricActor = (ElectricActor) checkForNearActor();
        if(electricActor != null) {
            if (!electricActor.isBusy()) {
                electricActor.addCapacity(1);
                capacity--;
                return true;
            }
        }
        return false;
    }

    public Collision coll(){return Collision.none;}

    public int image(){return 8;}

    @Override
    public ItemMaster getItem() {
        return coal;
    }

    @Override
    public boolean setItem(ItemMaster item){
        if(item.getId() == ItemId.COAL){
            coal = item;
            busy = true;
            return true;
        }
        return false;
    }
    @Override
    public boolean needUpdate(){ return true;}

    public ItemId getId() {
        return ItemId.GENERATOR;
    }
}