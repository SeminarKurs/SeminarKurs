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
            if(coal.getStackSize() > 0) {
                progress += dt / 10;
            }else   busy = false;
                if (progress >= 1) {
                    generate();
                    if(!movePowerToElectricActor()){
                        progress = 1;
                        return;
                    }
                    progress = 0;
                }
        }
    }


    private Actor checkForRightActor(IVector2 pos, Direction richtung){
        Actor a;
        if ((a = WorldM.getActor(pos)) != null) {
            System.out.println("existing");
            if(a.getId() == ItemId.POWERLINE && a.getDirection() == richtung) return a;
            System.out.println("powerline is found ");
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

    @Override
    public boolean movePowerToElectricActor() {
        ElectricActor electricActor = (ElectricActor) checkForNearActor();
        if(electricActor != null) {
            System.out.println("is");
            if (!electricActor.isBusy()) {
                System.out.println("free");
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