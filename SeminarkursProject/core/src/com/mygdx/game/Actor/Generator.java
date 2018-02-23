package com.mygdx.game.Actor;

import com.mygdx.game.Actor.Actor;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Types.Collision;

public class Generator extends StorageActor{
    private ItemMaster[] brennstofftank;
    private int battery;

    private int tankLevel = 0; // Max = 10

    private float progress = -0.5f;


    public void fillUp (ItemMaster[] extBrennstofftank){
        for (int i = 0; i < brennstofftank.length; i++){
            if (tankLevel < 10){
                brennstofftank [i + tankLevel] = extBrennstofftank[i];
                tankLevel++;
            }
        }
    }

    public void generate (){
        if(tankLevel > 0) {
            tankLevel--;
            battery++;
        }
    }

    public void update (float dt){
        progress += dt/100;
        if (progress >= 100){
            progress -= 100;
            generate();
        }
    }

    public int getBattery (){return battery;}

    public Collision coll(){return Collision.none;}

    public int image(){return 0;}

    @Override
    public ItemMaster getItem() {
        return null;
    }

    @Override
    public ItemMaster takeItem() {
        return null;
    }

    @Override
    public boolean setItem(ItemMaster Item) {
        return false;
    }

    public ItemId getId() {
        System.out.println("Generator needs to be implemented");
        return null;
    }
}